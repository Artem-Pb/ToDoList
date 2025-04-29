
public class Main {
    public static void main(String[] args) {
        Main.run();
    }

    private static void run() {
        FileManager fileManager = FileManager.getInstance();
        ToDoList list = fileManager.load();
        InputHandler inputHandler = InputHandler.getInstance();
        if (list == null) {
            list = new ToDoList(inputHandler);
        }


        inputHandler.println(Command.HI.getCommand());
        boolean running = true;
        while (running) {
            Command.printMenu();
            String userInput = inputHandler.readLine();
            try {
                int commandNumber = Integer.parseInt(userInput);
                Command command = Command.getByNumber(commandNumber);
                running = processCommand(command, list);
            } catch (NumberFormatException e) {
                inputHandler.println("Введите правильный номер команды!");
            } catch (IllegalArgumentException e) {
                inputHandler.println("Неизвестная команда! Попробуйте снова!");
            }
        }

        fileManager.save(list);
        inputHandler.println(Command.BY.getCommand());
    }

    public static boolean processCommand(Command command, ToDoList list) {
        InputHandler handler = InputHandler.getInstance();
        int id;
        try {
            switch (command) {
                case Command.ADD_SIMPLE:
                    handler.println(Command.ADD_SIMPLE.getCommand());
                    SimpleTask task = new SimpleTask(Status.NO_COMPLETED, handler);
                    list.addTask(task.getId(), task);
                    handler.println("Задача №" + task.getId() + " успешно создана и добавлена в ваш список дел!" + " Статус: " + task.getStatus());
                    break;
                case Command.ADD_MEGA:
                    handler.println(Command.ADD_MEGA.getCommand());
                    handler.println("Вернуться назад - 0");
                    MegaTask task1 = new MegaTask(Status.NO_COMPLETED, handler);
                    list.addTask(task1.getId(), task1);
                    handler.println("Задача №" + task1.getId() + " успешно создана и добавлена в ваш список дел!" + " Статус: " + task1.getStatus());
                    break;
                case Command.SHOW_TODO:
                    handler.println(Command.SHOW_TODO.getCommand());
                    list.printAllQuestions();
                    break;
                case Command.DEL_TASK:
                    handler.println(Command.DEL_TASK.getCommand());
                    handler.println(Command.WHAT_TASK.getCommand());
                    list.printAllQuestions();
                    list.removeTaskById(handler.readInt());
                    break;
                case Command.DEL_SIMPLE:
                    handler.println(Command.DEL_SIMPLE.getCommand());
                    handler.println(Command.WHAT_TASK.getCommand());
                    list.printAllQuestions();
                    id = handler.readInt();
                    Task task2 = list.getQuestion(id);
                    if (task2 instanceof SimpleTask) {
                        list.removeTaskById(id);
                    } else if (task2 instanceof MegaTask) {
                        handler.println(Command.WHAT_SIMPLE_TASK.getCommand());
                        ((MegaTask) task2).removeSubTask(handler.readInt());
                    }
                    break;
                case Command.UPDATE_TASK:
                    handler.println(Command.UPDATE_TASK.getCommand());
                    id = handler.readInt();
                    list.editQuestions(id);
                    break;
                case Command.UPDATE_STATUS_SIMPLE:
                    list.printAllQuestions();
                    handler.println("Введите № Мега задачи, статус подзадачи в которой, мы будем менять???");
                    id = handler.readInt();
                    handler.println(Command.UPDATE_STATUS_SIMPLE.getCommand());
                    int subTaskId = handler.readInt();
                    list.changeStatus(id, subTaskId);
                    handler.println("Статус изменён!");
                    break;
                case Command.DEL_TODO:
                    handler.println(Command.DEL_TODO.getCommand());
                    handler.println("Вы хотите...");
                    list = list.delToDo(list);
                    break;
                case Command.SHOW_COMPLETE:
                    list.showCompleteTask();
                    break;
                case Command.EXIT:
                    handler.println("Программа завершает свою работу! До встречи!");
                    return false;
            }
        } catch (IllegalArgumentException e) {
            handler.println("Неизвестная команда! Попробуйте снова!");
        } catch (Exception e) {
            handler.println("Произошла ошибка: " + e.getMessage());
        }
        return true;
    }
}
