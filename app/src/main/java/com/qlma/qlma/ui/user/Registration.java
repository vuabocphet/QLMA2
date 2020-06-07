package com.qlma.qlma.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.qlma.qlma.R;
import com.qlma.qlma.base.BaseActivity;
import com.qlma.qlma.ui.user.model.User;
import com.qlma.qlma.utils.Pref;

import java.util.List;

public class Registration extends BaseActivity {

    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, Registration.class);
        context.startActivity(intent);
    }

    private android.widget.EditText tvName;

    private TextInputEditText tvPass;

    private TextInputEditText tvConfigPass;

    private android.widget.Button btnRegistration;

    @Override
    protected int onLayout() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initViews() {
        this.initView();
    }

    private void initView() {
        tvName = (EditText) findViewById(R.id.tv_name);
        tvPass = (EditText) findViewById(R.id.tv_pass);
        tvConfigPass = (EditText) findViewById(R.id._tv_config_pass);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regis();
            }
        });
    }

    private void regis() {
        if (isEmptyAll() && checkSizePass(5) && checkName() && checkPass()) {
            String s = tvName.getText().toString();
            String s1 = tvPass.getText().toString();
            User user = User.init().setId(System.currentTimeMillis()).setName(s).setPass(s1);
            boolean b = Pref.get().putUser(user);
            if (b) {
                Toast.makeText(Registration.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                LoginActivity.open(this, s);
                finish();
                return;
            }
            Toast.makeText(Registration.this, "Đăng kí thất bại thử lại", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmptyAll() {
        String s = tvName.getText().toString();
        String s1 = tvPass.getText().toString();
        return !TextUtils.isEmpty(s) && !TextUtils.isEmpty(s1);
    }

    private boolean checkSizePass(int size) {
        if (!isEmptyAll()) {
            return false;
        }
        String s1 = tvPass.getText().toString();
        if ((s1.length() < size)) {
            //todo toast ...
            Toast.makeText(this, "Mật khẩu quá ngắn", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkPass() {
        String s1 = tvPass.getText().toString();
        String s2 = tvConfigPass.getText().toString();
        boolean equals = s1.equals(s2);
        if (!equals) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
        return equals;
    }

    private boolean checkName() {
        if (!isEmptyAll()) {
            return false;
        }
        String s = tvName.getText().toString();
        List<User> listUser = Pref.get().getListUser();
        if (listUser == null) {
            return true;
        }
        boolean ischeck = false;
        for (User user : listUser) {
            boolean equals = user.getName().equals(s);
            if (equals) {
                ischeck = equals;
                break;
            }
        }
        if (ischeck) {
            //todo toast lên là tên user đã tồn tại
            Toast.makeText(this, "Tên người dùng tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    //bmàn hình đăng kí ý, thì anh cho đăng kí như bìn thường ,, giống như đăng kí facebook đó...
    //// còn bên đăng nhập.. thì nếu có tài khoản rồi thì anh cứ đăng nhập là vào như thường thôi ý...à nhưng em có 1 cái này nữa
    // là nếu anh đăng kí tên anh là nguyentinh thhif lần sau anh đăng nhập lại chỉ cần ghi chứ ng là nó sẽ gợi ý tên cho anh  để anh chỉ cần ấn chọn ý.. k cần ghi tất cả
// ĐUược chưa em
//ngủ nha bé mai anh làm tiếp cho   abg jhaftcguwa em ngue qyue
}
