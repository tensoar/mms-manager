package ink.labrador.mmsmanager.controller;

import ink.labrador.mmsmanager.domain.Captcha;
import ink.labrador.mmsmanager.domain.CaptchaDisplay;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import ink.labrador.mmsmanager.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common")
@Tag(name = "通用接口")
@AllArgsConstructor
public class CommonController {
    private final CaptchaService captchaService;

    @GetMapping("captcha")
    @ResponseBody
    @Operation(summary = "获取验证码")
    @NotAuth
    public R<CaptchaDisplay> getCaptcha() {
        Captcha captcha = captchaService.createCaptcha();
        System.out.println(captcha.getId() + ": " + captcha.getAnswer());
        return R.ok(captchaService.toCaptchaDisplay(captcha));
    }
}
