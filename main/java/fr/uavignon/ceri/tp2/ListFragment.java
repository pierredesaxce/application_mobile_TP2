package fr.uavignon.ceri.tp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;

    private ListViewModel viewModel;

    FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        ListFragmentArgs args = ListFragmentArgs.fromBundle(getArguments());
        if((int)args.getBookDel()!=0L){

            viewModel.deleteBook((int)args.getBookDel());

        }

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        fab = view.findViewById(R.id.fab);

        listenerSetup();
        observerSetup();


    }

    private void listenerSetup() {



        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                action.setBookNum(-1);
                Navigation.findNavController(view).navigate(action);

            }

        });

    }

    private void observerSetup() {


        viewModel.getAllBooks().observe(getViewLifecycleOwner(),
                new Observer<List<Book>>() {

                    @Override
                    public void onChanged(List<Book> books) {
                        adapter.setBookList(books);
                    }

                });

    }



}