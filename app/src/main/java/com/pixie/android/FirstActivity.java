package com.pixie.android;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pixie.android.ui.login.LoginActivity;
import com.pixie.android.ui.signUp.SignUpActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class FirstActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;
    TextView account;

    ImageButton pixieButton;
    Button loginButton, signUpButton;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();

        pixieButton = findViewById(R.id.imageBtn);
        loginButton = findViewById(R.id.loginBtn);
        signUpButton = findViewById(R.id.signUpBtn);

        if (user == null) {
            getSupportActionBar().hide();

            pixieButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
        } else {
            getSupportActionBar().show();

            pixieButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            signUpButton.setVisibility(View.INVISIBLE);

            navigationView=findViewById(R.id.nav);
            drawerLayout=findViewById(R.id.layout_drawer);
            navigationView.setItemIconTintList(null);  // ?????? ??????
            fragmentView(1);

            // setcontentview ?????? ?????????, navigationView ??????
            account = navigationView.getHeaderView(0).findViewById(R.id.account);
            account.setText(user.getEmail());

            //????????????????????? ???????????? ???????????????
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.connectButton:
                            fragmentView(1);
                            break;
                        case R.id.checkVideo:
                            fragmentView(2);
                            break;
                        case R.id.report:
                            fragmentView(3);
                            break;
                        case R.id.logoutButton:
                            logout();
                            Toast.makeText(FirstActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            break;
                    }

                    // Drawer ??????
                    drawerLayout.closeDrawer(navigationView);

                    return false;
                }
            });

            //Drawer ????????? ?????? ?????? ?????? ??????
            barDrawerToggle = new ActionBarDrawerToggle(FirstActivity.this, drawerLayout, R.string.app_name,R.string.app_name);
            //ActionBar(?????????)??? ??? or ???????????? ????????? ?????????????????? ????????????...
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //??????????????? ???????????? ????????????
            //??????????????? ????????? ?????????
            barDrawerToggle.syncState();

            //?????? ???????????? ????????????????????? ???????????? ???????????????
            drawerLayout.addDrawerListener(barDrawerToggle);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        barDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    private void fragmentView(int fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(fragment){
            case 1:
                RbpiFragment rbpiFragment = new RbpiFragment();
                transaction.replace(R.id.fragment, rbpiFragment);
                transaction.commit();
                break;
            case 2:
                VideoFragment videoFragment = new VideoFragment();
                transaction.replace(R.id.fragment, videoFragment);
                transaction.commit();
                break;
            case 3:
                ReportFragment reportFragment = new ReportFragment();
                transaction.replace(R.id.fragment, reportFragment);
                transaction.commit();
                break;
        }
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        onStart();
    }
}