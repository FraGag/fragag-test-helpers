package ca.fragag.testhelpers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.junit.Test;

/**
 * A helper class that implements test cases that test the contract of the {@link Object#hashCode()} and
 * {@link Object#equals(Object)} methods.
 *
 * @author Francis Gagné
 */
public abstract class ObjectHashCodeEqualsContract {

    /** The main object to run tests on. */
    @Nonnull
    protected final Object mainObject;

    /** An object that is equal (by {@link Object#equals(Object)} to the main object. */
    @Nonnull
    protected final Object otherEqualObject;

    /** Another object that is equal (by {@link Object#equals(Object)} to the main object and to the other equal object. */
    @Nonnull
    protected final Object anotherEqualObject;

    /** An object that is not equal (by {@link Object#equals(Object)} to the other objects. */
    @CheckForNull
    protected final Object[] differentObjects;

    /**
     * Initializes a new ObjectHashCodeEqualsContract.
     *
     * @param mainObject
     *            the main object to run tests on
     * @param otherEqualObject
     *            an object that is equal (by {@link Object#equals(Object)} to the main object
     * @param anotherEqualObject
     *            another object that is equal (by {@link Object#equals(Object)} to the main object and to the other equal object
     * @param differentObjects
     *            objects that are not equal (by {@link Object#equals(Object)} to the other objects
     */
    protected ObjectHashCodeEqualsContract(@Nonnull Object mainObject, @Nonnull Object otherEqualObject,
            @Nonnull Object anotherEqualObject, @CheckForNull Object... differentObjects) {
        this.mainObject = mainObject;
        this.otherEqualObject = otherEqualObject;
        this.anotherEqualObject = anotherEqualObject;
        this.differentObjects = differentObjects;
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method on different objects returns <code>false</code>.
     */
    @Test
    public void differentObjectsAreNotEqual() {
        if (this.differentObjects != null) {
            for (Object differentObject : this.differentObjects) {
                assertThat(this.mainObject.equals(differentObject), is(false));
            }
        }
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method on equal objects returns <code>true</code>.
     */
    @Test
    public void equalObjectsAreEqual() {
        assertThat(this.mainObject.equals(this.otherEqualObject), is(true));
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method on equal objects returns <code>true</code> when the subject and the
     * argument are swapped.
     */
    @Test
    public void equalsIsCommutative() {
        assertThat(this.otherEqualObject.equals(this.mainObject), is(true));
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method called with the same object for the subject (<code>this</code>) and for
     * the argument returns <code>true</code>.
     */
    @Test
    public void equalsIsReflexive() {
        assertThat(this.mainObject.equals(this.mainObject), is(true));
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method returns <code>true</code> when called as <code>a.equals(c)</code>,
     * assuming that <code>a.equals(b)</code> and <code>b.equals(c)</code> both return <code>true</code>.
     */
    @Test
    public void equalsIsTransitive() {
        assumeThat(this.mainObject.equals(this.otherEqualObject), is(true));
        assumeThat(this.otherEqualObject.equals(this.anotherEqualObject), is(true));
        assertThat(this.mainObject.equals(this.anotherEqualObject), is(true));
    }

    /**
     * Asserts that the {@link Object#equals(Object)} method returns <code>false</code> when the argument is <code>null</code>.
     */
    @Test
    public void equalsNullIsFalse() {
        assertThat(this.mainObject.equals(null), is(false));
    }

    /**
     * Asserts that the {@link Object#hashCode()} method on equal objects returns the same hash code.
     */
    @Test
    public void hashCodeOnEqualObjectsIsEqual() {
        assertThat(this.mainObject.hashCode() == this.otherEqualObject.hashCode(), is(true));
    }

}
