package dev.sorn.autoworker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class GraphNodeTest {

    @Nested
    class Id {

        @Test
        void preserves_value() {
            // given
            var id = new GraphNode.Id("fetch-diff");

            // when
            var value = id.value();

            // then
            assertThat(value).isEqualTo("fetch-diff");
        }

        @Test
        void rejects_null() {
            // given
            var id = (String) null;

            // when // then
            thenThrownBy(() -> new GraphNode.Id(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' is required");
        }

        @Test
        void rejects_blank() {
            // given
            var id = "  ";

            // when // then
            thenThrownBy(() -> new GraphNode.Id(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'id' is required");
        }

    }

}
