package ink.labrador.mmsmanager.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ByteLen implements BaseEnum<Integer> {
    BYTE(1, "单字节"),
    SHORT(2, "短整形双字节"),
    INT3(3, "整形三字节"),
    INT(4, "整形四字节"),
    INT6(6, "长整型六字节"),
    LONG(8, "长整型八字节");

    @JsonValue
    private final Integer value;
    private final String description;

    public static ByteLen of(int i) {
        switch (i) {
            case 1: return BYTE;
            case 2: return SHORT;
            case 3: return INT3;
            case 4: return INT;
            case 6: return INT6;
            case 8: return LONG;
            default: throw new RuntimeException("字节数非法,必须是1/2/3/4/6/8");
        }
    }
}