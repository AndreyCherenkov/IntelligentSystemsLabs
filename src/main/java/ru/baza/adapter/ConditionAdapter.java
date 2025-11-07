package ru.baza.adapter;

import com.google.gson.*;
import ru.baza.Condition;

import java.lang.reflect.Type;

public class ConditionAdapter implements JsonSerializer<Condition>, JsonDeserializer<Condition> {

    @Override
    public JsonElement serialize(Condition src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getName());
    }

    @Override
    public Condition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String value = json.getAsString();
        for (Condition c : Condition.values()) {
            if (c.getName().equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new JsonParseException("Неизвестное значение Condition: " + value);
    }
}