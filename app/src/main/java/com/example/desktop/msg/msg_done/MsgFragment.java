package com.example.desktop.msg.msg_done;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.desktop.msg.MessageList;
import com.example.desktop.project.MainActivity;
import com.example.desktop.project.R;
import com.example.desktop.project.Settings;

import java.util.List;


public class MsgFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    MsgAdapter msgAdapter;
    MessageList messageList = new MessageList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler);

        List<Message> list = messageList.getMsgList();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        msgAdapter = new MsgAdapter(getContext(), list);

        recyclerView.setAdapter(msgAdapter);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.msg_swipe);
        swipe.setOnRefreshListener(this);

        new MsgTask.getMsg(getContext(), msgAdapter, swipe).execute(Settings.USERNAME);
        messageList.clearUnread();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View root = inflater.inflate(R.layout.msg_list, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Message");
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_msg, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_msgcreate:
                Intent msg_create = new Intent(getActivity(), MsgCreate.class);
                startActivity(msg_create);
        }
        return true;
    }

    @Override
    public void onRefresh() {
        new MsgTask.getMsg(getContext(), msgAdapter, swipe).execute(Settings.USERNAME);
    }
}
