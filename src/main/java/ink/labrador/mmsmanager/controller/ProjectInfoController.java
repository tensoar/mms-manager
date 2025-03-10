package ink.labrador.mmsmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.labrador.mmsmanager.controller.dto.ProjectInfoControllerDto;
import ink.labrador.mmsmanager.entity.ProjectInfo;
import ink.labrador.mmsmanager.integration.R;
import ink.labrador.mmsmanager.integration.annotation.Dto;
import ink.labrador.mmsmanager.service.ProjectInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@Tag(name = "项目信息相关接口")
@RequestMapping("project")
public class ProjectInfoController {
    private final ProjectInfoService projectInfoService;

    @PostMapping("save")
    @Operation(summary = "添加或修改项目", description = "添加或修改项目,添加时id给空，修改时id给要修改的项目的id")
    @ResponseBody
    public R<String> addProject(@Dto ProjectInfoControllerDto.SaveProjectDto dto) {
        ProjectInfo info = null;
        if (dto.getId() != null) {
            info = projectInfoService.getOne(wrapper -> wrapper.eq(ProjectInfo::getId, dto.getId()));
            if (info == null) {
                return R.fail("项目不存在");
            }
        }
        ProjectInfo sameNamePJ = projectInfoService.getOne(wrapper -> wrapper.eq(ProjectInfo::getProjectName, dto.getProjectName()));
        if ((sameNamePJ != null && dto.getId() == null) || (dto.getId() != null && !Objects.requireNonNull(sameNamePJ).getId().equals(dto.getId()))) {
            return R.fail("项目名称已存在");
        }
        if (info == null) {
            info = new ProjectInfo();
        }
        info.setProjectName(dto.getProjectName());
        info.setUseHttps(dto.getUseHttps());
        info.setIpv4(dto.getIp());
        info.setPort(dto.getPort());
        projectInfoService.saveOrUpdate(info);
        return R.ok("操作成功");
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除项目")
    @ResponseBody
    public R<String> deleteProject(@Dto ProjectInfoControllerDto.DeleteProjectDto dto) {
        projectInfoService.removeByIds(dto.getIds());
        return R.ok("操作成功");
    }

    @GetMapping("list")
    @Operation(summary = "查询项目")
    @ResponseBody
    public R<Page<ProjectInfo>> listProject(@Dto ProjectInfoControllerDto.ListProjectDto dto) {
        OrderItem orderItem = OrderItem.desc("create_time");
        if (!StringUtils.hasLength(dto.getProjectName()) && !StringUtils.hasLength(dto.getIp())) {
            return R.ok(projectInfoService.page(dto.mapPage(orderItem)));
        }
        LambdaQueryWrapper<ProjectInfo> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasLength(dto.getProjectName())) {
            queryWrapper.like(ProjectInfo::getProjectName, '%' + dto.getProjectName() + "%");
        }
        if (StringUtils.hasLength(dto.getIp())) {
            queryWrapper.like(ProjectInfo::getIpv4, '%' + dto.getIp() + "%");
        }
        return R.ok(projectInfoService.page(dto.mapPage(orderItem), queryWrapper));
    }
}
