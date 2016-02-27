package com.example.jason.helloworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.MyApplication;
import com.example.jason.helloworld.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    private MyApplication myApplication;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        setContentView(R.layout.activity_login);
        initViews();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
        et_username = (EditText) this.findViewById(R.id.loginUsername);
        et_password = (EditText) this.findViewById(R.id.loginPassword);
        btn_login = (Button) this.findViewById(R.id.loginBtn);
    }
}