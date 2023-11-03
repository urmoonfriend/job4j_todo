package kz.job4j.todo.model.dto;

import kz.job4j.todo.model.entity.Category;
import kz.job4j.todo.model.entity.Priority;
import kz.job4j.todo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
    private User user;
    private Priority priority;
    private List<Integer> categoryIds;
    private List<Category> categories;
}
