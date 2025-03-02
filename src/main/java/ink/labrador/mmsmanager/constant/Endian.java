package ink.labrador.mmsmanager.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endian implements BaseEnum<Integer> {
    LE(1, "小端序"),
    BE(2, "大端序");
    @JsonValue
    private final Integer value;
    private final String description;
}