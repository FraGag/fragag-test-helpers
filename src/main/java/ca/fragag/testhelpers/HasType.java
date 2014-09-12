package ca.fragag.testhelpers;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;

/**
 * Matches an object whose runtime type is exactly some specific type.
 *
 * @param <T>
 *            the type of the matched object
 *
 * @author Francis Gagné
 */
public class HasType<T> extends DiagnosingMatcher<T> {

    /**
     * Returns a matcher that matches an object whose runtime type is exactly some specific type.
     *
     * @param type
     *            the expected type
     * @return the matcher
     */
    public static <T> Matcher<T> hasType(Class<?> type) {
        return new HasType<>(type);
    }

    private final Class<?> type;

    /**
     * Initializes a new HasType.
     *
     * @param type
     *            the expected type
     */
    public HasType(Class<?> type) {
        if (type == null) {
            throw new NullPointerException("type");
        }

        this.type = type;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an object with type ").appendValue(this.type);
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        if (!Objects.equals(item.getClass(), this.type)) {
            mismatchDescription.appendText("has type ").appendValue(item.getClass());
            return false;
        }

        return true;
    }

}
