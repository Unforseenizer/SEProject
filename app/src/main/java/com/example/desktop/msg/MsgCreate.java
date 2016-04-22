package com.example.desktop.msg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

public class MsgCreate extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    EditText recipient, title, content;
    Button submit;
    Message output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_create);
        toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Send Message");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipient = (EditText) findViewById(R.id.input_send);
        title = (EditText) findViewById(R.id.input_title);
        content = (EditText) findViewById(R.id.input_content);
        submit = (Button) findViewById(R.id.msg_create);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        output = new Message(Settings.USERNAME
                , recipient.getText().toString()
                , title.getText().toString()
                , content.getText().toString());
        new MsgTask.sendMsg().execute(output);
        this.finish();
    }
}
