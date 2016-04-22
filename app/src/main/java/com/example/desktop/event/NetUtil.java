package com.example.desktop.event;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NetUtil {
    public static class sendEvent extends AsyncTask<Event, Integer, String> {
        ProgressDialog pd;
        private Context mContext;
        private String str2;
        private AnimationDrawable animation;

        public sendEvent(Context context) {
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.ic_app));
            pd.setIndeterminate(true);
            pd.setMessage("Loading...");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Event... params) {
            String evtName = params[0].getEventName();
            String evtDate = params[0].getEventTime().toString();
            String evtDesc = params[0].getEventDesc();
            String lat = Double.toString(params[0].getLat());
            String lng = Double.toString(params[0].getLng());
            String org = Settings.USERNAME;

            String path = "http://" + Settings.IP_ADDRESS + "/addEvent.php";
            BufferedReader in;
            DataOutputStream out;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(2000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append("evt=");
                sb.append(URLEncoder.encode(evtName, "UTF-8"));
                sb.append("&etime=");
                sb.append(URLEncoder.encode(evtDate, "UTF-8"));
                sb.append("&desc=");
                sb.append(URLEncoder.encode(evtDesc, "UTF-8"));
                sb.append("&lat=");
                sb.append(URLEncoder.encode(lat, "UTF-8"));
                sb.append("&lng=");
                sb.append(URLEncoder.encode(lng, "UTF-8"));
                sb.append("&org=");
                sb.append(URLEncoder.encode(org, "UTF-8"));
                String str = new String(sb.toString());
                out.writeBytes(str);
                out.flush();
                out.close();

                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                str2 = "";
                while ((str2 += in.readLine()) != null && in.ready()) ;
            } catch (Exception e) {
            }
            return str2;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            pd.dismiss();
            Toast.makeText(mContext, "Server REPLY : " + res, Toast.LENGTH_SHORT).show();
        }
    }

    public static class getJSON extends AsyncTask<String, Integer, Void> {

        String rawData = "";
        private Context mContext;
        private SwipeRefreshLayout swipe;

        public getJSON(Context context) {
            this.mContext = context;
        }

        public getJSON(Context mContext, SwipeRefreshLayout swipe) {
            this.mContext = mContext;
            this.swipe = swipe;
        }

        @Override
        protected Void doInBackground(String... params) {
            //   ProgressDialog PD = new ProgressDialog(mContext);
            // PD.show();
            String path = "http://" + Settings.IP_ADDRESS + "/queryEvent.php";
            BufferedReader in;
            DataOutputStream out;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(2000);
                conn.setDoInput(true);

                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                while ((rawData += in.readLine()) != null && in.ready()) ;
                JSONArray arr = new JSONArray(rawData);
                Settings.EventHoldList = new ArrayList<Event>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    Event evt = new Event(o.getString("evtName"), o.getString("evtDesc"));
                    if (!o.getString("participant").isEmpty() && o.getString("participant") != null)
                        evt.setParticipant(o.getString("participant"));
                    evt.setOrigizator(o.getString("host"));
                    evt.setLatLng(o.getDouble("lat"), o.getDouble("lng"));
                    evt.setTimstamp(o.getString("evtTime"));
                    evt.setCREATE_TIME(o.getString("CREATE_TIME"));
                    Settings.EventHoldList.add(evt);
                    //  PD.dismiss();
                }
            } catch (Exception e) {
            }
            //  parseJSON(rawData);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Settings.adapter.clear();
            Settings.adapter.addAll(Settings.EventHoldList);
            Settings.adapter.notifyDataSetChanged();
            if (swipe != null)
                swipe.setRefreshing(false);
        }

    }

    public static class delEvent extends AsyncTask<Event, Void, String> {
        ProgressDialog pd;
        private Context mContext;
        private String str2;
        private String create_time;

        public delEvent(Context context) {
            this.mContext = context;
            pd = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Event... params) {
            create_time = params[0].getCREATE_TIME();
            String path = "http://" + Settings.IP_ADDRESS + "/deleteEvent.php";
            BufferedReader in;
            DataOutputStream out;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(2000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append("ctime=");
                sb.append(URLEncoder.encode(create_time, "UTF-8"));
                String str = new String(sb.toString());
                out.writeBytes(str);
                out.flush();
                out.close();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                str2 = "";
                while ((str2 += in.readLine()) != null && in.ready()) ;
            } catch (EOFException ex) {
            } catch (Exception e) {
            }
            return str2;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            System.out.print(pd.getProgress());
            pd.dismiss();
            Toast.makeText(mContext, "Server REPLY : " + res, Toast.LENGTH_SHORT).show();
        }
    }

    public static class updateEvent extends AsyncTask<Event, Integer, String> {
        private Context mContext;
        private String str2;

        public updateEvent(Context context) {
            this.mContext = context;
        }

        @Override
        protected String doInBackground(Event... params) {
            String evtName = params[0].getEventName();
            String evtDate = params[0].getEventTime().toString();
            String evtDesc = params[0].getEventDesc();
            String ptt = params[0].outputParticipant();
            String org = params[0].getOrigizator();
            String lat = Double.toString(params[0].getLat());
            String lng = Double.toString(params[0].getLng());
            String ctime = params[0].getCREATE_TIME();
            String path = "http://" + Settings.IP_ADDRESS + "/updateEvent.php";
            BufferedReader in;
            DataOutputStream out;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(2000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append("evt=");
                sb.append(URLEncoder.encode(evtName, "UTF-8"));
                sb.append("&etime=");
                sb.append(URLEncoder.encode(evtDate, "UTF-8"));
                sb.append("&desc=");
                sb.append(URLEncoder.encode(evtDesc, "UTF-8"));
                sb.append("&lat=");
                sb.append(URLEncoder.encode(lat, "UTF-8"));
                sb.append("&lng=");
                sb.append(URLEncoder.encode(lng, "UTF-8"));
                sb.append("&ctime=");
                sb.append(URLEncoder.encode(ctime, "UTF-8"));
                sb.append("&org=");
                sb.append(URLEncoder.encode(org, "UTF-8"));
                sb.append("&ptt=");
                sb.append(URLEncoder.encode(ptt, "UTF-8"));
                String str = new String(sb.toString());
                out.writeBytes(str);
                out.flush();
                out.close();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                str2 = "";
                while ((str2 += in.readLine()) != null && in.ready()) ;
            } catch (Exception e) {
            }
            return str2;
        }

        @Override
        protected void onPostExecute(String res) {
            Settings.adapter.clear();
            Settings.adapter.addAll(Settings.EventHoldList);
            Settings.adapter.notifyDataSetChanged();
            super.onPostExecute(res);
            Toast.makeText(mContext, "Server REPLY : " + res, Toast.LENGTH_SHORT).show();
        }
    }
}