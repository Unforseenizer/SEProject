package com.example.desktop.msg;

import android.util.Log;

import com.example.desktop.msg.msg_done.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageList {
    private static ArrayList<Message> msgList = new ArrayList<>();
    private static ArrayList<Message> unread = new ArrayList<>();
    private static Timestamp timestamp = new Timestamp(1);
    private static boolean NOTIFY_BIT = false;

    public static ArrayList<Message> getUnread() {
        return unread;
    }

    public ArrayList<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList.clear();
        this.msgList.addAll(msgList);
    }

    public ArrayList<Message> fetchUnread() {
        unread.clear();
        if (!msgList.isEmpty() && msgList != null) {
            for (Message msg : msgList) {
                if (msg.getMsgTime().after(timestamp))
                    unread.add(msg);
            }
        }
        Log.e("UNREAD", String.valueOf(msgList.size()));
        Log.e("UNREAD", String.valueOf(NOTIFY_BIT));
        return unread;
    }

    public void clearUnread() {
        Message max = (unread != null && !unread.isEmpty())
                ? Collections.max(unread, new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                return lhs.getMsgTime().compareTo(rhs.getMsgTime());
            }
        }) : null;
        timestamp = (max != null) ? max.getMsgTime() : new Timestamp(1);
        unread.clear();
        NOTIFY_BIT = false;
        Log.e("Clear Unread", timestamp.toString());
    }

    public void setNotify() {
        NOTIFY_BIT = true;
    }

}
