package tuziks.kav.kav.model;

/**
 * Created with IntelliJ IDEA.
 * Date: 12.23.12
 * Time: 00:11
 */
public class KeyValue {
    private int Id;
    private String Key;
    private String Value;


    public KeyValue(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public KeyValue(int id, String key, String value) {
        setId(id);
        setKey(key);
        setValue(value);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "{" + KeyValue.class.getName() + " id:" + getId() + "; key:" + getKey() + "; value:" + getValue() + "}";
    }
}
