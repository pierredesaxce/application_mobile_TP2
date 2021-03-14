package fr.uavignon.ceri.tp2;


import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uavignon.ceri.tp2.data.Book;

public class RecyclerAdapter extends RecyclerView.Adapter<fr.uavignon.ceri.tp2.RecyclerAdapter.ViewHolder> {

    private static final String TAG = fr.uavignon.ceri.tp2.RecyclerAdapter.class.getSimpleName();

    private static List<Book> bookList;

    private ActionMode delActionMode;
    private View v;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(bookList.get(i).getTitle());
        viewHolder.itemDetail.setText(bookList.get(i).getAuthors());

    }

    @Override
    public int getItemCount() {
        if(bookList!=null) {
            return bookList.size();
        }
        else{
            return 0;
        }
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDetail = itemView.findViewById(R.id.item_detail);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    long id = RecyclerAdapter.this.bookList.get((int)getAdapterPosition()).getId();

                    /* Snackbar.make(v, "Click detected on chapter " + (position+1),
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                     */
                    ListFragmentDirections.ActionFirstFragmentToSecondFragment action = ListFragmentDirections.actionFirstFragmentToSecondFragment();
                    action.setBookNum(id);
                    Navigation.findNavController(v).navigate(action);


                }
            });

            ActionMode.Callback delActionModeCallback = new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.context_menu,menu);
                    mode.setTitle("Voulez-vous supprimer ?");
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    long id = RecyclerAdapter.this.bookList.get((int)getAdapterPosition()).getId();
                    if(item.getItemId()==R.id.delete){

                        ListFragmentDirections.ActionFirstFragmentSelf action = ListFragmentDirections.actionFirstFragmentSelf();
                        action.setBookDel(id);
                        Navigation.findNavController(v).navigate(action);

                        mode.finish();
                        return true;
                    }
                    else{
                        return false;
                    }


                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    delActionMode = null;
                }
            };

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    /*Snackbar.make( itemView, "test suppression longclick step 1" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
*/
                    if(delActionMode != null){
                        Snackbar.make( itemView, "test suppression longclick step null" , Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return false;
                    }
/*
                    Snackbar.make( itemView, "test suppression longclick step 2" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
     */

                    delActionMode = v.startActionMode(delActionModeCallback);

/*
                    Snackbar.make( itemView, "test suppression longclick step 3" , Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    */
                    return true;


                }


            });

        }
    }

}