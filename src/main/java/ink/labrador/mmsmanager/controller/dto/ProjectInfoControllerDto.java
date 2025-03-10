package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.constraints.MinSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.AnyToBooleanTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToIntegerTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongSetTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongTransformer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

public class ProjectInfoControllerDto {

    @Data
    @Schema(description = "添加项目信息")
    public static class SaveProjectDto {
        @Schema(description = "项目id,新增项目时给空")
        @FormValueTransfer(transformer = StringToLongTransformer.class)
        private Long id;

        @Schema(description = "项目名称")
        @NotBlank(message = "项目名称不能为空")
        @MaxSize(value = 255, message = "项目名称长度不能超过255")
        private String projectName;

        @Schema(description = "IPV4地址")
        @Pattern(regexp = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$", message = "IP地址格式不正确")
        @NotNull(message = "IP地址不能为空")
        private String ip;

        @Schema(description = "端口")
        @FormValueTransfer(transformer = StringToIntegerTransformer.class, message = "端口号必须是数字")
        @Range(min = 80, max = 65535, message = "端口号范围需在[80, 65535]")
        @NotNull(message = "端口不能为空")
        private Integer port;

        @Schema(description = "是否使用https")
        @FormValueTransfer(transformer = AnyToBooleanTransformer.class)
        @NotNull(message = "是否使用HTTPS不能为空")
        private Boolean useHttps;
    }

    @Data
    @Schema(description = "删除项目信息")
    public static class DeleteProjectDto {
        @Schema(description = "项目id列表,可多个")
        @FormValueTransfer(transformer = StringToLongSetTransfer.class)
        @MinSize(value = 1, message = "删除项目不能为空")
        @NotNull(message = "删除项目不能为空")
        private Set<Long> ids;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Schema(description = "查询项目信息")
    public static class ListProjectDto extends CommonDto.PageDto {
        @Schema(description = "项目名称,可空,模糊查询")
        @MaxSize(value = 255, message = "项目名称长度不能超过255")
        private String projectName;

        @Schema(description = "IPV4地址,可空,模糊查询")
        @Pattern(regexp = "^(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})$", message = "IP地址格式不正确")
        private String ip;
    }
}
