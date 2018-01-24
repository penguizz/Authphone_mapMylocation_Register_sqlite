package com.example.desktop.myproject.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desktop.myproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextphone, editTextpassword;
    private TextView textViewSignup;
    private Context mContext;

    private DatabaseStudent mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mManager = new DatabaseStudent(this);

        mContext = this;

        buttonLogin = (Button) findViewById(R.id.b_Login);
        editTextphone =(EditText) findViewById(R.id.editTextphone);
        editTextpassword =(EditText) findViewById(R.id.editTextpassword);
        textViewSignup =(TextView) findViewById(R.id.textViewSignup);

    }

    private void checkLogin() {
        String phone =  editTextphone.getText().toString().trim();
        String password =  editTextpassword.getText().toString().trim();

        StudentUser user = new StudentUser(phone, password);

        StudentUser validateUser = mManager.checkUserLogin(user);

        if (null == validateUser) {
            String message = getString(R.string.login_error_message);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(mContext, MapActivity.class);
            intent.putExtra(StudentUser.Column.STUDENT_PHONE, validateUser.getPhone());
            intent.putExtra(StudentUser.Column.STUDENT_ID, validateUser.getId());
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            checkLogin();
        }
        if(v == textViewSignup){
            Intent intent = new Intent(mContext, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
