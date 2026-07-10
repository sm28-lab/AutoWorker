package dev.sorn.autoworker;

import dev.sorn.autoworker.Value.ArrayValue;
import dev.sorn.autoworker.Value.BooleanValue;
import dev.sorn.autoworker.Value.DecimalValue;
import dev.sorn.autoworker.Value.IntegerValue;
import dev.sorn.autoworker.Value.ObjectValue;
import dev.sorn.autoworker.Value.TextValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.sorn.autoworker.Value.ArrayValue.arrayValue;
import static dev.sorn.autoworker.Value.BooleanValue.booleanValue;
import static dev.sorn.autoworker.Value.DecimalValue.decimalValue;
import static dev.sorn.autoworker.Value.IntegerValue.integerValue;
import static dev.sorn.autoworker.Value.ObjectValue.objectValue;
import static dev.sorn.autoworker.Value.TextValue.textValue;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class ValueTest {

    @Test
    void decimal_value_compares_by_value_not_scale() {
        // then
        assertThat(new DecimalValue(new BigDecimal("0")))
            .isEqualTo(new DecimalValue(new BigDecimal("0.0")));
    }

    @Nested
    class NullRejection {

        @Test
        void rejects_null_in_text_value() {
            // then
            thenThrownBy(() -> textValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_in_integer_value() {
            // then
            thenThrownBy(() -> integerValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_in_decimal_value() {
            // then
            thenThrownBy(() -> decimalValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_in_boolean_value() {
            // then
            thenThrownBy(() -> booleanValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_map_in_object_value() {
            // then
            thenThrownBy(() -> objectValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_key_in_map() {
            // given
            var map = new HashMap<String, Value>();
            map.put(null, textValue("x"));

            // when // then
            thenThrownBy(() -> new ObjectValue(map))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_value_in_map() {
            // given
            var map = new HashMap<String, Value>();
            map.put("key", null);

            // when // then
            thenThrownBy(() -> new ObjectValue(map))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_list_in_array_value() {
            // then
            thenThrownBy(() -> arrayValue(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void rejects_null_element_in_array_value() {
            // given
            var list = new ArrayList<Value>();
            list.add(null);

            // when // then
            thenThrownBy(() -> new ArrayValue(list))
                .isInstanceOf(NullPointerException.class);
        }

    }

    @Nested
    class Immutability {

        @Test
        void freezes_map() {
            // given
            var mutable = new HashMap<String, Value>();
            mutable.put("key", textValue("value"));

            // when
            var objectValue = new ObjectValue(mutable);
            mutable.put("injected", textValue("hack"));

            // then
            assertThat(objectValue.values()).doesNotContainKey("injected");
        }

        @Test
        void freezes_list() {
            // given
            var mutable = new ArrayList<Value>();
            mutable.add(textValue("first"));

            // when
            var arrayValue = new ArrayValue(mutable);
            mutable.add(textValue("injected"));

            // then
            assertThat(arrayValue.values()).hasSize(1);
            assertThat(arrayValue.values()).doesNotContain(textValue("injected"));
        }

        @Test
        void returns_unmodifiable_map() {
            // given
            var objectValue = new ObjectValue(Map.of("key", textValue("value")));

            // when // then
            thenThrownBy(() -> objectValue.values().put("new", textValue("x")))
                .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        void returns_unmodifiable_list() {
            // given
            var arrayValue = new ArrayValue(List.of(textValue("item")));

            // when // then
            thenThrownBy(() -> arrayValue.values().add(textValue("x")))
                .isInstanceOf(UnsupportedOperationException.class);
        }

    }

    @Nested
    class EqualsAndHashCodeContract {

        @Test
        void text_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(TextValue.class)
                .suppress(NULL_FIELDS)
                .verify();
        }

        @Test
        void integer_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(IntegerValue.class)
                .suppress(NULL_FIELDS)
                .verify();
        }

        @Test
        void decimal_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(DecimalValue.class)
                .suppress(NULL_FIELDS)
                .verify();
        }

        @Test
        void decimal_value_compares_by_value_not_scale() {
            // given
            var a = decimalValue(new BigDecimal("0"));
            var b = decimalValue(new BigDecimal("0.0"));

            // when // then
            assertThat(a).isEqualTo(b);
        }

        @Test
        void boolean_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(BooleanValue.class)
                .suppress(NULL_FIELDS)
                .verify();
        }

        @Test
        void object_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(ObjectValue.class)
                .withPrefabValues(Value.class, textValue("a"), textValue("b"))
                .suppress(NULL_FIELDS)
                .verify();
        }

        @Test
        void array_value_satisfies_contract() {
            // then
            EqualsVerifier.forClass(ArrayValue.class)
                .withPrefabValues(Value.class, textValue("a"), textValue("b"))
                .suppress(NULL_FIELDS)
                .verify();
        }

    }

}
