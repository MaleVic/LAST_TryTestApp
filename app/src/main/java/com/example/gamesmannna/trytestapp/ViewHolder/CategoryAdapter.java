package com.example.gamesmannna.trytestapp.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.gamesmannna.trytestapp.Model.Category;

import java.util.List;

public abstract class CategoryAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<MenuViewHolder> {

    public List<Category> Categories;
    private Context _context;


    public CategoryAdapter(Context context, List<Category> categories) {
        _context = context;
        Categories = categories;
    }


//    @NonNull
//    @Override
//    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       return  null;
//    }


    @Override
    public int getItemCount() {
        return Categories.size();
    }


}
