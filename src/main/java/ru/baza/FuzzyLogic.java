package ru.baza;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static ru.baza.SelectionOptions.*;

public class FuzzyLogic {

    private static final Map<String, Double> SIMILARITY_MAP;

    static {
        var map = new HashMap<String, Double>();
        addSimilarity(map, MEDIUM.text, HIGH.text, 0.6);
        addSimilarity(map, MEDIUM.text, LONG.text, 0.6);
        addSimilarity(map, SHORT.text, MEDIUM.text, 0.7);
        addSimilarity(map, SMALL_BUSINESS.text, PERSONAL.text, 0.5);
        addSimilarity(map, SMALL_BUSINESS.text, LARGE_PROJECT.text, 0.5);
        SIMILARITY_MAP = Collections.unmodifiableMap(map);
    }

    private FuzzyLogic() {}

    private static void addSimilarity(Map<String, Double> map, String a, String b, double value) {
        var key1 = makeKey(a, b);
        var key2 = makeKey(b, a);
        map.put(key1, value);
        map.put(key2, value);
    }

    private static String makeKey(String a, String b) {
        return a.toLowerCase(Locale.ROOT) + "|" + b.toLowerCase(Locale.ROOT);
    }

    public static double similarity(String a, String b) {
        if (a == null || b == null) return 0.0;
        if (a.equalsIgnoreCase(b)) return 1.0;
        return SIMILARITY_MAP.getOrDefault(makeKey(a, b), 0.0);
    }
}
