package dev.sorn.autoworker;

import static dev.sorn.autoworker.Require.requireNonNull;

public final class Envelope {

    private final Id correlationId;
    private final Value payload;
    private final Value metadata;

    private Envelope(Builder builder) {
        this.correlationId = requireNonNull("correlationId", builder.correlationId);
        this.payload = requireNonNull("payload", builder.payload);
        this.metadata = requireNonNull("metadata", builder.metadata);
    }

    public Id correlationId() {
        return correlationId;
    }

    public Value payload() {
        return payload;
    }

    public Value metadata() {
        return metadata;
    }

    public static final class Builder {

        private Id correlationId;
        private Value payload;
        private Value metadata;

        private Builder() {}

        public static Builder envelope() {
            return new Builder();
        }

        public Builder correlationId(Id correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder payload(Value payload) {
            this.payload = payload;
            return this;
        }

        public Builder metadata(Value metadata) {
            this.metadata = metadata;
            return this;
        }

        public Envelope build() {
            return new Envelope(this);
        }

    }

}
