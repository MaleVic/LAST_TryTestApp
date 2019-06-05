package com.example.gamesmannna.trytestapp.ViewHolder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.gamesmannna.trytestapp.Model.Category;
import com.example.gamesmannna.trytestapp.Model.Movie;
import com.example.gamesmannna.trytestapp.Model.Order;
import com.example.gamesmannna.trytestapp.Model.Request;
import com.example.gamesmannna.trytestapp.OrderStatus;

import java.util.List;

public abstract class RequestStatusAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<OrderViewHolder> {

    public List<Request> Requests;
    private Context _context;


    public RequestStatusAdapter(Context context, List<Request> requestList) {
        _context = context;
        Requests = requestList;
    }



    @Override
    public int getItemCount() {
        return Requests.size();
    }


}
