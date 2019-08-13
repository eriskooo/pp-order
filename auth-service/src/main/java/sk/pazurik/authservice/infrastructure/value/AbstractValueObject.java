package sk.pazurik.authservice.infrastructure.value;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A base class for value objects and so on. This class provides implementations for {@link
 * #equals(Object)} and {@link #hashCode()} using the return value of {@link #values()}.
 * Subclasses have to implement this method and return the actual values of this value object.
 */
public abstract class AbstractValueObject implements Serializable {

    private transient Object[] values;

    public int hashCode() {
        return Arrays.hashCode(getValues());
    }

    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null
                || !(object.getClass().isAssignableFrom(getClass()) || getClass().isAssignableFrom(object.getClass())
        )) {
            return false;
        }
        AbstractValueObject valueObject = (AbstractValueObject) object;
        return Arrays.equals(getValues(), valueObject.getValues());
    }

    /**
     * Returns all values of this value object.
     * <p>
     * Subclasses have to implement this method and return the actual values that make up this value object.
     */
    protected abstract Object[] values();

    private Object[] getValues() {
        if (values == null) {
            values = values();
        }
        return values;
    }
}
