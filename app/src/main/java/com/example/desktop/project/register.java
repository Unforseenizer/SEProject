package com.example.desktop.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    private EditText user;
    private EditText PW;
    private EditText re_password;
    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_0);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        user = (EditText) findViewById(R.id.r1_user);
        PW = (EditText) findViewById(R.id.r1_password);
        re_password = (EditText) findViewById(R.id.r1_repassword);

        btnCreateAccount = (Button) findViewById(R.id.r1_next);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                final String userName = user.getText().toString();
                final String password = PW.getText().toString();
                String confirmPassword = re_password.getText().toString();

                // check if any of the fields are vaccant
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent(register.this, register2.class);
                    intent.putExtra("UserName", userName);
                    intent.putExtra("PassWord", password);
                    startActivity(intent);
                }

                Button back = (Button) findViewById(R.id.r1_back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }

        });

    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
};