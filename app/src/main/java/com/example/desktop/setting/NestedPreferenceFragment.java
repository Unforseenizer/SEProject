package com.example.desktop.setting;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.desktop.project.R;

public class NestedPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    static final String TAG = "NestedFragment";
    EditTextPreference ip, username;
    CheckBoxPreference notify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        ip = (EditTextPreference) findPreference("ip");
        username = (EditTextPreference) findPreference("username");
        notify = (CheckBoxPreference) findPreference("notification");

        ip.setOnPreferenceChangeListener(this);
        username.setOnPreferenceChangeListener(this);
        notify.setOnPreferenceChangeListener(this);

        initScreen();
    }

    public void initScreen() {
        SPUtil.fetchSP("Settings", getActivity());
        if (Settings.IP_ADDRESS != "")
        ip.setSummary("IP Address : " + Settings.IP_ADDRESS);
        if (Settings.USERNAME != "")
            username.setSummary("Username : " + Settings.USERNAME);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case "ip":
                preference.setSummary("IP Address : " + newValue);
                Settings.IP_ADDRESS = newValue.toString();
                break;
            case "username":
                preference.setSummary("Username : " + newValue);
                Settings.USERNAME = newValue.toString();
                break;
            case "notification":
                Settings.IS_NOTIFY = Boolean.valueOf(newValue.toString());
                Toast.makeText(getActivity(), newValue.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        Log.e(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }
}
