package org.example.cli.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class that parses line into pairs of arguments.
 */
public final class KVArgParser {
    public static Map<String, String> parse(String input) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = input.split("\\s+");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);

            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Invalid argument format: " + pair);
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            result.put(key, value);
        }

        return result;
    }
}
