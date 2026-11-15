import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static final Library library = new Library();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add book");
            System.out.println("2. View all books");
            System.out.println("3. Search book");
            System.out.println("4. Issue book");
            System.out.println("5. Return book");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Title: ");
                        String title = sc.nextLine();
                        System.out.print("Author: ");
                        library.addBook(title, sc.nextLine());
                        System.out.println("Book added.");
                        break;
                    case "2":
                        show(library.getAll());
                        break;
                    case "3":
                        System.out.print("Keyword: ");
                        show(library.search(sc.nextLine().trim()));
                        break;
                    case "4":
                        int issueId = readId();
                        System.out.print("Member name: ");
                        library.issueBook(issueId, sc.nextLine());
                        System.out.println("Book issued.");
                        break;
                    case "5":
                        library.returnBook(readId());
                        System.out.println("Book returned.");
                        break;
                    case "6":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Enter 1-6.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static int readId() throws LibraryException {
        System.out.print("Book id: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new LibraryException("Please enter a valid number");
        }
    }

    private static void show(List<Book> list) {
        if (list.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        for (Book b : list) System.out.println(b);
    }
}