package dev.sorn.autoworker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static dev.sorn.autoworker.Require.requireNonNull;

public sealed interface Value {

    record ObjectValue(Map<String, Value> values) implements Value {

        public ObjectValue {
            requireNonNull("values", values);
            values = Map.copyOf(values);
        }

        public static ObjectValue objectValue(Map<String, Value> values) {
            return new ObjectValue(values);
        }

    }

    record ArrayValue(List<Value> values) implements Value {

        public ArrayValue {
            requireNonNull("values", values);
            values = List.copyOf(values);
        }

        public static ArrayValue arrayValue(List<Value> values) {
            return new ArrayValue(values);
        }

    }

    record TextValue(String value) implements Value {

        public TextValue {
            requireNonNull("value", value);
        }

        public static TextValue textValue(String value) {
            return new TextValue(value);
        }

    }

    record IntegerValue(Integer value) implements Value {

        public IntegerValue {
            requireNonNull("value", value);
        }

        public static IntegerValue integerValue(Integer value) {
            return new IntegerValue(value);
        }

    }

    record DecimalValue(BigDecimal value) implements Value {

        public DecimalValue {
            requireNonNull("value", value);
        }

        public static DecimalValue decimalValue(BigDecimal value) {
            return new DecimalValue(value);
        }

        @Override
        public int hashCode() {
            return value.stripTrailingZeros().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DecimalValue(var that))) return false;
            return this.value.compareTo(that) == 0;
        }

    }

    record BooleanValue(Boolean value) implements Value {

        public BooleanValue {
            requireNonNull("value", value);
        }

        public static BooleanValue booleanValue(Boolean value) {
            return new BooleanValue(value);
        }

    }

}
