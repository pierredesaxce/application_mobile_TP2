package fr.uavignon.ceri.tp2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class DetailFragment extends Fragment {

    private EditText textTitle, textAuthors, textYear, textGenres, textPublisher;

    private Boolean exist;

    private Button buttonUpdate;

    private DetailViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        super.onViewCreated(view, savedInstanceState);



// Get selected book
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        if((int)args.getBookNum()!=-1){

            exist = true;
            MutableLiveData<Book> book = viewModel.findBook((int)args.getBookNum());
            viewModel.setCurBook((int)args.getBookNum());
            Snackbar.make(view, "affichage de " + (int)args.getBookNum(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
        else {
            exist = false;
        }

        textTitle = (EditText) view.findViewById(R.id.nameBook);
        textAuthors = (EditText) view.findViewById(R.id.editAuthors);
        textYear = (EditText) view.findViewById(R.id.editYear);
        textGenres = (EditText) view.findViewById(R.id.editGenres);
        textPublisher = (EditText) view.findViewById(R.id.editPublisher);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);



        listenerSetup();
        observerSetup();



        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fr.uavignon.ceri.tp2.DetailFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);

            }
        });
    }

    private void listenerSetup() {

        buttonUpdate.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                if(exist) {
                    viewModel.getCurBook().observe(getViewLifecycleOwner(),
                            book -> {

                                long id;
                                id = book.getId();

                                String title = String.valueOf(textTitle.getText());
                                String auteur = String.valueOf(textAuthors.getText());
                                String annee = String.valueOf(textYear.getText());
                                String genre = String.valueOf(textGenres.getText());
                                String publish = String.valueOf(textPublisher.getText());

                                Book newBook = new Book(title, auteur, annee, genre, publish);
                                newBook.setId(id);
                                viewModel.insertOrUpdateBook(newBook);
                                Snackbar.make(view, "Mise a jour de " + id, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            });
                }

                else {
                    if(TextUtils.isEmpty(textTitle.getText().toString())==true || TextUtils.isEmpty(textAuthors.getText().toString())){

                        Snackbar.make(view, "Veuillez completer ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{

                        String title = String.valueOf(textTitle.getText());
                        String auteur = String.valueOf(textAuthors.getText());
                        String annee = String.valueOf(textYear.getText());
                        String genre = String.valueOf(textGenres.getText());
                        String publish = String.valueOf(textPublisher.getText());

                        Book newBook = new Book(title, auteur, annee, genre, publish);

                        newBook.setId(-1);

                        viewModel.insertOrUpdateBook(newBook);
                        Snackbar.make(view, "Ajout de " + title, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }
            }

        });



    }

    private void observerSetup() {

        if(exist) {
            viewModel.getCurBook().observe(getViewLifecycleOwner(),
                    new Observer<Book>() {

                        @Override
                        public void onChanged(Book book) {


                            textTitle.setText(book.getTitle());
                            textAuthors.setText(book.getAuthors());
                            textYear.setText(book.getYear());
                            textGenres.setText(book.getGenres());
                            textPublisher.setText(book.getPublisher());

                        }

                    });
        }


    }

}