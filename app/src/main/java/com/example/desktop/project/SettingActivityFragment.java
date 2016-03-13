package com.example.desktop.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivityFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Communicator setchg;
    EditText ipaddr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        Switch switch1 = (Switch) view.findViewById(R.id.switch_notification);
        ipaddr = (EditText) view.findViewById(R.id.IPAddress);

        switch1.setChecked(SettingActivity.isNotification);
        switch1.setOnCheckedChangeListener(this);
        ipaddr.setText(Settings.IP_ADDRESS);

        return view;
    }

    public void setcomm(Communicator setchg) {
        this.setchg = setchg;
    }

    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
            setchg.respond(true);
        } else {
            Toast.makeText(getContext(), "Disabled", Toast.LENGTH_SHORT).show();
            setchg.respond(false);
        }
    }

    @Override
    public void onDestroyView() {
        Settings.IP_ADDRESS = ipaddr.getText().toString();
        super.onDestroyView();
    }

    public interface Communicator {
        void respond(boolean config);
    }
}
