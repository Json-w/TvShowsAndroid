package com.example.jason.helloworld.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.StringUtil;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifyPwdActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backBtn, confirmBtn;
    private EditText ETOldPwd, ETNewPwd, ETRepeatNewPwd;
    private RequestQueue requestQueue;
    private User user;
    private ProgressDialog pd;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            switch (msg.what) {
                case 1:
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        requestQueue = Volley.newRequestQueue(this);
        user = (User) getIntent().getSerializableExtra("user");
        initView();
        backBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        ETRepeatNewPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!ETNewPwd.getText().toString().equals(ETRepeatNewPwd.getText().toString())) {
                        Toast.makeText(ModifyPwdActivity.this, "新密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        backBtn = (Button) this.findViewById(R.id.modify_pwd_back_btn);
        confirmBtn = (Button) this.findViewById(R.id.modify_pwd_confirm_button);
        ETOldPwd = (EditText) this.findViewById(R.id.modify_old_pwd);
        ETNewPwd = (EditText) this.findViewById(R.id.modify_new_pwd);
        ETRepeatNewPwd = (EditText) this.findViewById(R.id.modify_new_repeat_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_pwd_back_btn:
                finish();
                break;
            case R.id.modify_pwd_confirm_button:
                if (StringUtil.isNull(ETOldPwd.getText().toString()) || StringUtil.isNull(ETNewPwd.getText().toString())
                        || StringUtil.isNull(ETRepeatNewPwd.getText().toString())) {
                    Toast.makeText(ModifyPwdActivity.this, "选项不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                pd = ProgressDialog.show(ModifyPwdActivity.this, null, "正在更新，请稍候...");
                pd.show();
                updateUser(user);
                break;
        }
    }

    private void updateUser(final User user) {
        StringRequest updateUserRequest = new StringRequest(Request.Method.POST, TvShowsUrl.USER_MODIFY_PWD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject responseJson = null;
                try {
                    responseJson = new JSONObject(response);
                    if (responseJson.getInt("statusCode") == 0) {
                        Toast.makeText(ModifyPwdActivity.this
                                , "更新失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myHandler.sendEmptyMessage(1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModifyPwdActivity.this, "请检查网络!", Toast.LENGTH_SHORT).show();
                myHandler.sendEmptyMessage(0);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", user.getId() + "");
                map.put("password", ETOldPwd.getText().toString());
                map.put("newPwd", ETNewPwd.getText().toString());
                return map;
            }
        };
        requestQueue.add(updateUserRequest);
    }
}
