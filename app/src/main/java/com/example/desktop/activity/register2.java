package com.example.desktop.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.desktop.project.LoginDataBaseAdapter;
import com.example.desktop.project.R;

public class register2 extends Activity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    private Button btnCreateAccount;
    private EditText firstName;
    private EditText lastName;
    private EditText bDay;
    private EditText bMonth;
    private EditText bYear;
    private EditText eMail;
    private RadioGroup SexGroup;
    private RadioButton SexSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_1);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        bYear = (EditText) findViewById(R.id.year);
        bMonth = (EditText) findViewById(R.id.month);
        bDay = (EditText) findViewById(R.id.day);
        SexGroup = (RadioGroup) findViewById(R.id.sex);
        eMail = (EditText) findViewById(R.id.email);

        btnCreateAccount = (Button) findViewById(R.id.r2_finish);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String FirstName = firstName.getText().toString();
                String LastName = lastName.getText().toString();
                String FBirthday = bYear.getText().toString() + bMonth.getText().toString() + bDay.getText().toString();
                String Email = eMail.getText().toString();
                String UserName = getIntent().getStringExtra("UserName");
                String PassWord = getIntent().getStringExtra("PassWord");

                int selectedId = SexGroup.getCheckedRadioButtonId();
                SexSelect = (RadioButton) findViewById(selectedId);
                String Sex = SexSelect.getText().toString();


                // Save the Data in Database
                loginDataBaseAdapter.insertEntry(UserName, PassWord, FirstName, LastName, Sex, FBirthday, Email);

                AlertDialog.Builder builder = new AlertDialog.Builder(register2.this);
                builder.setTitle("Confirm register?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        register2.this.finish();
                        Intent intent = new Intent(register2.this, MainActivity.class);
                        Toast.makeText(register2.this, "Complete", Toast.LENGTH_SHORT);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().
                        show();

            }
        });
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}