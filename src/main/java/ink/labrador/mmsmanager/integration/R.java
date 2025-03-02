package ink.labrador.mmsmanager.integration;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import ink.labrador.mmsmanager.support.BaseEnum;
import ink.labrador.mmsmanager.support.ObjectMapperSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;

@Schema(description = "返回结果")
public class R <T> {
    @Schema(
            description = "状态码",
            name = "code",
            example = "1000"
    )
    private final Integer code;
    @Schema(
            description = "请求成功时返回数据",
            name = "data"
    )
    private final T data;
    @Schema(
            description = "请求错误或失败时提示信息",
            name = "msg"
    )
    private final String msg;
    private static final ObjectMapper objectMapper = ObjectMapperSupport.newMapperInstance();

    private R(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }
    public T getData() {
        return data;
    }
    public String getMsg() {
        return msg;
    }
    public static <T> R <T> of(Integer code, T data, String msg) {
        return new R<T>(code, data, msg);
    }

    public static <T> R <T> ok(T data) {
        return new R<T>(CODE.OK.getValue(), data, "");
    }

    public static <T> R <T> fail(String message) {
        return fail(CODE.FAILED.getValue(), message);
    }

    public static <T> R <T> fail(Integer code, String message) {
        return new R<T>(code, null, message);
    }

    public static <T> R <T> fail(CODE code, String message) {
        return fail(code.getValue(), message);
    }

    public static <T> R <T> fail(HttpStatus httpStatus, String message) {
        return fail(httpStatus.value(), message);
    }

    public static <T> void responseOk(HttpServletResponse response, T data) throws IOException {
        writeResponse(response, R.ok(data));
    }

    public static void responseFail(HttpServletResponse response, String msg) throws IOException {
        writeResponse(response, R.fail(msg));
    }


    private static <T> void writeResponse(HttpServletResponse response, T object) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(object));
        writer.flush();
    }

    @Getter
    @AllArgsConstructor
    public enum CODE implements BaseEnum<Integer> {
        OK(1000, "ok"),
        FAILED(1001, "failed");
        @JsonValue
        private Integer value;
        private String description;
    }
}
