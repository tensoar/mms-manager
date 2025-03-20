package ink.labrador.mmsmanager.properties;

import ink.labrador.mmsmanager.constant.CaptchaContentType;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("mms.captcha")
public class CaptchaProperties {
    /**
     * redis key前缀
     */
    private String prefix = "captcha:";
    /**
     * 是否区分大小写
     */
    private Boolean caseSensitive = false;
    /**
     * 干扰线条数
     */
    private Integer noiseNumber = 6;
    /**
     * 字符数量
     */
    private Integer size = 4;
    /**
     * 过期时间
     */
    private Integer expireTimeInMinute = 10;
    /**
     * 字体大小
     */
    private Integer fontSize = 26;
    /**
     * 字体路径
     */
    private String ttfFontPath = "";
    /**
     * 图片验证码宽度
     */
    private Integer imageWidth = 98;
    /**
     * 图片验证码高度
     */
    private Integer imageHeight = 48;
    /**
     * 图片验证码背景颜色,Hex表示,默认b0aa93
     */
    private String backgroundHexColor = "b0aa93";
    /**
     * 倾斜角度 0 ~ 180
     */
    private Integer tiltAngle = 30;

    /**
     * 选用与背景颜色相近的同色系字体颜色
     */
    private Boolean nearlyColor = true;

    /**
     * 验证码类型
     */
    private CaptchaContentType contentType = CaptchaContentType.MIXED_CHARACTER;


    @PostConstruct
    public void init() {
        if (backgroundHexColor.startsWith("#")) {
            backgroundHexColor = backgroundHexColor.substring(1);
        }
    }
}
