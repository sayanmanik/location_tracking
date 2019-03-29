package com.example.sayan.locationtracking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sayan.locationtracking.Fragment.AuthenticationFragment.LogInFragment;
import com.example.sayan.locationtracking.Fragment.AuthenticationFragment.SignUpFragment;
import com.example.sayan.locationtracking.R;

public class AuthenticateActivity extends AppCompatActivity
{


    private Intent intent;

    public static AuthenticateActivity authenticateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        /**
         *  take intent string from previous activity and launch new fragment according to the string value
         **/

        intent=getIntent();


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(intent.getStringExtra("EXTRA").equals(getString(R.string.signUp)))
        {
            transaction.add(R.id.frameContainer, new SignUpFragment());
        }

        else if(intent.getStringExtra("EXTRA").equals(getString(R.string.login)))
        {
            transaction.add(R.id.frameContainer, new LogInFragment());
        }

        transaction.commit();

    }

    public static synchronized AuthenticateActivity getInstance()
    {
        if(authenticateActivity==null)
        {
            authenticateActivity = new AuthenticateActivity();
        }
        return authenticateActivity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }

}