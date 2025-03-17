import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MegaTask implements Task {
    private String title;
    private List<SimpleTask> subTasks;
    private Status status;
    private final InputHandler inputHandler;
    private static int idCounter = 0;
    private final int id;
    private boolean completed;

    // конструктор для создания Задачи с подзадачами
    public MegaTask(Status status, InputHandler inputHandler) throws IOException {
        this.subTasks = getValidMegaDescription(inputHandler);
        this.status = status;
        this.title = getValidTitle(inputHandler);
        this.inputHandler = inputHandler;
        this.id = ++idCounter;
        updateStatus();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       if (title != null && !title.isEmpty()) {
           this.title = title;
       } else {
           inputHandler.println("Заголовок не может быть пустым!");
       }
    }


    @Override
    public String getDescription() {
        StringBuilder stringBuilder = new StringBuilder(title +  " в статусе " + status.toString() +"\n");
        for (SimpleTask task : subTasks) {
            stringBuilder.append("— ").append(task.getDescription()).append(" [").append(task.getStatus()).append("]\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public List<SimpleTask> getSubTasks() {
        return subTasks != null ? subTasks : Collections.emptyList();
    }

    public void setSubTasks(List<SimpleTask> subTasks) {
        if (subTasks != null && !subTasks.isEmpty()) {
            this.subTasks = new ArrayList<>(subTasks);
        }
        else {
            inputHandler.println("Новый список подзадач не может быть пустым!");
        }
    }

    public void updateSubTask(int index, SimpleTask newSubTask) {
        if (index >= 0 && index < subTasks.size()) {
            subTasks.set(index, newSubTask);
            updateStatus();
        } else {
            inputHandler.println("Неверный индекс подзадачи!");
        }
    }

    public void addSubTask(String description, Status status) {
        subTasks.add(new SimpleTask(description, status));
        updateStatus();
    }

    public void removeSubTask (int index) {
        if (index >= 0 && index < subTasks.size()) {
            subTasks.remove(index);

        } else {
            inputHandler.println("Неверный индекс подзадачи!");
        }
    }
    
    @Override
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

    // обновление статуса всей задачи
    private void updateStatus() {
        if (subTasks.isEmpty()) {
            status = Status.IN_PROGRESS;
            return;
        }

        boolean allComplete = true;
        for (SimpleTask subTask : subTasks) {
            if (subTask.getStatus() != Status.COMPLETED) {
                allComplete = false;
                break;
            }
        }

        status = allComplete ? Status.COMPLETED : Status.IN_PROGRESS;
    }
    // заголовок
    public String getValidTitle(InputHandler inputHandler) {
        inputHandler.println("Введите заголовок MegaTask: ");
        return inputHandler.readLine();
    }

// ввод одной большой задачи с подзадачами
    public List<SimpleTask> getValidMegaDescription(InputHandler inputHandler) {
        List<SimpleTask> subTasks = new ArrayList<>();
        inputHandler.println("Сколько будет подзадач в задаче?");
        int countQuest = Integer.parseInt(inputHandler.readLine());

        for (int i = 0; i < countQuest; i++) {
            inputHandler.println("Введите описание подзадачи " + (i + 1) +": ");
            String description = inputHandler.readLine();
            inputHandler.println("Введите статус подзадачи: ");
            Status status = Status.valueOf(inputHandler.readLine().toUpperCase());
            subTasks.add(new SimpleTask(description, status));
        }

        return subTasks;
    }

    @Override
    public String toString() {
        return "MegaTask { title ='" + title + "', status =" + status +", subTasks=" + subTasks + ", id: " + id + "}";

    }
}
