package com.example.gamesmannna.trytestapp;

        import android.app.ProgressDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.gamesmannna.trytestapp.Common.Common;
        import com.example.gamesmannna.trytestapp.Model.User;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText edtPhone, edtName, edtPassword;
    Button SignUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (EditText)findViewById(R.id.edtRegName);
        edtPassword = (EditText)findViewById(R.id.edtRegPassword);
        edtPhone = (EditText) findViewById(R.id.edtRegPhone);

        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        //final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference table_user = database.getReference("User") ;

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Ждите . . .");
                mDialog.show();


                Common.ApiService.GetUserByPhoneId(edtPhone.getText().toString()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("GetUser",response.message());
                        User user=response.body();
                        if(user==null)
                        {
                            mDialog.dismiss();
                            user = new User (edtName.getText().toString(),edtPassword.getText().toString(),edtPhone.getText().toString());
                            //table_user.child(edtPhone.getText().toString()).setValue(user);

                            Common.ApiService.CreateUser(user).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Log.i("CreateUser",response.message());
                                    Toast.makeText(SignUp.this, "Регистрация прошла успешно!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.e("CreateUser",t.getMessage());
                                    Toast.makeText(SignUp.this, "Регистрация завершилась с ошибкой!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });


                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Пользователь с таким номером телефона есть в системе", Toast.LENGTH_SHORT).show();
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
//                        if (dataSnapshot.child(edtPhone.getText().toString()).exists())
//                        {
//                            mDialog.dismiss();
//                            Toast.makeText(SignUp.this, "Номер телефона уже зарегистрирован",Toast.LENGTH_SHORT).show();
//                        }
//
//                        else
//
//                        {
//                            mDialog.dismiss();
//                            User user = new User (edtName.getText().toString(),edtPassword.getText().toString(),edtPhone.getText().toString());
//                            table_user.child(edtPhone.getText().toString()).setValue(user);
//                            Toast.makeText(SignUp.this, "Регистрация прошла успешно!",Toast.LENGTH_SHORT).show();
//                            finish();
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
