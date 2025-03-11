package ink.labrador.mmsmanager.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserConst {
    @AllArgsConstructor
    @Getter
    @Schema(description = "用户状态")
    public enum UserStatus implements BaseEnum<Integer> {
        @Schema(description = "正常")
        NORMAL(1, "正常"),
        @Schema(description = "冻结")
        FROZEN(2, "冻结");
        @JsonValue
        @EnumValue
        private Integer value;
        private String description;
    }

    @AllArgsConstructor
    @Getter
    @Schema(description = "用户类型")
    public enum UserType implements BaseEnum<Integer> {
        @Schema(description = "普通APP用户")
        PROJECT_NORMAL_USER(1, "普通项目用户"),
        @Schema(description = "系统管理员用户")
        SYS_ADMIN_USER(2, "系统管理员用户");
        @JsonValue
        @EnumValue
        private Integer value;
        private String description;
    }
}