package dev.sorn.autoworker;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dev.sorn.autoworker.Graph.Builder.graph;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class GraphTest implements GraphTestData {

    @Nested
    class EdgeValidation {


        @Test
        void rejects_null_from() {
            // given
            var nodeB = aNode().build();

            // when // then
            thenThrownBy(() -> graph().edge(null, nodeB))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'from' is required");
        }

        @Test
        void rejects_null_to() {
            // given
            var nodeA = aNode().build();

            // when // then
            thenThrownBy(() -> graph().edge(nodeA, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'to' is required");
        }

        @Test
        void rejects_self_loops() {
            // given
            var nodeA = aNode().id("node-a").build();

            // when // then
            thenThrownBy(() -> graph().edge(nodeA, nodeA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("self-loop detected: 'node-a' cannot have an edge to itself");
        }

        @Test
        void rejects_duplicate_edge() {
            // given
            var nodeA = aNode().build();
            var nodeB = aNode().build();

            // when // then
            thenThrownBy(() -> graph()
                .edge(nodeA, nodeB)
                .edge(nodeA, nodeB))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("duplicate edge")
                .hasMessageContaining(nodeA.id())
                .hasMessageContaining(nodeB.id());
        }

        @Test
        void rejects_disconnected_graph() {
            // given
            var nodeA = aNode().id("node-a").build();
            var nodeB = aNode().id("node-b").build();
            var nodeC = aNode().id("node-c").build();
            var nodeD = aNode().id("node-d").build();

            // when // then
            thenThrownBy(() -> graph()
                .edge(nodeA, nodeB)
                .edge(nodeC, nodeD)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("graph is disconnected")
                .hasMessageContaining("unreachable");
        }

        @Test
        void allows_cycles() {
            // given
            var nodeA = aNode().build();
            var nodeB = aNode().build();

            // when
            var graph = graph()
                .edge(nodeA, nodeB)
                .edge(nodeB, nodeA)
                .build();

            // then
            assertThat(graph.successorsOf(nodeA)).containsExactly(nodeB);
            assertThat(graph.successorsOf(nodeB)).containsExactly(nodeA);
            assertThat(graph.predecessorsOf(nodeA)).containsExactly(nodeB);
            assertThat(graph.predecessorsOf(nodeB)).containsExactly(nodeA);
        }

        @Test
        void requires_at_least_one_predecessor() {
            // given
            var graph = graph();

            // when // then
            thenThrownBy(graph::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'predecessors' cannot be empty");
        }

        @Test
        void maintains_successor_predecessor_map_sync() {
            // given
            var nodeA = aNode().build();
            var nodeB = aNode().build();
            var nodeC = aNode().build();
            var nodeD = aNode().build();

            // when
            var graph = graph()
                .edge(nodeA, nodeB)
                .edge(nodeA, nodeD)
                .edge(nodeB, nodeC)
                .build();

            // then
            assertThat(graph.successorsOf(nodeA)).containsExactlyInAnyOrder(nodeB, nodeD);
            assertThat(graph.successorsOf(nodeB)).containsExactly(nodeC);
            assertThat(graph.predecessorsOf(nodeA)).isEmpty();
            assertThat(graph.predecessorsOf(nodeB)).containsExactly(nodeA);
            assertThat(graph.predecessorsOf(nodeC)).containsExactly(nodeB);
            assertThat(graph.predecessorsOf(nodeD)).containsExactly(nodeA);
        }

    }

    @Nested
    class Immutability {

        @Test
        void returns_unmodifiable_predecessors() {
            // given
            var nodeA = aNode().id("node-a").build();
            var nodeB = aNode().id("node-b").build();
            var graph = graph()
                .edge(nodeA, nodeB)
                .build();
            var predecessors = graph.predecessorsOf(nodeB);

            // when // then
            thenThrownBy(() -> predecessors.add(aNode().build()))
                .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        void returns_unmodifiable_successors() {
            // given
            var nodeA = aNode().id("node-a").build();
            var nodeB = aNode().id("node-b").build();
            var graph = graph()
                .edge(nodeA, nodeB)
                .build();
            var successors = graph.successorsOf(nodeA);

            // when // then
            thenThrownBy(() -> successors.add(aNode().build()))
                .isInstanceOf(UnsupportedOperationException.class);
        }

    }

}
