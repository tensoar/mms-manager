package ink.labrador.mmsmanager.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.Getter;

@Getter
public enum SaltMixType implements BaseEnum<Integer> {
    PRE(1, "拼接在原始字符串之前"),
    AFTER(2, "拼接在原始字符串之后"),
    CHAR_BY_CHAR(3, "逐字符交叉拼接");

    @JsonValue
    private final Integer value;
    private final String description;
    SaltMixType(Integer value, String desc) {
        this.value = value;
        this.description = desc;
    }
}