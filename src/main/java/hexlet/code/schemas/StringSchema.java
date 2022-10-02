package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public boolean isValid(final Object o) {
        if (o instanceof String || o == null) {
            return super.isValid(o);
        }
        return false;
    }

    public StringSchema minLength(final int newMinLength) {
        addCondition(string -> string == null || string.length() >= newMinLength);
        return this;
    }

    public StringSchema contains(final String newSample) {
        addCondition(string -> string == null || string.contains(newSample));
        return this;
    }

    public StringSchema required() {
        addCondition(string -> string == null || string.length() > 0);
        return super.<StringSchema>required();
    }

}
