package com.example.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    @BindView(R.id.button_register)
    Button registerButton;
    @BindView(R.id.register_email)
    EditText emailRegister;
    @BindView(R.id.register_password)
    EditText passwordRegister;
    @BindView(R.id.already_have_account_register)
    TextView alreadyHaveAnAccount;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        Button register = registerButton;
        TextView already = alreadyHaveAnAccount;

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivitiy();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show();

        }
        else {

            progressBar.setVisibility(View.VISIBLE);// To Show ProgressBar
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String currentUserId = mAuth.getCurrentUser().getUid();
                        RootRef.child("Users").child(currentUserId).setValue("");
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(registerActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        sendUserToMainActivitiy();
                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(registerActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void goToRegisterActivitiy(){

        Intent registerIntent = new Intent(registerActivity.this, LoginActivity.class);
        startActivity(registerIntent);

    }

    private void sendUserToMainActivitiy(){

        Intent mainIntent = new Intent(registerActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }




}
