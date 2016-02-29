package com.example.jason.helloworld.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jason.helloworld.R;

import java.io.File;

public class SendActivity extends AppCompatActivity {
    private static final int SELECT_PIC_BY_PICK_PHOTO = 0;
    private static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    private Button sendBtn, backBtn;
    private ImageView sendImg;
    private EditText sendContent;
    private Uri photoUri;
    private String[] items = new String[]{"相册", "拍照"};
    private Bitmap headerPortait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        initView();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void initView() {
        sendBtn = (Button) this.findViewById(R.id.send_button);
        backBtn = (Button) this.findViewById(R.id.send_back_button);
        sendImg = (ImageView) this.findViewById(R.id.send_img);
        sendContent = (EditText) this.findViewById(R.id.send_content);
    }

    void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                Intent intent1 = new Intent(Intent.ACTION_PICK,
                                        null);
                                intent1.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent1, 11);
                                break;
                            case 1:
                                Intent intent2 = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE, null);
                                intent2.putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(new File(Environment

                                                .getExternalStorageDirectory(), "/"
                                                + getUserName() + ".jpg")));
                                startActivityForResult(intent2, 22);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 在sharedPreference中得到username作为头像图片的名字上传
     *
     * @return
     */
    public String getUserName() {
        /*SharedPreferences sp = this.getSharedPreferences("userInfo",
                this.MODE_PRIVATE);
        String username = sp.getString("username", null);*/

        return "test";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK) {
                }
                break;

            case 22:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/" + getUserName() + ".jpg");
                }
                break;
            case 33:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    headerPortait = extras.getParcelable("data");
                    if (headerPortait != null) {
                        sendImg.setImageBitmap(headerPortait);
                    }
                }
                break;
        }

    }


}
