package org.example.cli.utils;

/**
 * Utility class that helps to resolve type of value provided as string
 */
public class TypeResolver {

    @SuppressWarnings("unchecked")
    public static <T> T parseValue(String input, Class<T> type) {
        try {
            if (input == null) return null;
            if (type == Integer.class) return type.cast(Integer.parseInt(input));
            if (type == Long.class) return type.cast(Long.parseLong(input));
            if (type == Boolean.class) return type.cast(Boolean.parseBoolean(input));
            if (type == Double.class) return type.cast(Double.parseDouble(input));
            if (type == Float.class) return type.cast(Float.parseFloat(input));
            if (type == String.class) return type.cast(input);
            if (type.isEnum()) return (T) Enum.valueOf((Class<Enum>) type, input.toUpperCase());

        } catch(Exception e) {
            System.out.println(e.toString());
            throw new IllegalArgumentException("Incompatible type");
        }
        throw new IllegalArgumentException("Unsupported type: " + type.getName());
    }
}