package ca.fragag.testhelpers;

import javax.annotation.Nonnull;

import org.hamcrest.BaseDescription;
import org.hamcrest.Description;

/**
 * {@link Description} that automatically adds a prefix string at the beginning of each line.
 *
 * @author Francis Gagné
 */
public final class LinePrefixDescription extends BaseDescription {

    @Nonnull
    private final Description description;
    @Nonnull
    private final String prefix;
    private boolean prefixPending;

    /**
     * Initializes a new LinePrefixDescription.
     *
     * @param description
     *            the {@link Description} to wrap
     * @param prefix
     *            the string to add at the beginning of each line
     * @param prefixPending
     *            <code>true</code> if the description currently ends with a line break, or <code>false</code> otherwise
     */
    public LinePrefixDescription(@Nonnull Description description, @Nonnull String prefix, boolean prefixPending) {
        this.description = description;
        this.prefix = prefix;
        this.prefixPending = prefixPending;
    }

    @Override
    protected void append(char c) {
        this.appendPrefix();
        this.description.appendText(String.valueOf(c));
        if (c == '\n') {
            this.prefixPending = true;
        }
    }

    @Override
    protected void append(String str) {
        int fromIndex = 0;
        int indexOfLineFeed;
        while ((indexOfLineFeed = str.indexOf('\n', fromIndex)) != -1) {
            this.appendPrefix();
            this.description.appendText(str.substring(fromIndex, indexOfLineFeed + 1));
            this.prefixPending = true;
            fromIndex = indexOfLineFeed + 1;
        }

        if (fromIndex < str.length()) {
            this.appendPrefix();
            this.description.appendText(str.substring(fromIndex));
        }
    }

    private void appendPrefix() {
        if (this.prefixPending) {
            this.description.appendText(this.prefix);
            this.prefixPending = false;
        }
    }

}
