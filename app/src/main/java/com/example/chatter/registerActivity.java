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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();
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
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(registerActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        goToRegisterActivitiy();
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


}
