public enum Command {
    HI(99, "Привет, путник! Я рад, что ты обратился за помощью в ведении своих дел. Значит ты на верном пути. Здесь ты можешь упорядочить свои цели, задачи и просто повседневную рутину. Желаю тебе успехов, а теперь..." ),
    BY(999, "Возвращайся завтра, с результатами!"),
    WHAT_TASK(9999, "Удалить задачу №... ?"),
    WHAT_SIMPLE_TASK(11111, "Удалить подзадачу №... ?"),
    ADD_SIMPLE(1, "Создать обычную задачу"),
    ADD_MEGA(2, "Создать Мега задачу"),
    DEL_TASK(3, "Удалить задачу"),
    DEL_SIMPLE(4, "Удалить конкретную подзадачу"),
    UPDATE_TASK(5, "Изменить задачу №... "),
    UPDATE_STATUS_SIMPLE(6, "Изменить статус конкретной подзадачи"),
    DEL_TODO(7, "Удалить список дел(Начать с чистого листа)"),
    SHOW_TODO(8, "Показать имеющиеся дела"),
    SHOW_COMPLETE(9, "Показать выполненные дела"),
    EXIT(10, "Завершить работу! (Перейти от слов к делу)"),
    ;

    private final String command;
    private final int number;
    Command(int number, String command) {
        this.command = command;
        this.number = number;
    }
    public String getCommand() {
        return command;
    }

    public int getNumber() {
        return number;
    }

    public static Command getByNumber(int number) {
        for (Command cmd : Command.values()) {
            if (cmd.getNumber() == number) {
                return cmd;
            }
        }
        throw new IllegalArgumentException("Команда с таким номером не найдена");
    }

    public static void printMenu() {
        for (Command cmd : Command.values()) {
            if (cmd.getNumber() < 99) {
                System.out.println(cmd.getNumber() + ". " + cmd.getCommand());
            }
        }
    }
}
