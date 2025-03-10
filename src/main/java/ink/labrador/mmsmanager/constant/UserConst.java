package ink.labrador.mmsmanager.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserConst {
    @AllArgsConstructor
    @Getter
    public enum UserStatus implements BaseEnum<Integer> {
        NORMAL(1, "正常"),
        FROZEN(2, "冻结");
        @JsonValue
        @EnumValue
        private Integer value;
        private String description;
    }

    @AllArgsConstructor
    @Getter
    public enum UserType implements BaseEnum<Integer> {
        APP_NORMAL_USER(1, "普通APP用户");
        @JsonValue
        @EnumValue
        private Integer value;
        private String description;
    }
}