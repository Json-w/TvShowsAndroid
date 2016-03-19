package com.example.jason.helloworld.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.model.User;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button registerBtn;
    private MyApplication myApplication;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        setContentView(R.layout.activity_login);
        initViews();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("username", "wangpei");
                editor.putString("password", "123456");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                MyApplication.user = new User(3, "wangpei", "123456", "", "519875872@qq.com");
                // TODO: save the user to MyApplication to make sure other activities can invoke.
                finish();
               /* StringRequest loginRequest = new StringRequest(Request.Method.POST,
                        TvShowsUrl.LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            if (responseJson.getInt("statusCode") == 1) {
                                myApplication.setToken(responseJson.getJSONObject("data").getString("token"));
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "登陆失败!", Toast.LENGTH_SHORT).show();
                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        return getInput();
                    }
                };
                requestQueue.add(loginRequest);*/
            }
        });
    }

    private Map<String, String> getInput() {
        Map<String, String> inputParams = new HashMap<>();
        inputParams.put("username", et_username.getText().toString());
        inputParams.put("password", et_password.getText().toString());
        return inputParams;
    }

    private void initViews() {
        et_username = (EditText) this.findViewById(R.id.registerUsername);
        et_password = (EditText) this.findViewById(R.id.registerPassword);
        btn_login = (Button) this.findViewById(R.id.loginBtn);
        registerBtn = (Button) this.findViewById(R.id.registerBtn);
    }
}