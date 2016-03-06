package com.example.jason.helloworld.activities;

import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ETUsername, ETPassword, ETRepeatPassword, ETEmail;
    private Button backBtn, registerBtn;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        initView();
        backBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        ETUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    StringRequest checkUsernameIfRepea = new StringRequest(TvShowsUrl.USER_IF_REPEAT + ETUsername.getText(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (new JSONObject(response).getInt("statusCode") == 1) {
                                    Toast.makeText(RegisterActivity.this, "该用户名可用", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "该用户名不可用", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(checkUsernameIfRepea);
                }
            }
        });
        ETRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!ETPassword.getText().toString().equals(ETRepeatPassword.getText().toString())) {
                        Toast.makeText(RegisterActivity.this, "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        ETUsername = (EditText) this.findViewById(R.id.registerUsername);
        ETPassword = (EditText) this.findViewById(R.id.registerPassword);
        ETRepeatPassword = (EditText) this.findViewById(R.id.repeatRegisterPassword);
        ETEmail = (EditText) this.findViewById(R.id.email);
        backBtn = (Button) this.findViewById(R.id.register_back_button);
        registerBtn = (Button) this.findViewById(R.id.registerBtn);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back_button:
                finish();
                break;
            case R.id.registerBtn:
                if (StringUtil.isNull(ETUsername.getText().toString()) || StringUtil.isNull(ETPassword.getText().toString())
                        || StringUtil.isNull(ETRepeatPassword.getText().toString()) || StringUtil.isNull(ETEmail.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "选项不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                StringRequest registerRequest = new StringRequest(Request.Method.POST, TvShowsUrl.USER_REGISTER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (new JSONObject(response).getInt("statusCode") == 1) {
                                Toast.makeText(RegisterActivity.this, "注册成功,请登陆!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return getInput();
                    }
                };
                requestQueue.add(registerRequest);
                break;
            default:
                break;
        }
    }

    private Map<String, String> getInput() {
        Map<String, String> inputData = new HashMap<>();
        inputData.put("username", ETUsername.getText().toString());
        inputData.put("password", ETPassword.getText().toString());
        inputData.put("email", ETEmail.getText().toString());
        return inputData;
    }


}
