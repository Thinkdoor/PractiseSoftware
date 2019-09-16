package com.example.tablayoutmenu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    /*
    属性
     */
    private static final String TAG = "LoginActivity";
    private EditText editText,editText1;
    private Button button;
    private String path = "http://172.29.243.104:8088/sys/android/login";
    private OkHttpClient okHttpClient;
    private String username,password;
    //创建handler实现异步通信
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String flag = (String)msg.obj;
            //正常访问到服务器
            if(msg.what == 0){
                if(flag.endsWith("succ")){ //登录成功
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{//失败
                    Toast.makeText(LoginActivity.this,
                            "用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }//访问不了服务器
            else{
                Toast.makeText(LoginActivity.this,
                        "活动火爆,请稍候再试",Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
        初始化
         */
        editText = (EditText)findViewById(R.id.editText);
        editText1 = (EditText)findViewById(R.id.editText2);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        okHttpClient = new OkHttpClient();
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时
        okHttpClient= builder.connectTimeout(3000, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public void onClick(View v) {
        username = editText.getText().toString();
        password = editText1.getText().toString();
        login();
    }

    /**
     * 登录
     */
    public void login(){
        //创建FormBody对象
        FormBody.Builder builder = new FormBody.Builder();
        //把需要提交的数据存入
        builder.add("username",username).add("password",password);
        RequestBody body = builder.build();
        //构造request对象
        Request request = new Request.Builder().url(path).post(body).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               Message message = new Message();
               message.what = 1;
               handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, res);
                JSONObject jsonObject = null;
                try {
                    //json字符串转为json对象
                    jsonObject = new JSONObject(res);
                    String flag = jsonObject.getString("flag");
                    Message message = new Message();
                    message.obj = flag;
                    message.what = 0;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
