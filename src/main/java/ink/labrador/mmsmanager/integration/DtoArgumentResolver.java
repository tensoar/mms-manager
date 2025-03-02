package ink.labrador.mmsmanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.integration.transfer.exception.FormValueTransformerException;
import ink.labrador.mmsmanager.support.ObjectMapperSupport;
import ink.labrador.mmsmanager.util.StrUtil;
import ink.labrador.mmsmanager.integration.annotation.NoValid;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.TransformerCache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.apache.commons.beanutils.ConvertUtils;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


public class DtoArgumentResolver implements HandlerMethodArgumentResolver {
    private final Logger logger = LoggerFactory.getLogger(DtoArgumentResolver.class);
    private final ObjectMapper objectMapper = ObjectMapperSupport.newMapperInstance();
    private final Validator validator = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Dto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> paramCls = parameter.getParameterType();
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
//        objectMapper.
        Object paramInstance = null;
        boolean hasJSONBody = hasJSONBody(request);
        if (hasJSONBody) {
            try {
                String body = request.getReader().lines().reduce("", (a, b) -> a + b);
                if (StrUtil.hasLength(body)) {
                    paramInstance = objectMapper.readValue(body, paramCls);
                    for (Field field: paramCls.getDeclaredFields()) {
                        field.setAccessible(true);
                        transformAndSetValue(field, field.get(paramInstance), paramInstance, hasJSONBody);
//                    if (field.isAnnotationPresent(FormValueTransfer.class)) {
//                        FormValueTransfer transfer = field.getDeclaredAnnotation(FormValueTransfer.class);
//                        if (!transfer.formOnly()) {
//                            field.setAccessible(true);
//                            transformAndSetValue(field, field.get(paramInstance), paramInstance, hasJSONBody);
//                        }
//                    }
                    }
                    request.setAttribute("requestBody", body);
                }
            } catch (Exception e) {
                if (e instanceof FormValueTransformerException) {
                    throw e;
                }
                e.printStackTrace();
                logger.error("Parse dto failed " + e.getLocalizedMessage() + " ...");
                throw new HttpServerErrorException(HttpStatus.FORBIDDEN, "参数解析失败");
            }
        }
        if (paramInstance == null) {
            paramInstance = paramCls.getDeclaredConstructor().newInstance();
        }
        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String name = paramNames.next();
            String value = webRequest.getParameter(name);
            if (value == null) {
                continue;
            }
            try {
                Field field = paramCls.getDeclaredField(name);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.isEnum()) {
                    @SuppressWarnings("unchecked")
                    Class<Enum<?>> enumCls = (Class<Enum<?>>)fieldType;
                    field.set(paramInstance, transEnum(enumCls, value));
                    continue;
                }
//                if (field.isAnnotationPresent(FormValueTransfer.class)) {
                transformAndSetValue(field, value, paramInstance, hasJSONBody);
//                    continue;
//                }
//                field.set(paramInstance, value);
            } catch (Exception e) {
                if (e instanceof FormValueTransformerException) {
                    throwTransformerException((FormValueTransformerException) e, null);
                }
                logger.warn(String.format("Parameter %s was not resolved which value is %s ...", name, value));
            }
        }
        if (!paramCls.isAnnotationPresent(NoValid.class)) {
            Set<ConstraintViolation<Object>> constraints = validator.validate(paramInstance);
            if (!constraints.isEmpty()) {
                ConstraintViolation<Object> violation = constraints.iterator().next();
                throw new HttpServerErrorException(HttpStatus.FORBIDDEN, violation.getMessage());
            }
        }
        return paramInstance;
    }

    private boolean hasJSONBody(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains("application/json");
    }

    private Enum<?> transEnum(Class<Enum<?>> cls, Object value) {
        if (value == null) {
            return null;
        }
        try {
            Method getValueMethod = cls.getMethod("getValue");
            Object typedValue = value;
            for (Enum<?> e: cls.getEnumConstants()) {
                Object curValue = getValueMethod.invoke(e);
                if (!typedValue.getClass().getName().equals(curValue.getClass().getName())) {
                    typedValue = ConvertUtils.convert(typedValue, curValue.getClass());
                }
                if (curValue.equals(typedValue)) {
                    return e;
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
        }
        if (!(value instanceof String)) {
            throwTransformerException(null, "非法的枚举值");
        }
        String strValue = (String) value;
        if (strValue.isEmpty()) {
            return null;
        }
        for (Enum<?> e: cls.getEnumConstants()) {
            if (e.name().equals(strValue)) {
                return e;
            }
        }
        throwTransformerException(null, "枚举值不存在");
        return null;
    }
    @SuppressWarnings("unchecked")
    private void transformAndSetValue(Field field, Object value, Object instance, boolean bodyIsJson) throws FormValueTransformerException {
        FormValueTransfer[] transfers = field.getDeclaredAnnotationsByType(FormValueTransfer.class);
        Object result = value;
        if (transfers != null && transfers.length > 0) {
            List<FormValueTransfer> transfersSorted = Arrays.stream(transfers)
                    .sorted(Comparator.comparingInt(FormValueTransfer::order))
                    .collect(Collectors.toList());
            for (FormValueTransfer transfer: transfersSorted) {
                if (transfer.formOnly() && bodyIsJson) {
                    continue;
                }
//            FormValueTransfer transfer = field.getDeclaredAnnotation(FormValueTransfer.class);
                Class<? extends IFormValueTransformer<?, ?>> transformerClass = transfer.transformer();
                IFormValueTransformer transformer = TransformerCache.get(transformerClass);
                if (transformer == null) {
                    logger.error(String.format("Get transformer for %s failed ...", transformerClass.getCanonicalName()));
                    return;
                }
                try {
                    result = transformer.transform(value);
//                Method transform = transformerClass.getMethod("transform", result.getClass());
//                result = transform.invoke(result);
                } catch (Exception e) {
                    if (e instanceof FormValueTransformerException) {
                        throwTransformerException((FormValueTransformerException) e, transfer.message());
                    }
                    throwTransformerException(null, "参数转换错误");
                }
            }
        } else if (bodyIsJson) {
            return;
        }
        try {
            field.setAccessible(true);
            field.set(instance, result);
        } catch (IllegalAccessException e) {
            throwTransformerException(null ,"参数转换设置错误");
        }
    }

    private void throwTransformerException(FormValueTransformerException e, String message) throws FormValueTransformerException {
        if (StringUtils.hasLength(message)) {
            throw new FormValueTransformerException(message);
        }
        throw e;
    }
}
