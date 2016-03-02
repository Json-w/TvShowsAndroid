package com.example.jason.helloworld.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jason.helloworld.R;
import com.example.jason.helloworld.common.DateUtil;
import com.example.jason.helloworld.common.NetUtil;
import com.example.jason.helloworld.common.TvShowsUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {
    private static final int SELECT_PIC_BY_PICK_PHOTO = 0;
    private static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    private Button sendBtn, backBtn;
    private ImageView sendImg;
    private EditText ETsendContent;
    private Uri photoUri;
    private String[] items = new String[]{"相册", "拍照"};
    private Bitmap headerPortait;
    /**
     * 获取到的图片路径
     */
    private String picPath = "";
    private Context mContext;
    private static ProgressDialog pd;
    private String imgUrl = TvShowsUrl.UPLOAD_PIC_URL;
    private String resultStr = "";    // 服务端返回结果集
    private RequestQueue requestQueue;
    private String serverImgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        requestQueue = Volley.newRequestQueue(this);
        mContext = SendActivity.this;
        initView();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sendActivityRequest = new StringRequest(Request.Method.POST, TvShowsUrl.SEND_ACTIVITY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            if (responseJson.getInt("statusCode") == 1) {
                                handler.sendEmptyMessage(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.sendEmptyMessage(2);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        return getInput();
                    }
                };
                requestQueue.add(sendActivityRequest);
                pd = ProgressDialog.show(mContext, null, "正在发布动态，请稍候...");
                pd.show();
            }
        });
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

    private Map<String, String> getInput() {
        Map<String, String> map = new HashMap<>();
        map.put("content", ETsendContent.getText().toString());
        map.put("releaseTime", DateUtil.dateToStr(new Date()));
        map.put("userId", "3");
        map.put("picUrl", serverImgUrl);
        return map;
    }

    private void initView() {
        sendBtn = (Button) this.findViewById(R.id.send_button);
        backBtn = (Button) this.findViewById(R.id.send_back_button);
        sendImg = (ImageView) this.findViewById(R.id.send_img);
        ETsendContent = (EditText) this.findViewById(R.id.send_content);
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
                                pickPhoto();
                                break;
                            case 1:
                                takePhoto();
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
        // 点击取消按钮
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        // 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
        switch (requestCode) {
            case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
                doPhoto(requestCode, data);
                break;
            case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
                doPhoto(requestCode, data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {

        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String[] pojo = {MediaStore.MediaColumns.DATA};
        // The method managedQuery() from the type Activity is deprecated
        //Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        Cursor cursor = mContext.getContentResolver().query(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);

            // 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
                cursor.close();
            }
        }

        // 如果图片符合要求将其上传到服务器
        if (picPath != null && (picPath.endsWith(".png") ||
                picPath.endsWith(".PNG") ||
                picPath.endsWith(".jpg") ||
                picPath.endsWith(".JPG"))) {


            BitmapFactory.Options option = new BitmapFactory.Options();
            // 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
            option.inSampleSize = 1;
            // 根据图片的SDCard路径读出Bitmap
            Bitmap bm = BitmapFactory.decodeFile(picPath, option);
            // 显示在图片控件上
            sendImg.setImageBitmap(bm);

            pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
            new Thread(uploadImageRunnable).start();
        } else {
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 使用HttpUrlConnection模拟post表单进行文件
     * 上传平时很少使用，比较麻烦
     * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
     */
    Runnable uploadImageRunnable = new Runnable() {
        @Override
        public void run() {

            if (TextUtils.isEmpty(imgUrl)) {
                Toast.makeText(mContext, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, String> textParams = new HashMap<String, String>();
            Map<String, File> fileparams = new HashMap<String, File>();

            try {
                // 创建一个URL对象
                URL url = new URL(imgUrl);
                textParams = new HashMap<String, String>();
                fileparams = new HashMap<String, File>();
                // 要上传的图片文件
                File file = new File(picPath);
                fileparams.put("file", file);
                // 利用HttpURLConnection对象从网络中获取网页数据
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
                conn.setConnectTimeout(10000);
                // 设置允许输出（发送POST请求必须设置允许输出）
                conn.setDoOutput(true);
                // 设置使用POST的方式发送
                conn.setRequestMethod("POST");
                // 设置不使用缓存（容易出现问题）
                conn.setUseCaches(false);
                // 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
                conn.setRequestProperty("ser-Agent", "Fiddler");
                // 设置contentType
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
                OutputStream os = conn.getOutputStream();
                DataOutputStream ds = new DataOutputStream(os);
                NetUtil.writeStringParams(textParams, ds);
                NetUtil.writeFileParams(fileparams, ds);
                NetUtil.paramsEnd(ds);
                // 对文件流操作完,要记得及时关闭
                os.close();
                // 服务器返回的响应吗
                int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
                // 对响应码进行判断
                if (code == 200) {// 返回的响应码200,是成功
                    // 得到网络返回的输入流
                    InputStream is = conn.getInputStream();
                    resultStr = NetUtil.readString(is);
                } else {
                    Toast.makeText(mContext, "请求URL失败！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
        }
    };

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    pd.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(resultStr);
                        // 服务端以字符串“1”作为操作成功标记
                        if (jsonObject.optString("statusCode").equals("1")) {

                            // 用于拼接发布说说时用到的图片路径
                            // 服务端返回的JsonObject对象中提取到图片的网络URL路径
                            String imageUrl = jsonObject.getJSONObject("data").getString("pictureUrl");
                            serverImgUrl = imageUrl;
                            // 获取缓存中的图片路径
                            Toast.makeText(mContext, imageUrl, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, jsonObject.optString("statusDesc"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    pd.dismiss();
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "发布失败", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                default:
                    break;
            }
            return false;
        }
    });

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        // 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

}
