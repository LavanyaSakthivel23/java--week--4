import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SortNumbers {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(45, 12, 89, 7, 23, 56));
        System.out.println("Original : " + numbers);

        Collections.sort(numbers);
        System.out.println("Ascending: " + numbers);

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Descending: " + numbers);

        System.out.println("Max: " + Collections.max(numbers));
        System.out.println("Min: " + Collections.min(numbers));
    }
}