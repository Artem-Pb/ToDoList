import java.util.*;

public class ToDoList {
    private Map<Integer, Task> questions;
    private final InputHandler inputHandler;

    public ToDoList(InputHandler inputHandler) {
        this.questions = new HashMap<>();
        this.inputHandler = inputHandler;
    }

    // Добавление задачи в список
    public void addTask(Task task, int id) {
        questions.put(id, task);
    }

    public void editMegaQuestions(MegaTask megaTask) {
        int answerForCase;

        while (true) { // меню выбора
            inputHandler.println("Что вы хотите изменить?" +
                    "\n1. Заголовок?" +
                    "\n2.Конкретную подзадачу?" +
                    "\n3.Удалить подзадачу?" +
                    "\n4.Изменить статус подзадачи?" +
                    "\n5 Добавить новую подзадачу?" +
                    "\n6.Выйти без изменений");
            inputHandler.println("Ваш выбор: ");

            String newSubTusk;
            switch (inputHandler.readInt()) {
                case 1: // изменить заголовок
                    inputHandler.println("Введите новый заголовок!");
                    newSubTusk = inputHandler.readLine();
                    if (!newSubTusk.isEmpty()) {
                        megaTask.setTitle(newSubTusk);
                        inputHandler.println("Заголовок задачи изменен!");
                    } else {
                        inputHandler.println("Заголовок не может быть пустым.");
                    }
                    break;
                case 2: // Изменить конкретную подзадачу
                    inputHandler.println("Список подзадач: ");
                    for (int i = 0; i < megaTask.getSubTasks().size(); i++) {
                        inputHandler.println(i + ". " + megaTask.getSubTasks().get(i));
                    }
                    inputHandler.println("Какую подзадачу будем менять?");
                    answerForCase = inputHandler.readInt();
                    if (answerForCase >= 0 && answerForCase < megaTask.getSubTasks().size()) {
                        inputHandler.println("Введите новое условие подзадачи №" + answerForCase + ": ");
                        newSubTusk = inputHandler.readLine();
                        if (!newSubTusk.isEmpty()) {
                            megaTask.updateSubTask(answerForCase, new SimpleTask(newSubTusk, megaTask.getSubTasks().get(answerForCase).getStatus()));
                            inputHandler.println("Подзадача изменина!");
                        } else {
                            inputHandler.println("Подзадача не иожет быть пустой!");
                        }
                    } else {
                        inputHandler.println("Неверный номер подзадачи!");
                    }
                    break;

                case 3: // удалить подзадачу
                    inputHandler.println("Список подзадач: ");
                    for (int i = 0; i < megaTask.getSubTasks().size(); i++) {
                        inputHandler.println(i + ". " + megaTask.getSubTasks().get(i));
                    }

                    inputHandler.println("Какую подзадачу будем менять?");
                    answerForCase = inputHandler.readInt();
                    if (answerForCase >= 0 && answerForCase < megaTask.getSubTasks().size()) {
                        megaTask.removeSubTask(answerForCase);
                        inputHandler.println("Подзадача №" + answerForCase + " удалена!");
                    } else {
                        inputHandler.println("Такой подзадачи нет! Попробуйте снова...");
                    }
                    break;

                case 4: // изменить статус задачи
                    inputHandler.println("Текущий статус задачи: " + megaTask.getStatus());
                    inputHandler.println("Вы выполнили задачу? (Да/Нет): ");
                    String ans = inputHandler.readLine().trim();
                    if (ans.equalsIgnoreCase("Да") || ans.equalsIgnoreCase("Yes")) {
                        megaTask.setStatus(Status.COMPLETED);
                        inputHandler.println("Поздравляю с выполнением Задачи!");
                    } else if (ans.equalsIgnoreCase("Нет") || ans.equalsIgnoreCase("No")) {
                        inputHandler.println("Наберитесь терпения, задачу можно завершить!");
                        megaTask.setStatus(Status.NO_COMPLETED);
                    } else {
                        inputHandler.println("Статус остаётся прежним!");
                    }
                    break;
                case 5: // Добавить новую подзадачу
                    inputHandler.println("Введите описание новой подзадачи: ");
                    newSubTusk = inputHandler.readLine();
                    if (!newSubTusk.isEmpty()) {
                        megaTask.addSubTask(newSubTusk, Status.IN_PROGRESS);
                        inputHandler.println("Подзадача добавлена!");
                    } else {
                        inputHandler.println("Подзадача не может быть пустой");
                    }
                    break;
                case 6 : // выйти без изменений
                    inputHandler.println("Выход из режима редактирования!");
                    return;
                default:
                    inputHandler.println("Невырный выбор. Попробуйте снова!");
                    break;
            }
        }
    }

    public void editQuestions(int id) {
        Task task = questions.get(id);
        if (task == null) {
            inputHandler.println("Задача с таким ID не найдена.");
            return;
        }
        if (task instanceof SimpleTask) {
            inputHandler.println("Введите новое описание задачи: ");
            String newDescription = inputHandler.readLine();
            if (!newDescription.isEmpty()) {
                ((SimpleTask) task).setDescription(newDescription);
                inputHandler.println("Описание задачи изменено!");
            } else {
                inputHandler.println("Описание не может быть пустым!");
            }
        } else if (task instanceof MegaTask) {
            editMegaQuestions((MegaTask) task);
        }
    }

    public Map<Integer, Task> getQuestions() {
        return questions;
    }

    public void printAllQuestions() {
        if (questions.isEmpty()) {
            inputHandler.println("Список задач пуст!");
        } else {
            questions.forEach((id, task) -> inputHandler.println("ID: " + id + " Task: " + task));
        }
    }


    public boolean removeTaskById(int id) {
        return questions.remove(id) != null;
    }

    private String changeDes(String quest) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : quest.toCharArray()) {
            stringBuilder.append(c).append("\u0336");
        }
        return stringBuilder.toString();
    }

    public void changeStatus(int id) {
        Task task = questions.get(id);
        if (task instanceof SimpleTask) {
            SimpleTask simpleTask = (SimpleTask) task;
            simpleTask.setDescription((changeDes(simpleTask.getDescription())));
            simpleTask.setCompleted(true);
        } else if (task instanceof MegaTask) {
            MegaTask megaTask = (MegaTask) task;
            boolean allSubTasksCompleted = true;
            for (SimpleTask subTasks : megaTask.getSubTasks()) {
                if (!subTasks.isCompleted()) {
                    subTasks.setDescription(changeDes(subTasks.getDescription()));
                    subTasks.setCompleted(true);
                }
                if (!subTasks.isCompleted()) {
                    allSubTasksCompleted = false;
                }
            }
            if (allSubTasksCompleted) {
                megaTask.setTitle(changeDes(megaTask.getTitle()));
                megaTask.setCompleted(true);
            }
        }
    }
}
