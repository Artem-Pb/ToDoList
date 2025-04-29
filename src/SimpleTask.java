import java.io.*;

public class SimpleTask implements Task, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String description;
    private Status status;
    private transient InputHandler inputHandler;
    private static  int idCounter = 0;
    private final int id;
    private boolean completed;


    // конструктор, который создаёт обычную задачу
    public SimpleTask(Status status, InputHandler inputHandler) {
        this.description = getValidDescription(inputHandler);
        this.status = status;
        this.inputHandler = inputHandler;
        this.id = ++idCounter;
    }

    public SimpleTask(String description, Status status) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        this.description = description;
        this.status = status;
        this.inputHandler = null;
        this.id = ++idCounter;
    }

    private Object readResolve() {
        this.inputHandler = InputHandler.getInstance();
        return this;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    // для изменения задачи в процессе работы
    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        this.description = description;
    }

    // возврат задачи, для изменения
    public String getDescription() {
        return description;
    }
    @Override
    // возврат состояния задачи для её изменения
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    // Проверка на валидность вводимых данных. Строка не может быть пустой
    public String getValidDescription(InputHandler inputHandler) {
        String validDes = "";
        while (validDes.isEmpty()) {
            inputHandler.println("Введите вашу задачу: ");
            validDes = inputHandler.readLine();
            if (validDes != null && !validDes.isEmpty()) {
                return validDes;
            } else {
                inputHandler.println("Задача не может быть пустой. Попробуйте снова...");
            }
        }
        return validDes;
    }

    @Override
    public String toString() {
        return "SimpleTask { description: " + description + ", status: " + status + "}";
    }
}
