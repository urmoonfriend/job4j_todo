package kz.job4j.todo.mapper;

import kz.job4j.todo.model.dto.TaskDto;
import kz.job4j.todo.model.entity.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto getModelFromEntity(Task task);

    @InheritInverseConfiguration
    Task detEntityFromDto(TaskDto taskDto);
}
