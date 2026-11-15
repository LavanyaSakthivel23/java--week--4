public class Book {
    private final int id;
    private final String title;
    private final String author;
    private String issuedTo; // null means the book is available

    public Book(int id, String title, String author, String issuedTo) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issuedTo = issuedTo;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIssuedTo() { return issuedTo; }
    public boolean isAvailable() { return issuedTo == null; }

    public void issue(String member) throws LibraryException {
        if (!isAvailable()) {
            throw new LibraryException("Book is already issued to " + issuedTo);
        }
        issuedTo = member;
    }

    public void giveBack() throws LibraryException {
        if (isAvailable()) {
            throw new LibraryException("This book was not issued");
        }
        issuedTo = null;
    }

    public String toFileLine() {
        return id + "," + title + "," + author + "," + (issuedTo == null ? "-" : issuedTo);
    }

    public static Book fromFileLine(String line) throws LibraryException {
        String[] p = line.split(",");
        if (p.length != 4) throw new LibraryException("Bad line: " + line);
        try {
            return new Book(Integer.parseInt(p[0].trim()), p[1], p[2], p[3].equals("-") ? null : p[3]);
        } catch (NumberFormatException e) {
            throw new LibraryException("Bad id in line: " + line);
        }
    }

    @Override
    public String toString() {
        return String.format("#%-3d %-28s %-18s %s", id, title, author,
                isAvailable() ? "Available" : "Issued to " + issuedTo);
    }
}