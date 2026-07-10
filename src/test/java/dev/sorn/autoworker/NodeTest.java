package dev.sorn.autoworker;

import dev.sorn.autoworker.Value.TextValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dev.sorn.autoworker.Node.Builder.node;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class NodeTest implements NodeTestData {

    @Nested
    class NullRejection {

        @Test
        void rejects_null_id() {
            // given
            var builder = node()
                .envelope(anEnvelope().build());

            // when // then
            thenThrownBy(builder::build)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'builder.id' is required");
        }

        @Test
        void rejects_empty_id() {
            // given
            var builder = node()
                .id("")
                .envelope(anEnvelope().build());

            // when // then
            thenThrownBy(builder::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("'builder.id' cannot be empty");
        }

        @Test
        void rejects_null_envelope() {
            // given
            var builder = node()
                .id("node-a");

            // when // then
            thenThrownBy(builder::build)
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("'builder.envelope' is required");
        }

    }

    @Nested
    class EqualsAndHashCodeContract {

        @Test
        void satisfies_equals_hash_code_contract() {
            // given
            var value1 = new TextValue("prefab-a");
            var value2 = new TextValue("prefab-b");

            // when // then
            EqualsVerifier.forClass(Node.class)
                .withPrefabValues(Value.class, value1, value2)
                .suppress(NULL_FIELDS)
                .verify();
        }

    }

    @Nested
    class ToString {

        @Test
        void returns_node_id() {
            // given
            var node = aNode().id("some-id").build();

            // when
            var str = node.toString();

            // then
            assertThat(str).isEqualTo("some-id");
        }

    }

}
