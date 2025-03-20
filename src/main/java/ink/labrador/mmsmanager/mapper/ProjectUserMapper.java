package ink.labrador.mmsmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.labrador.mmsmanager.entity.ProjectUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectUserMapper extends BaseMapper<ProjectUser> {
    Page<ProjectUser> listJoinProject(IPage<ProjectUser> page, @Param("user") ProjectUser user);
}
