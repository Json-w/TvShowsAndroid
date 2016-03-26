package com.example.jason.helloworld.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.TvShowsUrl;
import com.example.jason.helloworld.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
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
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest loginRequest = new StringRequest(Request.Method.POST,
                        TvShowsUrl.LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            if (responseJson.getInt("statusCode") == 1) {
                                myApplication.setToken(responseJson.getJSONObject("data").getString("token"));
                                JSONObject userJson = responseJson.getJSONObject("data").getJSONObject("user");
                                User user = parseUserJson(userJson);
                                MyApplication.user = user;
                                saveUserAndTokenInfoAtLocale(user);
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
                requestQueue.add(loginRequest);
            }
        });
    }

    private void saveUserAndTokenInfoAtLocale(User user) {
        SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("token", myApplication.getToken());
        editor.putLong("loginTime", new Date().getTime());
        editor.commit();
    }

    @NonNull
    private User parseUserJson(JSONObject userJson) throws JSONException {
        User user = new User();
        user.setId(userJson.getInt("id"));
        user.setUsername(userJson.getString("username"));
        user.setEmail(userJson.getString("email"));
        user.setPortraitUrl(userJson.getString("portraitUrl"));
        return user;
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