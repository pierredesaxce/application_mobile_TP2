package fr.uavignon.ceri.tp2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;
import fr.uavignon.ceri.tp2.data.BookRepository;

public class DetailViewModel extends AndroidViewModel {

    private BookRepository bookRepo;
    private MutableLiveData<Book> curBook;
    static long idActuel = 11;

    public  DetailViewModel(Application application){

        super(application);
        bookRepo = new BookRepository(application);

    }

    public MutableLiveData<Book> findBook(long id) {
        bookRepo.findBook(id);
        return bookRepo.getSelectedBook();
    }

    public void setCurBook(long id) {
        bookRepo.findBook(id);
        this.curBook = bookRepo.getSelectedBook();
    }

    public void insertOrUpdateBook(Book newbook) {

        if ( (newbook.getId()!=-1)) {
            bookRepo.updateBook(newbook);
        }
        else{

            idActuel+=1;
            newbook.setId(DetailViewModel.idActuel);
            bookRepo.insertBook(newbook);

        }

    }

    public MutableLiveData<Book> getCurBook() {
        return curBook;
    }
}
