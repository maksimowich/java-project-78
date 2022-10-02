package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<Object, Object>> {

    public boolean isValid(final Object o) {
        if (o instanceof Map || o == null) {
            return super.isValid(o);
        }
        return false;
    }

    private boolean checkShape(Map<Object, Object> map, Map<String, BaseSchema> shape) {
        for (Map.Entry<String, BaseSchema> entry : shape.entrySet()) {
            BaseSchema schema = entry.getValue();
            Object value = map.get(entry.getKey());
            if (!schema.isValid(value)) {
                return false;
            }
        }
        return true;
    }

    public MapSchema sizeof(final int newSize) {
        addCondition(map -> map == null || map.size() == newSize);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> newShape) {
        addCondition(map -> map == null || newShape == null || checkShape(map, newShape));
        return this;
    }

    public MapSchema required() {
        return super.<MapSchema>required();
    }
}
