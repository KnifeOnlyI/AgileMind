package com.knife.agilemind.mapper.project;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.mapper.EntityMapper;
import com.knife.agilemind.mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

/**
 * Mapper to manage projects
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
@Mapper(componentModel = "spring", uses = {
    UserMapper.class
})
public interface ProjectMapper extends EntityMapper<ProjectEntity, ProjectDTO> {
    /**
     * Convert a CreateProjectDTO to a ProjectEntity
     *
     * @param dto The DTO to convert
     *
     * @return The entity
     */
    ProjectEntity toEntity(CreateProjectDTO dto);
}
