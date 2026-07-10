package dev.sorn.autoworker;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static dev.sorn.autoworker.Require.requireNonEmpty;
import static dev.sorn.autoworker.Require.requireNonNull;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.joining;

public final class Graph {

    private final Map<Node, Set<Node>> successors;
    private final Map<Node, Set<Node>> predecessors;

    private Graph(Builder builder) {
        requireNonNull("successors", builder.successors);
        requireNonEmpty("predecessors", builder.predecessors);
        checkWeaklyConnected(builder.successors, builder.predecessors);
        this.successors = freeze(builder.successors);
        this.predecessors = freeze(builder.predecessors);
    }

    public Set<Node> successorsOf(Node node) {
        return successors.getOrDefault(node, emptySet());
    }

    public Set<Node> predecessorsOf(Node node) {
        return predecessors.getOrDefault(node, emptySet());
    }

    private void checkWeaklyConnected(
        Map<Node, Set<Node>> successors,
        Map<Node, Set<Node>> predecessors
    ) {
        final var allNodes = new HashSet<Node>();
        allNodes.addAll(successors.keySet());
        allNodes.addAll(predecessors.keySet());
        final var start = allNodes.iterator().next();
        final var visited = new HashSet<Node>();
        final var queue = new ArrayDeque<Node>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            final var current = queue.poll();
            for (final var neighbor : successors.getOrDefault(current, emptySet())) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
            for (final var neighbor : predecessors.getOrDefault(current, emptySet())) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        if (visited.size() != allNodes.size()) {
            final var disconnected = new HashSet<>(allNodes);
            disconnected.removeAll(visited);
            final var ids = disconnected
                .stream()
                .map(node -> "'" + node.id() + "'")
                .sorted()
                .collect(joining(", ", "[", "]"));
            throw new IllegalArgumentException("graph is disconnected; nodes %s are unreachable".formatted(ids));
        }
    }

    private static Map<Node, Set<Node>> freeze(Map<Node, Set<Node>> mutable) {
        final var frozen = new HashMap<Node, Set<Node>>();
        mutable.forEach((node, neighbors) -> frozen.put(node, Set.copyOf(neighbors)));
        return Map.copyOf(frozen);
    }

    public static final class Builder {

        private final Map<Node, Set<Node>> successors = new HashMap<>();
        private final Map<Node, Set<Node>> predecessors = new HashMap<>();

        private Builder() {}

        public static Builder graph() {
            return new Builder();
        }

        public Builder edge(Node from, Node to) {
            requireNonNull("from", from);
            requireNonNull("to", to);
            if (from.equals(to)) {
                throw new IllegalArgumentException("self-loop detected: '%s' cannot have an edge to itself".formatted(from));
            }
            if (successors.getOrDefault(from, Set.of()).contains(to)) {
                throw new IllegalArgumentException("duplicate edge: '%s' → '%s'".formatted(from, to));
            }
            successors.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            predecessors.computeIfAbsent(to, k -> new HashSet<>()).add(from);
            return this;
        }

        public Graph build() {
            return new Graph(this);
        }

    }

}
