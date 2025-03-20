package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.constraints.MinSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.RSADecodeUsePrivateKeyTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongSetTransfer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

public class SysUserControllerDto {
    @Data
    @Schema(description = "系统登录")
    public static class LoginDto {
        @Schema(description = "用户名")
        @NotBlank(message = "用户名不能为空")
        @MaxSize(value = 255, message = "用户名过长")
        private String username;

        @Schema(description = "RSA加密后密码")
        @NotBlank(message = "密码不能为空")
        @FormValueTransfer(transformer = RSADecodeUsePrivateKeyTransformer.class, message = "密码非法")
        @MaxSize(value = 255, message = "密码过长")
        @MinSize(value = 5, message = "密码长度不能小于5")
        private String password;
    }

    @Data
    @Schema(description = "系统登录")
    @ToString
    public static class Login {
        @Schema(description = "用户名称名称")
        @NotBlank(message = "用户名称不能为空")
        private String username;

        @Schema(description = "密码,RSA加密")
        @NotBlank(message = "用户密码不能为空")
        @FormValueTransfer(transformer = RSADecodeUsePrivateKeyTransformer.class, formOnly = false)
        private String password;

        @Schema(description = "验证码ID")
        @NotBlank(message = "验证码不能为空")
        private String captchaId;

        @Schema(description = "验证码")
        @NotBlank(message = "验证码不能为空")
        private String captchaAnswer;
    }
}
