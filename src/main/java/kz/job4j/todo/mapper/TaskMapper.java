package kz.job4j.todo.mapper;

import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto getModelFromEntity(Task task);

    @InheritInverseConfiguration
    @Mapping(source = "user", target = "user")
    @Mapping(source = "priority", target = "priority")
    @Mapping(source = "categories", target = "categories")
    Task getEntityFromDto(TaskDto taskDto);
}
