package edu.csu.JustMusic.musicplayer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.csu.JustMusic.musicplayer.db.UserDao;
import edu.csu.JustMusic.musicplayer.model.BaseActivity;
import edu.csu.JustMusic.musicplayer.R;
import edu.csu.JustMusic.musicplayer.tool.SocketLogin;

public class LoginActivity extends BaseActivity {

    private static int login_status = 0;//登录状态，0是未登录，1是已登录
    private Button btn_login;//登录按钮
    private Button btn_register;//注册按钮
    private CheckBox remember_user, remember_password;//复选框
    private TextView login_pas;
    private String passwordStr;//密码
    private TextView login_user;
    private String userStr;//用户名
    private SharedPreferences.Editor sPreEditor_usr, sPreEditor_password;

    private EditText et_account;
    private EditText et_password;
    private UserDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.w("LoginActivity", "onCreate被执行了");//调试语句
//        Log.w("LoginActivity", "statusMusicPlayer是" + login_status);
        if (login_status == 0) {//未登录情况
            setContentView(R.layout.activity_login);
            btn_login = findViewById(R.id.button_login1);//登录按钮实例化
            btn_register = findViewById(R.id.button_reg1);//注册按钮实例化
            et_account = findViewById(R.id.log_edit_text_user);
            et_account = findViewById(R.id.log_pas);
            initButtonDeal();//处理登录按钮的事件
//            initButtonReg();//处理注册按钮的事件
//            loadDefaultMsg();//加载默认用户名和密码
        } else {//已经登陆过  这样做是为了登录后退出app再进入不需要再次登录
            jumpToNextActivity();
        }
    }
    //onStart      <-----
    //onResume          |
    //                 onRestart
    //onPause           |
    //onStop         ---
    //onDestroy

    public void jumpToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, DisplayActivity.class);
        startActivity(intent);
    }

    public void initButtonDeal() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = et_account.getText().toString().trim();
                String pass = et_password.getText().toString().trim();
                dao = new UserDao(getApplicationContext());
                dao.open();
                if (dao.isExist(acc) == false) {
                    Toast.makeText(LoginActivity.this,"账号不存在，请重新输入！", Toast.LENGTH_SHORT).show();
                } else {
                    if (dao.getPassword(acc).equals(pass)) {
                    jumpToNextActivity();
                        //销毁该活动
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
                dao.close();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            //判断结果码是否等于1，等于1则接受返回数据。
            if(requestCode == 1 && resultCode == 1){
                String name = data.getStringExtra("acc");
                String password = data.getStringExtra("pass");
                et_account.setText(name);
                et_password.setText(password);
            }
        }
    }
}

//    public void initButtonReg(){
//        btn_reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivityForResult(intent,1);
//            }
//        });
//    }
//    public void initButtonDeal() {
//        //处理确定按钮的事件
//        btn_reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                jumpToNextActivity();
//                login_pas = findViewById(R.id.reg_pas);//密码文本框对象
//                login_user = findViewById(R.id.reg_edit_text_user);//用户名文本框对象
//                userStr = login_user.getText().toString();//获得输入的用户名（文本）
//                passwordStr = login_pas.getText().toString();//获得输入的密码（文本）
//                if (!userStr.equals("") && !passwordStr.equals("")) {//如果都不为空
//                    /******保存用户名和密码*****/
//                    //检测是否想要记住用户名
//                    sPreEditor_usr = getSharedPreferences("username",
//                            MODE_PRIVATE).edit();
//                    if (remember_user.isChecked()) {
//                        sPreEditor_usr.putString("name",userStr);//存入数据
//                    } else {
//                        sPreEditor_usr.clear();//清除数据
//                    }
//                    sPreEditor_usr.apply();//生效
//
//                    //检测是否想要记住密码
//                    sPreEditor_password = getSharedPreferences("password",
//                            MODE_PRIVATE).edit();
//                    if (remember_password.isChecked()) {
//                        sPreEditor_password.putString("password",passwordStr);//保存
//                    } else {
//                        sPreEditor_password.clear();//清除
//                    }
//                    sPreEditor_password.apply();//生效
//
//                    /******使用socket与主机通信验证密码*****/
////                    Log.w("LoginActivity", "使用的信息是" + userStr+""+passwordStr);
//                    final SocketLogin s1 = new SocketLogin(userStr, passwordStr);
//                    s1.start();
//
//                    new Handler().postDelayed(new Runnable() {//延时1000ms后获取主机返回的验证状态
//                        @Override
//                        public void run() {
//                            int login_state = s1.getRespond_state_int();//获取主机验证后返回的信息
////                            Log.w("LoginActivity", "state is " + login_state);
//                            if(login_state == -1){
//                                Toast.makeText(LoginActivity.this, "服务器未响应或网络不好！", Toast.LENGTH_SHORT).show();
//                            }else if (login_state == 1) {//验证成功
//                                jumpToNextActivity();
//                                login_status = 1;
//                            } else {//验证失败
//                                Toast.makeText(LoginActivity.this, "账号验证失败，请重试！", Toast.LENGTH_SHORT).show();
//                            }
//                            s1.interrupt();
//                        }
//                    }, 1000);
//                } else {
//                    Toast.makeText(LoginActivity.this, "账号、密码不能为空！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void loadDefaultMsg() {//加载默认用户名和密码
//
//        remember_user = findViewById(R.id.remember_user);
//        login_user = findViewById(R.id.reg_edit_text_user);
//        //加载默认用户名
//        SharedPreferences pref = getSharedPreferences("username_data", MODE_PRIVATE);
//        String default_name = pref.getString("name", "");
//        if (!default_name.isEmpty()) {
//            login_user.setText(default_name);
//        }
//
//        remember_password = findViewById(R.id.remember_password);
//        login_pas = findViewById(R.id.reg_pas);
//        //加载默认密码
//        SharedPreferences pref_pas = getSharedPreferences("password_data", MODE_PRIVATE);
//        String default_password = pref_pas.getString("password", "");
//        if (!default_password.isEmpty()) {
//            login_pas.setText(default_password);
//        }
//    }
//
//    public static void setLogin_status(int login_status) {//后台服务MusicService中有用到
//        LoginActivity.login_status = login_status;
//    }
//
//    public static int getLogin_status() {
//        return login_status;
//    }
//}