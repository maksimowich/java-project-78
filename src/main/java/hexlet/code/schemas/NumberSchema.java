package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public boolean isValid(final Object o) {
        if (o instanceof Integer || o == null) {
            return super.isValid(o);
        }
        return false;
    }

    public NumberSchema positive() {
        addCondition(number -> number == null || number > 0);
        return this;
    }

    public NumberSchema range(final int min, final int max) {
        addCondition(number -> number == null || (number >= min && number <= max));
        return this;
    }

    public NumberSchema required() {
        return super.<NumberSchema>required();
    }

}
