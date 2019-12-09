package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.community.adapter.SearchTypeAdapter;
import com.community.model.SearchInfo;

import java.util.List;

/**
 * 搜索页
 * Created by mayn on 2018/9/18.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_search;
    private EditText et_search;
    private MyGridView gv_type;
    private List<SearchInfo> searchInfo;
    private SearchTypeAdapter searchTypeAdapter;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case search_classify_id://搜索类型
                    if (msg.obj != null) {
                        searchInfo = (List<SearchInfo>) msg.obj;
                        if (searchInfo!=null&&searchInfo.size()!=0){
                            searchInfo.get(0).ispick=true;
                        }
                        searchTypeAdapter = new SearchTypeAdapter(SearchActivity.this, searchInfo);
                        gv_type.setAdapter(searchTypeAdapter);
                    }
                    break;
                case search_classify_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        et_search = (EditText) findViewById(R.id.et_search);
        gv_type = (MyGridView) findViewById(R.id.gv_type);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        et_search.setOnEditorActionListener(action);
        initData();
    }

    @Override
    protected void initData() {
        netRun.SearchClassify();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.iv_search){
            //搜索
            String s = et_search.getText().toString();
            SearchList(s);
        }

//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.iv_search://搜索
//                String s = et_search.getText().toString();
//                SearchList(s);
//                break;
//        }
    }

    private TextView.OnEditorActionListener action = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("--------------------", "搜索  " + textView.getText().toString());
                    SearchList(textView.getText().toString());
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 搜索
     *
     * @param keyword
     */
    private void SearchList(String keyword) {
        if (TextUtils.isEmpty(keyword)) keyword = "";
        if (searchTypeAdapter.getpick().equals(getString(R.string.find_reminder52))){
            Intent intent = new Intent(SearchActivity.this, ForumListActivity.class);
            intent.putExtra("keyword",keyword);
            startActivity(intent);
        }else if (searchTypeAdapter.getpick().equals(getString(R.string.find_reminder89))){
            Intent intent = new Intent(SearchActivity.this, PostGroupActivity.class);
            intent.putExtra("keyword",keyword);
            startActivity(intent);
        }else if (searchTypeAdapter.getpick().equals(getString(R.string.find_reminder111))){
            Intent intent = new Intent(SearchActivity.this, CircleoffriendsActivityu.class);
            intent.putExtra("keyword",keyword);
            startActivity(intent);
        }else if (searchTypeAdapter.getpick().equals(getString(R.string.find_reminder69))){
            Intent intent = new Intent(SearchActivity.this, GettogetherListActivity.class);
            intent.putExtra("keyword",keyword);
            startActivity(intent);
        }
    }

    @Override
    public void ReGetData() {

    }
}
