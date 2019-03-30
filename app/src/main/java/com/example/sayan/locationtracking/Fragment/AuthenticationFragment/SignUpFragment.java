package com.example.sayan.locationtracking.Fragment.AuthenticationFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sayan.locationtracking.Activity.MapNewActivity;
import com.example.sayan.locationtracking.Authentication.EmailAuthenticate;
import com.example.sayan.locationtracking.R;
import com.example.sayan.locationtracking.ToastForInputValidation.CustomToast;
import com.example.sayan.locationtracking.UtilityStringClass.Utils;
//import com.google.android.gms.plus.Plus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SignUpFragment extends Fragment implements View.OnClickListener
{
    private View view;
    private EditText fullName, emailId, mobileNumber, location, password, confirmPassword;
    private TextView login;
    private Button signUpButton, mGoogleSignIn;
    private CheckBox terms_conditions;

    private EmailAuthenticate emailAuthenticate;

    private FirebaseAuth auth;

     String mFullName, mEmailId, mLocation, mPassword, mMobileNumber, mConfirmpassword;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);

        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

     //   signUpButton.setEnabled(false); // initially button is disabled
        emailAuthenticate = new EmailAuthenticate(getActivity());


       // FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();


        // Setting text selector over textviews
        @SuppressLint("ResourceType")
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set Listeners

    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    /**
     *  On Click methods
     * @param
     */


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                // Now call authenticate() of Authenticate interface implemented in EmailAuthentication class


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                updateUI(currentUser);
                break;

            case R.id.already_user:

                // Replace login fragment
                //new MainActivity().replaceLoginFragment();
                Fragment logInFragment = new LogInFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameContainer, logInFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

        }
    }


    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        mFullName = fullName.getText().toString();
        mEmailId = emailId.getText().toString();
        mMobileNumber = mobileNumber.getText().toString();
        mLocation = location.getText().toString();
        mPassword = password.getText().toString();
        mConfirmpassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(mEmailId);

        // Check if all strings are null or not
        if (mFullName.equals("") || mFullName.length() == 0
                || mEmailId.equals("") || mEmailId.length() == 0
                || mMobileNumber.equals("") || mMobileNumber.length() == 0
                || mLocation.equals("") || mLocation.length() == 0
                || mPassword.equals("") || mPassword.length() == 0
                || mConfirmpassword.equals("")
                || mConfirmpassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!mConfirmpassword.equals(mPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff

        else
          //  Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT).show();
        {
           // signUpButton.setEnabled(true);
            emailAuthenticate.authenticate(mEmailId, mPassword);
        }
        }

    /**
     * getter method
     *
     *
     * @return
     */


    public String getmPassword() {
        return mPassword;
    }

    public String getmEmailId() {
        return mEmailId;
    }

    private void updateUI(FirebaseUser user)
    {
        Intent intent=new Intent(getActivity(), MapNewActivity.class);
        //intent.putExtra("UserName",user.toString());
        startActivity(intent);
    }
}