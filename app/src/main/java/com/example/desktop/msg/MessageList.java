package com.example.desktop.msg;

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

    public static boolean isNotifyBit() {
        return NOTIFY_BIT;
    }

    public ArrayList<Message> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Message> msgList) {
        this.msgList.clear();
        this.msgList.addAll(msgList);
    }

    public ArrayList<Message> fetchUnread() {
        int oldSize = unread.size();
        unread.clear();
        if (!msgList.isEmpty() && msgList != null) {
            for (Message msg : msgList) {
                if (msg.getMsgTime().after(timestamp))
                    unread.add(msg);
            }
            if (unread.size() > oldSize)
                NOTIFY_BIT = false;
        }
        return unread;
    }

    public void clearUnread() {
        if (unread != null && !unread.isEmpty()) {
            Message max = Collections.max(unread, new Comparator<Message>() {
                @Override
                public int compare(Message lhs, Message rhs) {
                    return lhs.getMsgTime().compareTo(rhs.getMsgTime());
                }
            });
            timestamp = max.getMsgTime();
        }
        unread.clear();
        NOTIFY_BIT = false;
    }

    public void setNotify() {
        NOTIFY_BIT = true;
    }
}
