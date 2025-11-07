package ru.baza;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class RulesLoader {

    public static final String DEFAULT_RULES_PATH = "rules.json";
    public static final List<Rule> RULES = loadRules(DEFAULT_RULES_PATH);

    private RulesLoader() {}

    /**
     * Загружает правила из JSON-файла
     *
     * @param filePath путь к JSON файлу
     * @return список правил, никогда не возвращает null
     */
    public static List<Rule> loadRules(String filePath) {
        var path = Path.of(filePath);

        if (!Files.exists(path)) {
            System.err.println("❌ Файл не найден: " + filePath);
            return Collections.emptyList();
        }

        try (var reader = Files.newBufferedReader(path)) {
            var gson = new Gson();
            var ruleListType = new TypeToken<List<Rule>>() {}.getType();
            List<Rule> rules = gson.fromJson(reader, ruleListType);

            if (rules == null) {
                System.out.println("⚠️ Файл пуст или некорректный: " + filePath);
                return Collections.emptyList();
            }

            System.out.println("✅ Загружено правил: " + rules.size());
            return Collections.unmodifiableList(rules);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + filePath, e);
        }
    }
}
