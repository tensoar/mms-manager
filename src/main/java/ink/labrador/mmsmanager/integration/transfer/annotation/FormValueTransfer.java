package ink.labrador.mmsmanager.integration.transfer.annotation;

import ink.labrador.mmsmanager.integration.transfer.transformer.IFormValueTransformer;

import java.lang.annotation.*;

/**
 * 表单字段转换注解,如果Dto对象的字段应用了改注解,则对字段执行transformer进行转换.
 * <br/><br/>
 * formOnly规则:
 * <br/>
 *      若formOnly为true(默认),则只有前端传输的类型为非application/json(如form-data/www-url-form-encoded/url)
 *      时,才对字段执行操作,此时字段的初始类型一定为string
 * <br/>
 *      若formOnly为false,则前端传输的类型为application/json,则先对JSON执行parse操作,然后对字段执行此转换操作
 * <br/><br/>
 * order:
 * <br/>
 *      order为转换规则执行顺序,当应用了多个注解时,按照order的顺序进行执行,前一个的输出会是后一个转换的输入,order越小优先级越高
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FormValueTransfers.class)
public @interface FormValueTransfer {
    Class<? extends IFormValueTransformer<?, ?>> transformer();
    boolean formOnly() default true;
    int order() default -1;
    String message() default "";
}