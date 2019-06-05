package com.example.gamesmannna.trytestapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamesmannna.trytestapp.Model.User;

import com.example.gamesmannna.trytestapp.Common.Common;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
EditText edtPhone, edtPassword;
Button SignInButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (EditText) findViewById(R.id.edtRegPassword);
        edtPhone = (EditText) findViewById(R.id.edtRegPhone);
        SignInButton = (Button) findViewById(R.id.SignInButton);


     //  final FirebaseDatabase database = FirebaseDatabase.getInstance();
     //  final DatabaseReference table_user = database.getReference("User") ;






        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);

                try{
                    mDialog.setMessage("Ждите . . .");
                    mDialog.show();
                }
                catch (Exception ex)
                {
                    Log.e("dialogError",ex.toString());
                }
                Common.ApiService.GetUserByPhoneId(edtPhone.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("GetUser",response.message());
                        User user=response.body();
                        if(user!=null)
                        {
                            user.setPhone(edtPhone.getText().toString());
                            if (user.getpassword().equals(edtPassword.getText().toString()))
                            {
                                Intent homeIntent = new Intent(SignIn.this, Home.class);
                                Common.CURRENT_USER = user;
                                startActivity(homeIntent);
                                finish();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Пользователя нет в системе", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("CreateOrder",t.getMessage());
                    }
                });
//                table_user.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
//                        mDialog.dismiss();
//                        User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
//                        user.setPhone(edtPhone.getText().toString());
//                        if (user.getpassword().equals(edtPassword.getText().toString()))
//                        {
//                            Intent homeIntent = new Intent(SignIn.this, Home.class);
//                            Common.CURRENT_USER = user;
//                            startActivity(homeIntent);
//                            finish();
//                        }
//
//                        else
//
//                        {
//                            Toast.makeText(SignIn.this, "Неверный пароль", Toast.LENGTH_SHORT).show();
//                        }
//
//                        }
//                        else
//                        {
//                            mDialog.dismiss();
//                            Toast.makeText(SignIn.this, "Такой пользователь не существует", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }
}
