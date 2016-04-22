package com.example.desktop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import java.util.regex.*;

import com.example.desktop.project.LoginDataBaseAdapter;
import com.example.desktop.project.R;

public class register extends Activity implements TextWatcher{
    LoginDataBaseAdapter loginDataBaseAdapter;
    private EditText user;
    private EditText PW;
    private EditText re_password;
    private Button btnCreateAccount;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int i = 0;
        try
        {
            i = Integer.valueOf(s.toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        boolean b = m.find();

        if (b) {
            Toast.makeText(register.this, "User ID or password should not contain special characters", Toast.LENGTH_SHORT).show();
            if (user.hasFocus())
                user.selectAll();
            else if (PW.hasFocus())
                PW.selectAll();
            else
                re_password.selectAll();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_0);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        user = (EditText) findViewById(R.id.r1_user);
        PW = (EditText) findViewById(R.id.r1_password);
        re_password = (EditText) findViewById(R.id.r1_repassword);

        user.addTextChangedListener(this);
        PW.addTextChangedListener(this);
        re_password.addTextChangedListener(this);

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