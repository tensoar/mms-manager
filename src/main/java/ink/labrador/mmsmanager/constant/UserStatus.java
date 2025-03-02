package ink.labrador.mmsmanager.constant;

import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus implements BaseEnum<Integer> {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结");
    private Integer value;
    private String description;
}
