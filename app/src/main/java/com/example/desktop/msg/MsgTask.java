package com.example.desktop.msg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.desktop.activity.MainActivity;
import com.example.desktop.project.R;
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
                conn.setConnectTimeout(2000);
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
            }
            return reply;
        }
    }

    public static class getMsg extends AsyncTask<String, Void, String> {
        MessageList messageList = new MessageList();
        ArrayList<Message> unread = new ArrayList<>();
        Context mContext;
        private SwipeRefreshLayout swipe;
        private RecyclerView.Adapter adapter;

        public getMsg(Context mContext, RecyclerView.Adapter adapter, SwipeRefreshLayout swipe) {
            this.mContext = mContext;
            this.adapter = adapter;
            this.swipe = swipe;
        }

        public getMsg(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... params) {
            String path = "http://" + Settings.IP_ADDRESS + "/queryMessage.php?recipient=" + params[0];
            BufferedReader in;
            DataOutputStream out;
            StringBuilder rawData = new StringBuilder();

            List<Message> msgList = new ArrayList<>();
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(2000);
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
                            , obj.getString("recipient")
                            , obj.getString("title")
                            , obj.getString("content"));
                    dump.setMsgTime(obj.getString("createtime"));
                    msgList.add(dump);
                }
                messageList.setMsgList(msgList);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (swipe != null)
                swipe.setRefreshing(false);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }

            if (!(unread = messageList.fetchUnread()).isEmpty() && mContext != null && !messageList.isNotifyBit() && Settings.IS_NOTIFY) {

                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent2 = new Intent(mContext, MainActivity.class);
                intent2.putExtra("Fragment", "MsgFragment");
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent2, 0);
                long[] vib = {200, 200, 200, 200};
                Notification notification = new Notification.Builder(mContext)
                        .setSmallIcon(R.drawable.ic_app)
                        .setContentTitle(messageList.getUnread().get(messageList.getUnread().size() - 1).getTitle())
                        .setContentText(String.format("You have %d unread message.", messageList.getUnread().size()))
                        .setContentInfo("This is Info")
                        .setContentIntent(pendingIntent)
                        .setLights(0xff0000ff, 100, 100)
                        .setVibrate(vib)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                        .build();
                messageList.setNotify();
                notificationManager.notify(1, notification);

            }
        }
    }
}
