package com.classdude.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classdude.R;

public class loginActivity extends AppCompatActivity {
    private TextView mloginReferSignUp;
    private TextView loginSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mloginReferSignUp = (TextView) findViewById(R.id.loginReferSignup);
        loginSkip = (TextView) findViewById(R.id.loginSkip);

        mloginReferSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,signupActivity.class));
                finish();
            }
        });

        loginSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,MainActivity.class));
            }
        });
    }
}
