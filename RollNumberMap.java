import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RollNumberMap {
    public static void main(String[] args) {
        HashMap<Integer, String> students = new HashMap<>();
        students.put(101, "Asha");
        students.put(102, "Ravi");
        students.put(103, "Meena");
        students.put(104, "Kumar");

        System.out.println("All students:");
        for (Map.Entry<Integer, String> e : students.entrySet()) {
            System.out.println("Roll " + e.getKey() + " -> " + e.getValue());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter roll number to search: ");
        try {
            int roll = Integer.parseInt(sc.nextLine().trim());
            if (students.containsKey(roll)) {
                System.out.println("Name: " + students.get(roll));
            } else {
                System.out.println("No student with roll number " + roll);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
        sc.close();
    }
}