package kz.job4j.todo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private String created;
    private boolean done;
}
