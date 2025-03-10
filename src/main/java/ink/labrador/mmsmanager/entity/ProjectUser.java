package ink.labrador.mmsmanager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import ink.labrador.mmsmanager.constant.UserConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mms_project_user")
@Schema(description = "项目用户信息")
//@Builder
public class ProjectUser {
    @TableId
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "所属工程id")
    private Long projectId;

    @Schema(description = "用户名")
    private String name;

    @Schema(description = "密码摘要")
    private String passwordDigest;

    @Schema(description = "密码盐")
    private String passwordSalt;

    @Schema(description = "用户类型")
    private UserConst.UserType type;

    @Schema(description = "用户状态")
    private UserConst.UserStatus status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
