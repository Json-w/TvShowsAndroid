package com.example.jason.helloworld.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jason.helloworld.R;
import com.example.jason.helloworld.model.User;


public class ModifyUserBaseInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button backBtn, finishBtn;
    private TextView titleTV;
    private EditText modifyContentET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_base_info);
        initView();
        User user = (User) getIntent().getSerializableExtra("user");
        String title = getIntent().getStringExtra("title");
        titleTV.setText(title);
        modifyContentET.setText(user.getEmail());
    }

    private void initView() {
        backBtn = (Button) this.findViewById(R.id.modify_back_button);
        finishBtn = (Button) this.findViewById(R.id.modify_finish_button);
        titleTV = (TextView) this.findViewById(R.id.modify_title);
        modifyContentET = (EditText) this.findViewById(R.id.modify_content);
    }

    @Override
    public void onClick(View v) {
        
    }
}
