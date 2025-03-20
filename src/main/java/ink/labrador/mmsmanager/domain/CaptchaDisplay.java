package ink.labrador.mmsmanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "验证码信息")
public class CaptchaDisplay {
    @Schema(description = "验证码id")
    private String id;
    @Schema(description = "base64格式的图片")
    private String imageInBase64;
}
