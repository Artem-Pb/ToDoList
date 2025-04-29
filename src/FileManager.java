import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    private static final String FILE_DAT = "toDoList.dat";
    private static final String INPUT_ERR = "Ошибка при работе с файлом: ";

    private static final FileManager INSTANCE = new FileManager();
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    private FileManager() {
    }

    public static FileManager getInstance() {
        return INSTANCE;
    }

    public void save(ToDoList list) {
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILE_DAT))) {
            writer.writeObject(list);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, INPUT_ERR + FILE_DAT, e);
        }
    }

    public ToDoList load() {
        File file = new File(FILE_DAT);
        if (!file.exists() || file.length() == 0) {
            LOGGER.log(Level.INFO, "Файл отсутствует или пуст. Создаём новый список дел!");
            return new ToDoList(InputHandler.getInstance());
        }
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE_DAT))) {
            return (ToDoList) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, INPUT_ERR + FILE_DAT, e);
            return new ToDoList(InputHandler.getInstance());
        }
    }

    boolean isEmpty() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_DAT))) {
            return reader.readLine() == null;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, INPUT_ERR + FILE_DAT, e);
        }
        return false;
    }
}
