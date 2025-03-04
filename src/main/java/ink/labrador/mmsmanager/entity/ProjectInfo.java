package ink.labrador.mmsmanager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("mms_project_info")
@Schema(description = "项目信息")
public class ProjectInfo {
    @TableId
    private Long id;

    @Schema(description = "项目名称")
    private String projectName;
    @Schema(description = "是否使用https")
    private Boolean useHttps;
    @Schema(description = "IPV4地址")
    private String ipv4;
    @Schema(description = "端口")
    private Integer port;
}
