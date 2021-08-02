package com.pixie.android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pixie.android.ui.login.LoginActivity;

public class RbpiFragment extends Fragment {
    private EditText ip;
    Button rbpiButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ip = v.findViewById(R.id.rbpiText);
        rbpiButton = v.findViewById(R.id.rbpiButton);

        rbpiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // firebase 라즈베리파이 ip 주소 저장
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("ip");

                ref.setValue(ip.getText().toString());
                Toast.makeText(container.getContext(), "연결 시도", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }
}