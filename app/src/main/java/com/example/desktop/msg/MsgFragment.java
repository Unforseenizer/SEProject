package com.example.desktop.msg;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.desktop.activity.MainActivity;
import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

import java.util.List;


public class MsgFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerView.OnItemTouchListener, MsgAdapter.ClickListener {
    static final String TAG = "MsgFragment";
    static View root;
    static PopupWindow mPopupWindow;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    MsgAdapter msgAdapter;
    MessageList messageList = new MessageList();
    TextView v1, v2, v3, v4;

    public static boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                mPopupWindow.showAtLocation(root.findViewById(R.id.recycler), Gravity.BOTTOM, 0, 0);
            }
            return true;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        messageList.clearUnread();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        root = inflater.inflate(R.layout.msg_list, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Message");

        setupPopupWindow(inflater);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return root;
    }

    public void setupPopupWindow(LayoutInflater inflater) {
        View popupView = inflater.inflate(R.layout.msg_dialog, null);

        v1 = (TextView) popupView.findViewById(R.id.msg_infoSender);
        v2 = (TextView) popupView.findViewById(R.id.msg_infoRecipient);
        v3 = (TextView) popupView.findViewById(R.id.msg_infoTitle);
        v4 = (TextView) popupView.findViewById(R.id.msg_infoContent);

        mPopupWindow = new PopupWindow(popupView, 1000, 1000, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#13ADAD")));
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Message> list = messageList.getMsgList();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        msgAdapter = new MsgAdapter(getContext(), list);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(msgAdapter);
        recyclerView.addOnItemTouchListener(this);

        msgAdapter.setClickListener(this);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.msg_swipe);
        swipe.setOnRefreshListener(this);

        new MsgTask.getMsg(getContext(), msgAdapter, swipe).execute(Settings.USERNAME);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
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
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("OnTouch", "s");
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("onInterceptTouchEvent", "s");
        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e("onRequestDisallow", "s");

    }

    @Override
    public void onRefresh() {
        new MsgTask.getMsg(getContext(), msgAdapter, swipe).execute(Settings.USERNAME);
    }

    @Override
    public void itemClicked(View view, int position) {
        Message row = messageList.getMsgList().get(position);
        v1.setText(String.format("Sender: %s", row.getSender()));
        v2.setText(String.format("Recipient: %s", row.getReceipt()));
        v3.setText(String.format("Title: %s", row.getTitle()));
        v4.setText(String.format("Content: %s", row.getContent()));
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 300);
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach");
        super.onDetach();
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
}
