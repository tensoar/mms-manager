package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.AnyToBooleanTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.RSADecodeUsePrivateKeyTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToIntegerTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongTransformer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

public class ProjectUserControllerDto {
    @Data
    @Schema(description = "保存项目用户信息")
    public static class SaveUserDto {
        @Schema(description = "用户id,新增用户时给空")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        private Long userId;

        @Schema(description = "用户名称")
        @NotBlank(message = "用户名称不能为空")
        @MaxSize(value = 255, message = "用户名称长度不能超过255")
        private String username;

        @Schema(description = "关联项目id")
        @NotNull(message = "关联项目id不能为空")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        private Long projectId;

        @Schema(description = "RSA加密后用户密码")
        @NotNull(message = "密码不能为空")
        @FormValueTransfer(transformer = RSADecodeUsePrivateKeyTransformer.class, formOnly = false)
        private String password;

        @Schema(description = "用户类型，默认给1即可")
        @NotNull(message = "用户类型不能为空")
        private UserConst.UserType userType;

        @Schema(description = "用户状态")
        @NotNull(message = "用户状态不能为空")
        private UserConst.UserStatus userStatus;
    }
}
