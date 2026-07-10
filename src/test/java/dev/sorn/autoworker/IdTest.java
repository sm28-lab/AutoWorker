package dev.sorn.autoworker;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static dev.sorn.autoworker.Id.id;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class IdTest {

    @Test
    void returns_given_value() {
        // given
        var id = id("some-value");

        // when
        var value = id.value();

        // then
        assertThat(id.value()).isEqualTo("some-value");
    }

    @Test
    void rejects_null_value() {
        // given
        var value = (String) null;

        // when // then
        thenThrownBy(() -> id(value))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("'value' is required");
    }

    @Test
    void rejects_empty_value() {
        // given
        var value = "";

        // when // then
        thenThrownBy(() -> id(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("'value' cannot be empty");
    }

    @Test
    void satisfies_equals_and_hash_code_contract() {
        // then
        EqualsVerifier.forClass(Id.class)
            .suppress(NULL_FIELDS)
            .verify();
    }

}
