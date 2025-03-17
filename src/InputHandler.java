import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputHandler {
    private final BufferedReader reader;
    private static InputHandler instance;

    private InputHandler()  {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    public String readLine() {
        String input = "";
        while (input.isEmpty()) {
            try {
                input = reader.readLine();
            } catch (IOException e) {
                System.out.println("Строка не может быть пустой");
            }
        }
        return input;
    }

    public int readInt() {
        int input = -1;
        while (true) {
            try {
                input = Integer.parseInt(reader.readLine());
                break;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Необходимо ввести число!");
            }
        }
        return input;
    }

    public void print(String mes) {
        System.out.print(mes);
    }

    public void println(String mes) {
        System.out.println(mes);
    }
}
