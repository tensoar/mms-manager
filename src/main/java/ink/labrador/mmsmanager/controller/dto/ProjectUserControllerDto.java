package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.constraints.MinSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

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


    @Data
    @Schema(description = "删除项目用户")
    public static class DelUser {
        @Schema(description = "用户id列表,可多个")
        @FormValueTransfer(transformer = StringToLongSetTransfer.class)
        @MinSize(value = 1, message = "删除用户不能为空")
        @NotNull(message = "删除用户不能为空")
        private Set<Long> ids;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(description = "查询项目用户信息")
    public static class ListUserDto extends CommonDto.PageDto {
        @Schema(description = "用户名称名称,可空,模糊查询")
//        @MaxSize(value = 255, message = "名称长度不能超过255")
        private String username;

        @Schema(description = "项目ID,可空")
//        @Pattern(regexp = "^(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})$", message = "IP地址格式不正确")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        private Long projectId;

        @Schema(description = "用户状态,可空")
        private UserConst.UserStatus userStatus;
    }

    @Data
    @Schema(description = "项目用户登录")
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
