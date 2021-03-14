package fr.uavignon.ceri.tp2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Update
    void updateBook(Book book);

    @Insert
    void insertBook(Book book);

    @Query("SELECT * FROM books WHERE productId= :id")
    Book getBook(long id);

    @Query("DELETE FROM books WHERE productId= :id")
    void deleteBook(long id);

    @Query("SELECT * FROM books ")
    LiveData<List<Book>> getAllBooks();

    @Query("DELETE FROM books")
    void deleteAllBooks();
}
