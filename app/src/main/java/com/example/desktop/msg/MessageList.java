package com.example.desktop.msg;

import com.example.desktop.msg.msg_done.Message;
import com.example.desktop.msg.msg_done.MsgTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MessageList {
    private static ArrayList<Message> msgList = new ArrayList<>();
    private static ArrayList<Message> unread;
    private static Time timestamp;

    public void setMsgList(List<Message> msgList) {
        this.msgList.clear();
        this.msgList.addAll(msgList);
    }

    public ArrayList<Message> getMsgList() {
        new MsgTask.getMsg().execute();
        return msgList;
    }

    public void checkUpdate() {
    }
    
}
