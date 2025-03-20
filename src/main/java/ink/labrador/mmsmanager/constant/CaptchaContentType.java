package ink.labrador.mmsmanager.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CaptchaContentType implements BaseEnum<Integer> {
    /**
     * 仅包含数字
     */
    NUMBER_ONLY(1, "仅包含数字"),
    /**
     * 仅包含字母
     */
    ALPHABET_ONLY(2, "仅包含字母"),
    /**
     * 字母数字混合
     */
    MIXED_CHARACTER(3, "母数字混合"),
    /**
     * 表达式
     */
    EXPRESSION(4, "表达式");
    @JsonValue
    private Integer value;
    private String description;
}
