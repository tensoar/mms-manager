package ink.labrador.mmsmanager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mms_user")
public class User {
    @TableId
    private Long id;

    private String name;
    private String passwordDigest;
    private String passwordSalt;
    private Integer type;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
