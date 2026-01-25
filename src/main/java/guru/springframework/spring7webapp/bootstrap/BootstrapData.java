package guru.springframework.spring7webapp.bootstrap;

import guru.springframework.spring7webapp.domain.Author;
import guru.springframework.spring7webapp.domain.Book;
import guru.springframework.spring7webapp.domain.Publisher;
import guru.springframework.spring7webapp.repositories.AuthorRepository;
import guru.springframework.spring7webapp.repositories.BookRepository;
import guru.springframework.spring7webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    public BootstrapData(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        Author firstAuthor = new Author();
        firstAuthor.setFirstName("Eric");
        firstAuthor.setLastName("Adams");

        Book firstBook = new Book();
        firstBook.setTitle("Domain-Driven Design");
        firstBook.setIsbn("978-0321125215");

        Author firstAuthorSaved = authorRepository.save(firstAuthor);
        Book firstBookSaved = bookRepository.save(firstBook);

        firstAuthorSaved.getBooks().add(firstBookSaved);
        firstBookSaved.getAuthors().add(firstAuthorSaved);

        Author secondAuthor = new Author();
        secondAuthor.setFirstName("John");
        secondAuthor.setLastName("Smith");

        Book secondBook = new Book();
        secondBook.setTitle("Spring in Action");
        secondBook.setIsbn("978-1617290547");

        Author secondAuthorSaved = authorRepository.save(secondAuthor);
        Book secondBookSaved = bookRepository.save(secondBook);

        secondAuthorSaved.getBooks().add(secondBookSaved);
        secondBookSaved.getAuthors().add(secondAuthorSaved);

        Publisher publisher = new Publisher();
        publisher.setName("Wrox Press");
        publisher.setAddress("100 Main Street");
        Publisher savedPublisher = publisherRepository.save(publisher);

        firstBookSaved.setPublisher(savedPublisher);
        secondBookSaved.setPublisher(savedPublisher);

        authorRepository.save(firstAuthorSaved);
        authorRepository.save(secondAuthorSaved);
        bookRepository.save(firstBookSaved);
        bookRepository.save(secondBookSaved);


        System.out.println("Bootstrap data loaded...");
        System.out.println("Authors count: " + authorRepository.count());
        System.out.println("Books count: " + bookRepository.count());
        System.out.println("Publisher count: " + publisherRepository.count());
    }
}
