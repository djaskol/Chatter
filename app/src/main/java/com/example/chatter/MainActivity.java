package com.example.chatter;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_page_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_tabs_pager)
    ViewPager myViewPager;
    @BindView(R.id.main_tabs)
    TabLayout myTabLayout;

    private TabsAccessorAdapter myTabs;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chatter");

        myTabs = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabs);

        myTabLayout.setupWithViewPager(myViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser == null){

            sendUserToLoginActivity();
        }
    }

    private void sendUserToLoginActivity() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
