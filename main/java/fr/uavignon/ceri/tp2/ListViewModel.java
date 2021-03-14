package fr.uavignon.ceri.tp2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;
import fr.uavignon.ceri.tp2.data.BookRepository;

//import static fr.uavignon.ceri.tp2.data.BookRoomDatabase.databaseWriteExecutor;

public class ListViewModel extends AndroidViewModel {

    private BookRepository bookRepo;
    private static LiveData<List<Book>> allBooks;

    public  ListViewModel(Application application){

        super(application);
        bookRepo = new BookRepository(application);
        allBooks= bookRepo.getAllBooks();

    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public MutableLiveData<Book> getSelectedBook(){

        return bookRepo.getSelectedBook();

    }

    public void insertBook(Book newbook) {
        bookRepo.insertBook(newbook);
    }

    public void deleteBook(long id) {
        bookRepo.deleteBook(id);
    }

    public MutableLiveData<Book> findBook(long id) {
        bookRepo.findBook(id);
        return bookRepo.getSelectedBook();
    }

    public void updateBook(Book newbook) {
        bookRepo.updateBook(newbook);
    }

}
