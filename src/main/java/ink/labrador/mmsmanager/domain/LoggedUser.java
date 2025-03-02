package ink.labrador.mmsmanager.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoggedUser {
    private Long userId;
    private String username;
    private Integer type;
    private String tokenName;
    private String tokenValue;
}
