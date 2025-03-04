package ink.labrador.mmsmanager.controller.dto;

import ink.labrador.mmsmanager.integration.constraints.MaxSize;
import ink.labrador.mmsmanager.integration.transfer.annotation.FormValueTransfer;
import ink.labrador.mmsmanager.integration.transfer.transformer.AnyToBooleanTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToIntegerTransformer;
import ink.labrador.mmsmanager.integration.transfer.transformer.StringToLongTransformer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

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
        @Pattern(regexp = "^(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})\\\\.(\\\\d{1,3})$")
        private String ip;

        @Schema(description = "端口")
        @FormValueTransfer(transformer = StringToIntegerTransformer.class)
        private Integer port;

        @Schema(description = "是否使用https")
        @FormValueTransfer(transformer = AnyToBooleanTransformer.class)
        private Boolean useHttps;
    }
}
