import java.util.HashMap;
import java.util.Map;

public class Service {

    private final String id;
    private final Map<String, Object> data;

    public Service(String id) {
        this.id = id;
        this.data = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public boolean hasData(String key) {
        return this.data.containsKey(key);
    }

    public void setData(String key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public String toString() {
        return "Service " + this.id + " with data : \n" + this.data.toString();
    }

}
