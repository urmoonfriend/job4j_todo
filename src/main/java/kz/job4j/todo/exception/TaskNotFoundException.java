package kz.job4j.todo.exception;

public class TaskNotFoundException extends Throwable {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
