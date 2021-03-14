package fr.uavignon.ceri.tp2.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static fr.uavignon.ceri.tp2.data.BookRoomDatabase.databaseWriteExecutor;

public class BookRepository {

    private MutableLiveData<Book> selectedBook = new MutableLiveData<>();
    private LiveData<List<Book>> allBooks;

    private BookDao bookDao;

    public BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        bookDao = db.bookDao();
        allBooks = bookDao.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public MutableLiveData<Book>  getSelectedBook(){

        return selectedBook;

    }

    public void insertBook(Book newbook) {
        databaseWriteExecutor.execute(() -> {
            bookDao.insertBook(newbook);
        });
    }

    public void deleteBook(long id) {
        databaseWriteExecutor.execute(() -> {
            bookDao.deleteBook(id);
        });
    }

    public void findBook(long id) {
        Future<Book> fbooks = databaseWriteExecutor.submit(() -> {
            return bookDao.getBook(id);
        });
        try{
            selectedBook.setValue(fbooks.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book newbook) {
        databaseWriteExecutor.execute(() -> {
            bookDao.updateBook(newbook);
        });
    }

}
