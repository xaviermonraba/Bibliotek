import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Author;
import model.Book;
import model.BookCopy;
import model.Category;
import service.BookService;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotekPU");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("EntityManagerFactory created successfully.");
            BookService bookService = new BookService();
            Author author = new Author("Joshua Bloch", "USA");
            Category category = new Category("Programming");
            Book book = new Book("Effective Java", author, "978-0134686097", category);
            BookCopy bookCopy = new BookCopy("978-0134686097_1", book);

            book.getBookCopies().add(bookCopy);
            bookService.addBook(book);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("EntityManagerFactory closed.");
            }
        }
    }
}
