package ru.baza.adapter;

import com.google.gson.*;
import ru.baza.Answer;

import java.lang.reflect.Type;

public class AnswerAdapter implements JsonSerializer<Answer>, JsonDeserializer<Answer> {
    @Override
    public JsonElement serialize(Answer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getText());
    }

    @Override
    public Answer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String value = json.getAsString();
        for (Answer a : Answer.values()) {
            if (a.getText().equalsIgnoreCase(value)) {
                return a;
            }
        }
        throw new JsonParseException("Неизвестное значение Answer: " + value);
    }
}
