import java.io.Externalizable;
import java.util.Collections;
import java.util.List;

public interface Task {
    String getDescription();
    Status getStatus();
    void setStatus(Status status);
    int getId();
    default InputHandler  getInstance() {
        return InputHandler.getInstance();
    }
    default List<SimpleTask> getSubTasks() {
        return Collections.emptyList();
    }
}
