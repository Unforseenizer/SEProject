package com.example.desktop.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.desktop.project.R;

public class SettingActivityFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    //Communicator setchg;
    EditText ipaddr, username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        Switch switch1 = (Switch) view.findViewById(R.id.switch_notification);
        ipaddr = (EditText) view.findViewById(R.id.IPAddress);
        username = (EditText) view.findViewById(R.id.settings_username);

        switch1.setOnCheckedChangeListener(this);
        ipaddr.setText(Settings.IP_ADDRESS);
        username.setText(Settings.USERNAME);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
            Settings.IS_NOTIFY = true;
            //setchg.respond(true);
        } else {
            Toast.makeText(getContext(), "Disabled", Toast.LENGTH_SHORT).show();
            Settings.IS_NOTIFY = false;
            //setchg.respond(false);
        }
    }

    @Override
    public void onDestroyView() {
        Settings.IP_ADDRESS = ipaddr.getText().toString();
        Settings.USERNAME = username.getText().toString();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("setting", getActivity().MODE_PRIVATE).edit();
        editor.putBoolean("isNotification", Settings.IS_NOTIFY);
        editor.commit();
        super.onDestroyView();
    }

           /* onCreate
        switch1.setChecked(SettingActivity.isNotification);
        */
    /*public void setcomm(Communicator setchg) {
        this.setchg = setchg;
    }*/

    /*public interface Communicator {
        void respond(boolean config);
    }*/
}
