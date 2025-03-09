package ink.labrador.mmsmanager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project_user")
@AllArgsConstructor
@Tag(name = "项目用户相关接口")
public class ProjectUserController {
}
