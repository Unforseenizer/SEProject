package com.example.desktop.msg.msg_done;

import android.os.AsyncTask;
import android.util.Log;

import com.example.desktop.msg.MessageList;
import com.example.desktop.project.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MsgTask {
    public static class sendMsg extends AsyncTask<Message, Void, String> {
        String sender, recipient, title, content;
        String path = "http://" + Settings.IP_ADDRESS + "/addMessage.php";
        BufferedReader in;
        DataOutputStream out;
        StringBuilder sb;
        String reply;

        @Override
        protected String doInBackground(Message... params) {
            Message msg = params[0];
            sender = msg.getSender();
            recipient = msg.getReceipt();
            title = msg.getTitle();
            content = msg.getContent();

            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                out = new DataOutputStream(conn.getOutputStream());
                sb = new StringBuilder();
                sb.append("sender=");
                sb.append(URLEncoder.encode(sender, "UTF-8"));
                sb.append("&recipient=");
                sb.append(URLEncoder.encode(recipient, "UTF-8"));
                sb.append("&title=");
                sb.append(URLEncoder.encode(title, "UTF-8"));
                sb.append("&content=");
                sb.append(URLEncoder.encode(content, "UTF-8"));
                String str = new String(sb.toString());
                Log.e("POST", str);
                out.writeBytes(str);
                out.flush();
                out.close();
                Log.e("test", str);
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                reply = "";
                while ((reply += in.readLine()) != null && in.ready()) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reply;
        }
    }

    public static class getMsg extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String path = "http://" + Settings.IP_ADDRESS + "/queryMessage.php";
            BufferedReader in;
            DataOutputStream out;
            StringBuilder rawData = new StringBuilder();

            List<Message> msgList = new ArrayList<>();
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                out = new DataOutputStream(conn.getOutputStream());
                out.writeUTF(URLEncoder.encode(Settings.USERNAME, "UTF-8"));
                out.flush();
                out.close();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while (rawData.append(in.readLine()) != null && in.ready()) ;
                JSONArray arr = new JSONArray(rawData.toString());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Message dump = new Message(obj.getString("sender")
                            ,obj.getString("recipient")
                            ,obj.getString("title")
                            ,obj.getString("content"));
                    dump.setMsgTime(obj.getString("createtime"));
                    msgList.add(dump);
                }
                new MessageList().setMsgList(msgList);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
