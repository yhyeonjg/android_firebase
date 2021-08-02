package com.pixie.android.ui.signUp;

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

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private FirebaseAuth firebaseAuth;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.signUpEmail);
        editTextPassword = (EditText) findViewById(R.id.signUpPassword);

        signUpButton = findViewById(R.id.signUpFirebase);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NotNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // 회원가입 성공시
                                        Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        // 계정이 중복된 경우
                                        Toast.makeText(SignUpActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignUpActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}