package dev.sorn.autoworker;

import java.util.Objects;

import static dev.sorn.autoworker.Require.requireNonEmpty;
import static dev.sorn.autoworker.Require.requireNonNull;
import static java.util.Objects.hash;

public final class Node {

    private final String id;
    private final Envelope envelope;

    private Node(Builder builder) {
        this.id = requireNonEmpty("builder.id", builder.id);
        this.envelope = requireNonNull("builder.envelope", builder.envelope);
    }

    public String id() {
        return id;
    }

    public Envelope envelope() {
        return envelope;
    }

    @Override
    public int hashCode() {
        return hash(id, envelope);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Node that)) return false;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.envelope, that.envelope);
    }

    @Override
    public String toString() {
        return id;
    }

    public static final class Builder {

        private String id;
        private Envelope envelope;

        private Builder() {}

        public static Builder node() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder envelope(Envelope envelope) {
            this.envelope = envelope;
            return this;
        }

        public Node build() {
            return new Node(this);
        }

    }

}
