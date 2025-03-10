package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.constraints.MinSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.RSADecodeUsePrivateKeyTransformer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
}
