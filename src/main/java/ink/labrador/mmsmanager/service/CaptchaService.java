package ink.labrador.mmsmanager.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import ink.labrador.mmsmanager.constant.CaptchaContentType;
import ink.labrador.mmsmanager.domain.Captcha;
import ink.labrador.mmsmanager.domain.CaptchaDisplay;
import ink.labrador.mmsmanager.properties.CaptchaProperties;
import ink.labrador.mmsmanager.util.ColorUtil;
import ink.labrador.mmsmanager.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {
    private final Logger logger = LoggerFactory.getLogger(CaptchaService.class);
    private final Cache<String, String> cache;
    private final CaptchaProperties captchaProperties;
    private Font font = null;
    public CaptchaService(
            CaptchaProperties captchaProperties
    ) {
        this.captchaProperties = captchaProperties;
        cache = Caffeine.newBuilder()
                .expireAfterWrite(captchaProperties.getExpireTimeInMinute(), TimeUnit.MINUTES)
                .maximumSize(100_000)
                .build();
        if (StringUtils.hasLength(captchaProperties.getTtfFontPath())) {
            logger.info("Load font: " + captchaProperties.getTtfFontPath());
            try {
                InputStream inputStream = FileUtil.loadFileAsInputStream(captchaProperties.getTtfFontPath());
                if (inputStream == null) {
                    logger.warn("Captcha font file was not found ...");
                } else {
                    this.font = Font.createFont(Font.TRUETYPE_FONT, inputStream)
                            .deriveFont(Font.BOLD + Font.ITALIC, captchaProperties.getFontSize());
                    inputStream.close();
                }
            } catch (IOException | FontFormatException e) {
                logger.error("Load font failed ...");
            }
        }
        if (this.font == null) {
            String defaultFontName = "font/consolab.ttf";
            logger.info("Load default font: ClassPath:" + defaultFontName);
            try {
                ClassPathResource fontResource = new ClassPathResource(defaultFontName);
                this.font = Font.createFont(Font.TRUETYPE_FONT, fontResource.getInputStream())
                        .deriveFont(Font.BOLD + Font.ITALIC, captchaProperties.getFontSize());
            } catch (IOException | FontFormatException e) {
                logger.error("Load default font failed ...");
            }
        }
        if (this.font == null) {
            logger.info("Load default system font ...");
            this.font = new Font(null, Font.BOLD + Font.ITALIC, captchaProperties.getFontSize());
        }
    }


    /**
     * 获取验证码
     * @param captchaContentType 验证码类型
     * @param size 验证码长度,当验证码类型是表达式时,此设置无效
     * @return 验证码
     */
    public Captcha createCaptcha(CaptchaContentType captchaContentType, int size) {
        Captcha captcha = Captcha.init(captchaContentType, size);
        cache.put(captcha.getId(), captcha.getAnswer());
        return captcha;
    }

    public Captcha createCaptcha(CaptchaContentType type) {
        return createCaptcha(type, captchaProperties.getSize());
    }

    public Captcha createCaptcha() {
        return createCaptcha(captchaProperties.getContentType(), captchaProperties.getSize());
    }

    public CaptchaDisplay toCaptchaDisplay(Captcha captcha) {
        CaptchaDisplay display = new CaptchaDisplay();
        display.setId(captcha.getId());
        display.setImageInBase64(toImageInBase64(captcha));
        return display;
    }

    public CaptchaDisplay toCaptchaDisplay(Captcha captcha, int width, int height) {
        CaptchaDisplay display = new CaptchaDisplay();
        display.setId(captcha.getId());
        display.setImageInBase64(toImageInBase64(captcha, width, height));
        return display;
    }

    public boolean verify(String id, String answer) {
        String value = cache.getIfPresent(id);
        if (!StringUtils.hasText(value)) {
            return false;
        }
        if (captchaProperties.getCaseSensitive()) {
            return value.equals(answer);
        } else {
            return value.equalsIgnoreCase(answer);
        }
    }

    public void remove(String id) {
        cache.invalidate(id);
    }

    public String toImageInBase64(Captcha captcha) {
        return toImageInBase64(captcha, captchaProperties.getImageWidth(), captchaProperties.getImageHeight());
    }

    public String toImageInBase64(Captcha captcha, int width, int height) {
        int size = captcha.getContent().length();
        // 创建空白图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图片画笔
        Graphics2D graphic = image.createGraphics();
        // 设置抗锯齿
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画笔颜色
        Color bgColor = new Color(Integer.parseInt(captchaProperties.getBackgroundHexColor(), 16));
        graphic.setColor(bgColor);
        // 绘制矩形背景
        graphic.fillRect(0, 0, width, height);
        // 画随机字符
        SecureRandom ran = new SecureRandom();

        //graphic.setBackground(Color.WHITE);

        // 计算每个字符占的宽度，这里预留一个字符的位置用于左右边距
        int codeWidth = width / (size + 1);
        // 字符所处的y轴的坐标
        int y = height * 3 / 4;

        for (int i = 0; i < size; i++) {
            Color color = captchaProperties.getNearlyColor() ? ColorUtil.randomColorNearly(bgColor) : ColorUtil.getRandomColor();
            // 设置随机颜色
            graphic.setColor(color);

            Font curFont = font;
            if (captchaProperties.getTiltAngle() > 0) {
                // 随机一个倾斜的角度 -45到45度之间
                int theta = ran.nextInt(captchaProperties.getTiltAngle());
                // 随机一个倾斜方向 左或者右
                theta = ran.nextBoolean() ? theta : -theta;
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(Math.toRadians(theta), 0, 0);
                curFont = font.deriveFont(affineTransform);
            }
            // 设置字体大小
            graphic.setFont(curFont);

            // 计算当前字符绘制的X轴坐标
            int x = (i * codeWidth) + (codeWidth / 2);

            // 得到字符文本
            String code = String.valueOf(captcha.getContent().charAt(i));
            // 画字符
            graphic.drawString(code, x, y);

        }
        // 画干扰线
        for (int i = 0; i < captchaProperties.getNoiseNumber(); i++) {
            // 设置随机颜色
            graphic.setColor(ColorUtil.getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(width), ran.nextInt(height), ran.nextInt(width), ran.nextInt(height));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
            String b64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            outputStream.close();
            return "data:image/png;base64," + b64;
        } catch (IOException e) {
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            logger.error("Generate base64 image failed ...");
            return "";
        }
    }

}
