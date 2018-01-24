package com.example.desktop.myproject.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.desktop.myproject.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Button buttonRegister;
    private EditText editTextphone,editTextpassword,editTextconfirmpassword;

    private Context mContext;
    private DatabaseStudent mManager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mManager = new DatabaseStudent(this);
        mContext = this;
        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextphone = (EditText) findViewById(R.id.editTextphone);
        editTextpassword = (EditText) findViewById(R.id.editTextpassword);
        editTextconfirmpassword = (EditText) findViewById(R.id.editTextconfirmpassword);
    }
    private void initViews() {
        String phone =  editTextphone.getText().toString().trim();
        String password =  editTextpassword.getText().toString().trim();
        String confirmpassword =  editTextconfirmpassword.getText().toString().trim();

        if (password.equals(confirmpassword)) {
            StudentUser user = new StudentUser(phone, password);
            long rowId = mManager.registerUser(user);

            if (rowId == -1) {
                String message = getString(R.string.register_error_message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            } else {
                String message = getString(R.string.register_success);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                finish();
            }

        } else {
            String message = getString(R.string.register_password_error);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(RegisterActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(RegisterActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        if(v == buttonRegister) {
            initViews();
            isServicesOK();
            Intent intent = new Intent(RegisterActivity.this, MapActivity.class);
            startActivity(intent);
        }
    }
}
