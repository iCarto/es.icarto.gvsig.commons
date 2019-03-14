package javax.swing;

import java.util.List;

/**
 * SpinnerListModel that searches through its elements in a linear way, always
 * starting from the first element instead of the last one selected.
 *
 * @author jlopez
 *
 */
public class LinearSearchSpinnerListModel extends SpinnerListModel {

    private static final long serialVersionUID = 1L;

    public LinearSearchSpinnerListModel(List<String> pks) {
        super(pks);
    }

    /**
     * Returns the next object that starts with <code>substring</code>.
     *
     * @param substring
     *            the string to be matched
     * @return the match
     */
    Object findNextMatch(String substring) {
        int max = getList().size();

        if (max == 0) {
            return null;
        }
        int counter = 0;

        do {
            Object value = getList().get(counter);
            String string = value.toString();

            if (string != null && string.startsWith(substring)) {
                return value;
            }
            counter = (counter + 1) % max;
        } while (counter != 0);
        return null;
    }

}
