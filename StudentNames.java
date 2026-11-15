import java.util.ArrayList;
import java.util.Scanner;

public class StudentNames {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> names = new ArrayList<>();

        System.out.print("How many students? ");
        int n = Integer.parseInt(sc.nextLine().trim());

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter name " + i + ": ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) names.add(name);
        }

        System.out.println("\nStudent names:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i));
        }
        System.out.println("Total students: " + names.size());
        sc.close();
    }
}