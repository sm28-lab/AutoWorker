package dev.sorn.autoworker;

import static dev.sorn.autoworker.Require.requireNonEmpty;
import static java.util.UUID.randomUUID;

public record Id(String value) {

    public Id {
        requireNonEmpty("value", value);
    }

    public static Id id() {
        return id(randomUUID().toString());
    }

    public static Id id(String value) {
        return new Id(value);
    }

}
