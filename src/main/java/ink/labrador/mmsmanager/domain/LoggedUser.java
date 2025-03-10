package ink.labrador.mmsmanager.domain;

import ink.labrador.mmsmanager.constant.UserConst;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoggedUser {
    private Long userId;
    private String username;
    private UserConst.UserType type;
    private String tokenName;
    private String tokenValue;
}
