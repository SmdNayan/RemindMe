package com.nayan.me.remindme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.R.attr.password;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private BaseActivity baseActivity;

    private static final String TAG = "MainActivity";

    private EditText emailET;
    private EditText passwordET;


    private FirebaseAuth signInAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = (EditText) findViewById(R.id.signin_email_ID);
        passwordET = (EditText) findViewById(R.id.sign_in_password_ID);

        findViewById(R.id.sign_in_btn).setOnClickListener(this);
        findViewById(R.id.create_acc_btn).setOnClickListener(this);

        signInAuth = FirebaseAuth.getInstance();

        baseActivity = new BaseActivity();

//        sign_up = new Sign_Up();





    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        baseActivity.showProgressDialog();

         //[START sign_in_with_email]
        signInAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "SignIn Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ToDoList.class);
                            startActivity(intent);
                           // FirebaseUser user = signInAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
//                        baseActivity.hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    // Check email for valid or invalid

    private boolean validateForm() {
        boolean valid = true;

        String email = emailET.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailET.setError("Required.");
            valid = false;
        } else {
            emailET.setError(null);
        }

        String password = passwordET.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordET.setError("Required.");
            valid = false;
        } else {
            passwordET.setError(null);
        }

        return valid;
    }




    public void signOut() {
        signInAuth.signOut();

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_btn) {
            signIn(emailET.getText().toString(),passwordET.getText().toString());
        } else if(i == R.id.create_acc_btn) {
            Intent intent = new Intent(MainActivity.this, Sign_Up.class);
            startActivity(intent);
        }

    }
}
