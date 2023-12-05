package fr.jdegut.main.env;

import java.util.HashMap;
import java.util.Map;

public class Service {

    private final String id;
    private final Map<DataType, Object> data;

    public Service(String id) {
        this.id = id;
        this.data = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public Object getData(DataType key) {
        return this.data.get(key);
    }

    public boolean hasData(DataType key) {
        return this.data.containsKey(key);
    }

    public void setData(DataType key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public String toString() {
        return this.id;
    }
}
