package ink.labrador.mmsmanager.controller;

import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.NotAuth;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @NotAuth
    @PostMapping("login")
    public R login() {
        return R.ok("login ...");
    }
}
