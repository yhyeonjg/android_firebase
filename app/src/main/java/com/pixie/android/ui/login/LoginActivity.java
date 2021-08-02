package com.pixie.android.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pixie.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private FirebaseAuth firebaseAuth;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.loginEmail);
        editTextPassword = (EditText) findViewById(R.id.loginPassword);

        loginButton = findViewById(R.id.loginFirebase);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")
                    && editTextPassword.getText().toString().length()>=6) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // 로그인 성공시
                                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        // 계정이 중복된 경우
                                        Toast.makeText(LoginActivity.this, "존재하지 않는 계정입니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(LoginActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}