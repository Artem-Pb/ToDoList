import java.util.*;

public class TaskKey {
    private final int id;
    private final Status status;

    public TaskKey(int id, Status status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskKey taskKey = (TaskKey) obj;
        return (id == taskKey.id) && Objects.equals(status, taskKey.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "TaskKey{id=" + id + ", status=" + status + "}";
    }
}
