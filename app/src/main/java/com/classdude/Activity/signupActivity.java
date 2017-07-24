package com.classdude.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classdude.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import de.hdodenhof.circleimageview.CircleImageView;

public class signupActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private TextView signupSkip;
    private Button mSignUpButton;
    private TextView mSignUpReferLogin;
    private CircleImageView mUserProfilePic;
    private ProgressDialog progressDialog;

    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mName             = (EditText) findViewById(R.id.signUpName);
        mUsername         = (EditText) findViewById(R.id.signUpUsername);
        mEmail            = (EditText) findViewById(R.id.signUpEmail);
        mPassword         = (EditText) findViewById(R.id.signUpPassword);
        mConfirmPassword  = (EditText) findViewById(R.id.signUpConfirmPassword);
        mSignUpButton     = (Button) findViewById(R.id.signUpButton);
        mSignUpReferLogin = (TextView) findViewById(R.id.signUpReferLogin);
        mUserProfilePic   = (CircleImageView) findViewById(R.id.signUpDefaultUser);
        signupSkip        = (TextView)findViewById(R.id.signupSkip);

        signupSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupActivity.this,MainActivity.class));
            }
        });

        mUserProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mName.getText().toString().trim();
                String userName= mUsername.getText().toString().trim();
                String Email   = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                if(validateInformation(mName,mUsername,mEmail,mPassword,mUserProfilePic))
                {
                    if(!Password.equals(confirmPassword))
                    {
                        mConfirmPassword.setError("Password doesn't match");
                    }
                    else
                    {
                        progressDialog = new ProgressDialog(signupActivity.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Registering your account...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        Log.i("Confirm","success");
                        createUser(Email,Password,Name,userName);
                    }
                }

            }
        });
    }

    private void createUser(String email, String password, String name, String userName)
    {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           // Log.d("myMessage", "createUserWithEmail:success");
                            progressDialog.dismiss();
                            Toast.makeText(signupActivity.this, "Authentication Success.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        } else {
                           // Log.w("myMessage", "createUserWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(signupActivity.this, "Authentication failed: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private boolean validateInformation(EditText mName, EditText mUsername, EditText mEmail, EditText mPassword, CircleImageView mConfirmPassword)
    {
        mName.setError(null);
        mUsername.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);

        if(mName.getText().toString().trim().isEmpty())
        {
            mName.setError("Profile name field cannot be empty.");
            return false;
        }else if(mName.getText().toString().length() > 30)
        {
            mName.setError("Profile name is too long.");
            return false;
        }else  if (mEmail.getText().toString().isEmpty()) {
            mEmail.setError("Email field cannot be empty.");
            return false;
        }else if(mUsername.getText().toString().trim().isEmpty())
        {
            mUsername.setError("Username field cannot be empty.");
            return false;
        } else if (mPassword.getText().toString().trim().isEmpty())
        {
            mPassword.setError("Password field cannot be empty.");
            return false;
        } else if (mPassword.length() < 8)
        {
            mPassword.setError("Password must be 8 characters long.");
            return false;
        }
        return  true;
    }
    public void showFileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }
}
