package edu.csu.JustMusic.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.csu.JustMusic.musicplayer.db.UserDao;
import edu.csu.JustMusic.musicplayer.model.BaseActivity;
import edu.csu.JustMusic.musicplayer.R;
import edu.csu.JustMusic.musicplayer.model.User;

public class RegisterActivity extends BaseActivity {
    private EditText reg_name;
    private EditText reg_password;
    private Button btn_reg;
    private Button btn_quit;
    private TextView login_pas;    private String passwordStr;//密码
    private TextView login_user;   private String userStr;//用户名

    private UserDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg = findViewById(R.id.button_reg2);//注册按钮实例化
        btn_quit = findViewById(R.id.button_quit);//注册按钮实例化
        initView();
    }

    public void initView(){
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = reg_name.getText().toString().trim();
                String pass = reg_password.getText().toString().trim();
                User user = new User(acc, pass);
                dao = new UserDao(getApplicationContext());
                dao.open();
                if (dao.find(user) == true) {
                    Toast.makeText(RegisterActivity.this, "账号已存在", Toast.LENGTH_SHORT).show();
                } else {
                    dao.addUser(user);
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    //将账号和密码传递过去
                    intent.putExtra("account", acc);
                    intent.putExtra("password", pass);
                    setResult(1, intent);
                    finish();
                }
                dao.close();
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
