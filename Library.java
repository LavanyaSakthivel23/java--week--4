import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private static final String FILE_NAME = "books.txt";
    private final List<Book> books = new ArrayList<>();

    public Library() {
        load();
    }

    public void addBook(String title, String author) throws LibraryException {
        if (title.trim().isEmpty() || author.trim().isEmpty()) {
            throw new LibraryException("Title and author cannot be empty");
        }
        if (title.contains(",") || author.contains(",")) {
            throw new LibraryException("Commas are not allowed");
        }
        int id = 1;
        for (Book b : books) id = Math.max(id, b.getId() + 1);
        books.add(new Book(id, title.trim(), author.trim(), null));
        save();
    }

    public Book findById(int id) throws LibraryException {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        throw new LibraryException("No book with id " + id);
    }

    public void issueBook(int id, String member) throws LibraryException {
        if (member.trim().isEmpty() || member.contains(",")) {
            throw new LibraryException("Enter a valid member name");
        }
        findById(id).issue(member.trim());
        save();
    }

    public void returnBook(int id) throws LibraryException {
        findById(id).giveBack();
        save();
    }

    public List<Book> search(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> getAll() {
        return books;
    }

    private void save() {
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (Book b : books) fw.write(b.toFileLine() + "\n");
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    private void load() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    books.add(Book.fromFileLine(line));
                } catch (LibraryException e) {
                    System.out.println("Skipped bad line in file");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
    }
}