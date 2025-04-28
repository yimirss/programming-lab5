package org.example.cli.builder;

import java.util.function.BiConsumer;

/**
 * Record that represents field of domain object. Used in TicketBuilder
 */
public record FieldSpec(String name, String inputMessage, String validationErrorMessage,
                        boolean isRequired, Class<?> type, BiConsumer<?, ?> mutator) {

}
