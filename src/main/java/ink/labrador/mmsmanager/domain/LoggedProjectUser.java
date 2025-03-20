package ink.labrador.mmsmanager.domain;

import ink.labrador.mmsmanager.constant.UserConst;
import ink.labrador.mmsmanager.entity.ProjectInfo;
import ink.labrador.mmsmanager.entity.ProjectUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "项目用户登录信息")
@Data
public class LoggedProjectUser {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户名称")
    private String username;

    @Schema(description = "用户状态")
    private UserConst.UserStatus userStatus;

    @Schema(description = "用户类型")
    private UserConst.UserType userType;

    @Schema(description = "项目信息")
    private ProjectInfo projectInfo;

    public static LoggedProjectUser of(ProjectUser user, ProjectInfo projectInfo) {
        LoggedProjectUser u = new LoggedProjectUser();
        u.setId(user.getId());
        u.setProjectInfo(projectInfo);
        u.setUsername(user.getName());
        u.setUserStatus(user.getStatus());
        u.setUserType(user.getType());
        return u;
    }
}
