package dev.sorn.autoworker;

import dev.sorn.autoworker.Value.ArrayValue;
import dev.sorn.autoworker.Value.BooleanValue;
import dev.sorn.autoworker.Value.DecimalValue;
import dev.sorn.autoworker.Value.IntegerValue;
import dev.sorn.autoworker.Value.ObjectValue;
import dev.sorn.autoworker.Value.TextValue;

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
import static java.util.UUID.randomUUID;
import static java.util.concurrent.ThreadLocalRandom.current;

public interface ValueTestData {

    int MAX_DEPTH = 3;

    default TextValue aTextValue() {
        return textValue(randomUUID().toString());
    }

    default IntegerValue anIntegerValue() {
        return integerValue(current().nextInt());
    }

    default DecimalValue aDecimalValue() {
        return decimalValue(BigDecimal.valueOf(current().nextDouble()));
    }

    default BooleanValue aBooleanValue() {
        return booleanValue(current().nextBoolean());
    }

    default ObjectValue anObjectValue() {
        return anObjectValue(MAX_DEPTH);
    }

    private ObjectValue anObjectValue(int depth) {
        var size = current().nextInt(1, 5);
        var map = new HashMap<String, Value>();
        while (map.size() < size) {
            map.put(randomUUID().toString(), aValue(depth));
        }
        return objectValue(Map.copyOf(map));
    }

    default ArrayValue anArrayValue() {
        return anArrayValue(MAX_DEPTH);
    }

    private ArrayValue anArrayValue(int depth) {
        var size = current().nextInt(1, 5);
        var list = new ArrayList<Value>();
        while (list.size() < size) {
            list.add(aValue(depth));
        }
        return arrayValue(List.copyOf(list));
    }

    default Value aValue() {
        return aValue(MAX_DEPTH);
    }

    private Value aValue(int depth) {
        var scalarOnly = depth == 0;
        var range = scalarOnly ? 4 : 6;
        return switch (current().nextInt(range)) {
            case 0 -> aTextValue();
            case 1 -> anIntegerValue();
            case 2 -> aDecimalValue();
            case 3 -> aBooleanValue();
            case 4 -> anObjectValue(depth - 1);
            case 5 -> anArrayValue(depth - 1);
            default -> throw new IllegalStateException();
        };
    }

}
