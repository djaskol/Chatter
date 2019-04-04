package com.example.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    @BindView(R.id.phone_login_button)
    Button phoneLoginButton;
    @BindView(R.id.button_login)
    Button loginButton;
    @BindView(R.id.login_email)
    EditText emailLogin;
    @BindView(R.id.login_password)
    EditText passwordLogin;
    @BindView(R.id.forgot_password)
    TextView forgotPassword;
    @BindView(R.id.need_new_account)
    TextView needAccout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        TextView needAcc = needAccout;

        needAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegistration();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowUserToLogin();
            }
        });

    }

    private void allowUserToLogin() {
        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show();

        }
        else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendUserToMainActivity();
                        Toast.makeText(LoginActivity.this, "Logged In Succesfully", Toast.LENGTH_SHORT).show();
                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
        sendUserToMainActivity();
        }
    }

    private void sendUserToMainActivity(){

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }
    private void sendUserToRegistration(){

            Intent registerIntent = new Intent(LoginActivity.this, registerActivity.class);
            startActivity(registerIntent);

    }
}
