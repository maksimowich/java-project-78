package hexlet.code.schemas;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private List<Predicate<T>> conditions = new ArrayList<>();

    /**
   * This implementation checks accordance to conditions for input param.
   * @return return true if value passed test
   * @param o - object for verification
   */
    protected boolean isValid(final Object o) {
        for (Predicate<T> cond : conditions) {
            if (!cond.test((T) o)) {
                return false;
            }
        }
        return true;
    }

    /**
   * This implementation appeared because "v.number().required().positive()" didn't work.
   * @return return *Schema.class
   * @param <T2> child-class for return
   */
    protected <T2 extends BaseSchema> T2 required() {
        addCondition(object -> object != null);
        return (T2) this;
    }

    protected final void addCondition(Predicate<T> condition) {
        conditions.add(condition);
    }
}
