package com.example.desktop.msg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.*;

import com.example.desktop.project.MainActivity;
import com.example.desktop.project.R;
import com.example.desktop.project.Settings;

import java.util.List;


public class MsgFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerView.OnItemTouchListener {
   static View root;
    RecyclerView recyclerView;
    static PopupWindow mPopupWindow;
    SwipeRefreshLayout swipe;
    MsgAdapter msgAdapter;
    MessageList messageList = new MessageList();

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

        View popupView = inflater.inflate(R.layout.msg_dialog, null);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffFEBB31")));
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

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return root;
    }
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
        Log.e("OnTouch","s");

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("onInterceptTouchEvent","s");
        mPopupWindow.showAsDropDown(getView());

        return false;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e("onRequestDisallow","s");

    }

    @Override
    public void onRefresh() {
        new MsgTask.getMsg(getContext(), msgAdapter, swipe).execute(Settings.USERNAME);
    }
}
