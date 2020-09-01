//TODO: Import libraries as needed

import java.util.*;

public class SortedCollection {

    public static ArrayList inputs;

    public SortedCollection() {
        inputs = new ArrayList(1);
    }

    /**
     * Adds the number n to the collection.
     *
     * @param n the number to add to the collection
     * @param position position in the array to insert the read number
     * @return always returns true
     */
    public boolean add(int n) {
        // TODO: Implement
        inputs.add(n);
        Collections.sort(inputs);
        return true;
    }

    /**
     * Removes the smallest number in the collection and returns it.
     *
     * @return the smallest number in the collection
     */
    public int remove() throws NoSuchElementException {
        // TODO: Implement
        if (inputs.isEmpty()) {
            throw new NoSuchElementException("No Such Element Exception");
        } else {
            return (int) inputs.remove(0);
        }
    }

    /**
     * Prints usage information.
     */
    public static void showUsage() {
        System.out.println("Usage: java SortedCollection [num1] [num2] [num3] ...");
    }

    /**
     * Main method. Receives a list of numbers as commandline arguments and
     * prints out the list in sorted order from smallest to largest.
     *
     * @param args commandline arguments; see showUsage() for detailed
     * information
     */
    public static void main(String[] args) {
        SortedCollection collection = new SortedCollection();
        if (args.length == 0) {
            showUsage();
            return;
        }

        // If any commandline argument is not a number, call showUsage() and return.
        String regex = "[^0-9]";

        // TODO: add numbers in commandline arguments to collection using the add(int) method.
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches(regex)) {
                showUsage();
                return;
            } else {
                collection.add(Integer.parseInt(args[i]));
            }
        }

        System.out.print("sorted: ");
        for (int i = 0; i < args.length; i++) {
            int num = collection.remove();
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
