package com.example.gamesmannna.trytestapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesmannna.trytestapp.Common.Common;
import com.example.gamesmannna.trytestapp.Interface.ItemClickListener;
import com.example.gamesmannna.trytestapp.Model.Order;
import com.example.gamesmannna.trytestapp.Model.Request;
import com.example.gamesmannna.trytestapp.ViewHolder.RequestStatusAdapter;
import com.example.gamesmannna.trytestapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    //  FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    // FirebaseDatabase database;
    //  DatabaseReference requests;
    private List<Order> _orderList = new ArrayList<>();

    private List<Request> _requestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //   database = FirebaseDatabase.getInstance();
        //   requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.CURRENT_USER.getPhone());
    }

    private void loadOrders(String phone) {

final String phoneFinal=phone;
        Common.ApiService.GetRequestColl().enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                _requestList = response.body();
                List<Request> requests = new ArrayList<>();
                for (Request req : _requestList) {
                    if (req.getPhone().equals(phoneFinal))
                        requests.add(req);
                }

                Log.d("Phone","phone:"+phoneFinal);
                Log.d("Requests","requests:"+requests.size());
                Log.d("Requests","Original requests:"+_requestList.size());
                RequestStatusAdapter requestStatusAdapter = new RequestStatusAdapter(getBaseContext(), requests) {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.order_layout, parent, false);


                        //Добавлено для отмены краша в списке заказов
                        OrderViewHolder viewHolder = new OrderViewHolder(itemView);
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
//                        Intent orderDetailIntent = new Intent(OrderStatus.this, OrderStatus.class);
//                        orderDetailIntent.putExtra(Constants.MOVIE_ID,_orderList.get(position).getMovieId());
//                        startActivity(orderDetailIntent);
                            }
                        });
                        return viewHolder;
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        OrderViewHolder viewHolder = (OrderViewHolder) holder;
                        viewHolder.txtOrderId.setText(_requestList.get(position).getTotal());
                        viewHolder.txtOrderStatus.setText(convertCodeToStatus(_requestList.get(position).getStatus()));
                        viewHolder.txtOrderAddress.setText(_requestList.get(position).getAddress());
                        viewHolder.txtOrderPhone.setText(_requestList.get(position).getPhone());
                    }
                };

                recyclerView.setAdapter(requestStatusAdapter);
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {

            }
        });





//        Query getOrderByUser = requests.orderByChild("phone")
//                .equalTo(phone);
//
//        FirebaseRecyclerOptions<Request> orderOptions = new FirebaseRecyclerOptions.Builder<Request>()
//                .setQuery(getOrderByUser, Request.class)
//                .build();
//
//        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(orderOptions) {
//            @Override
//            protected void onBindViewHolder(OrderViewHolder viewHolder, int position, Request model) {
//                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
//                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
//                viewHolder.txtOrderAddress.setText(model.getAddress());
//                viewHolder.txtOrderPhone.setText(model.getPhone());
//
//
//            }
//
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.order_layout,parent, false);
//                //return new OrderViewHolder(itemView);
//
//                //Добавлено для отмены краша в списке заказов
//                OrderViewHolder viewHolder=new OrderViewHolder(itemView);
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        /*Intent orderDetailIntent = new Intent(OrderStatus.this, OrderStatus.class);
//                        orderDetailIntent.putExtra(Constants.MOVIE_ID, adapter.getRef(position).getKey());
//                        startActivity(orderDetailIntent);*/
//                    }
//                });
//                return  viewHolder;
//            }
//        };
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //  adapter.stopListening();
    }

    private String convertCodeToStatus(String status) {
        if ("0".equals(status))
            return "Размещено";
        else if ("1".equals(status))
            return "В пути";
        else
            return "Доставлено";
    }


}
