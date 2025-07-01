import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Author;
import model.Book;
import model.BookCopy;
import model.Category;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotekPU");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("EntityManagerFactory created successfully.");
            em.getTransaction().begin();
            // Create instances of Author, Category, Book, and BookCopy
            Author author = new Author("Michael Crichton", "American");
            Category category = new Category("Science Fiction");
            Book book = new Book("Jurassic Park", author, "978-0-06-025492-6", category);
            BookCopy bookCopy = new BookCopy(book.getIsbn()+"_1", book);

            // Check if the author already exists and persist it if it does not
            Author existingAuthor = em.createQuery(
                            "SELECT a FROM Author a WHERE a.name = :name", Author.class)
                    .setParameter("name", author.getName())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if(existingAuthor == null)
                em.persist(author);

            // Check if the category already exists and persist it if it does not
            Category existingCategory = em.createQuery(
                            "SELECT c FROM Category c WHERE c.name = :name", Category.class)
                    .setParameter("name", category.getName())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if(existingCategory == null)
                em.persist(category);

            // Check if the book already exists and persist it if it does not
            Book existingBook = em.createQuery(
                            "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                    .setParameter("isbn", book.getIsbn())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if(existingBook == null)
                em.persist(book);

            // Check if the book copy already exists and persist it if it does not
            BookCopy existingBookCopy = em.createQuery(
                            "SELECT bc FROM BookCopy bc WHERE bc.copyId = :copyId", BookCopy.class)
                    .setParameter("copyId", bookCopy.getCopyId())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if(existingBookCopy == null)
                em.persist(bookCopy);

            em.getTransaction().commit();

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
