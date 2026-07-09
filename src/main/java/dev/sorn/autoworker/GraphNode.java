package dev.sorn.autoworker;

public interface GraphNode {

    record Id(String value) {
        public Id {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("'id' is required");
            }
        }
    }

    Id id();

}
