package dev.shaythesquog.components;

import java.util.Hashtable;

public class Registry<T extends SnowflakeIdentifiable> {
    private final Hashtable<Snowflake, T> heldData = new Hashtable<>();

    public boolean has(T object) {
        return heldData.get(object.id) != null;
    }
    public boolean has(Snowflake objectId) {
        return heldData.containsKey(objectId);
    }
    public void add(T object) {
        heldData.put(object.id, object);
    }
    public boolean remove(Snowflake objectId) {
        heldData.remove(objectId);
        return !has(objectId);
    }
    public boolean remove(T object) {
        return remove(object.id);
    }
    public T get(Snowflake objectId) {
        return heldData.get(objectId);
    }
    public T lookupOrElseAdd(T object) {
        if(has(object.id))
            return heldData.get(object.id);
        add(object);
        return object;
    }
}
