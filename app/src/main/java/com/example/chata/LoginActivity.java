package com.example.chata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private Button LoginButton;
    private EditText UserPhone, UserPassword;
    private TextView NewAccount, ForgetPassword;

    private FirebaseAuth mAuth;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        InitialiseFields();

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });



        NewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendUserToRegisterActivity();

            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogin();

            }
        });


    }


    private void AllowUserToLogin() {

        String login_password = UserPassword.getText().toString();
        String login_phone = UserPhone.getText().toString();

        if (TextUtils.isEmpty(login_phone))
        {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(login_password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        else {


            loadingBar.setTitle("Sign in");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();




            mAuth.signInWithEmailAndPassword(login_phone, login_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                SendUserToMainActivity();
                                Toast.makeText(LoginActivity.this, "Logged in Successful", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error :" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }


                        }
                    });
        }

    }

    private void InitialiseFields() {

        LoginButton = (Button) findViewById(R.id.login_button);
        UserPhone = (EditText) findViewById(R.id.login_phone);
        UserPassword = (EditText) findViewById(R.id.login_password);
        NewAccount = (TextView) findViewById(R.id.new_account_link);
        ForgetPassword = (TextView) findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);

    }


    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();

    }

    private void SendUserToRegisterActivity() {

        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);

    }
}
