package com.qlma.qlma.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.qlma.qlma.R;
import com.qlma.qlma.base.BaseActivity;
import com.qlma.qlma.base.Const;
import com.qlma.qlma.ui.user.model.User;
import com.qlma.qlma.utils.Pref;

import java.util.List;

public class LoginActivity extends BaseActivity implements Const {

    public static void open(Context context, String name) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(KEY_NAME_USER, name);
        context.startActivity(intent);
    }

    private AutoCompleteTextView autoCompleteTextView;
    private TextInputEditText edtPass;
    private Button btnLogin;
    private Button btnRe;
    private User user;

    @Override
    protected int onLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        autoCompleteTextView = findViewById(R.id.tv_name);
        edtPass = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRe = findViewById(R.id.btnRegistration);
        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration.open(v.getContext());
            }
        });
        this.init();
    }

    private void init() {
        final List<User> listUser = Pref.get().getListUser();
        final String[] list = new String[listUser.size()];
        for (int i = 0; i < listUser.size(); i++) {
            Log.e("mai-ngo", listUser.get(i).getName() + "--" + i);
            list[i] = listUser.get(i).getName();
        }
        ArrayAdapter adapterUser
                = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        autoCompleteTextView.setAdapter(adapterUser);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = autoCompleteTextView.getText().toString();
                for (int i = 0; i < list.length; i++) {
                    boolean equals = s.equals(list[i]);
                    if (equals) {
                        edtPass.setText(listUser.get(i).getPass());
                        break;
                    }
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = autoCompleteTextView.getText().toString();
                String s1 = edtPass.getText().toString();
                boolean isLogin = false;
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(s1)) {
                    for (int i = 0; i < listUser.size(); i++) {
                        User user1 = listUser.get(i);
                        if (user1.getName().equals(s) && user1.getPass().equals(s1)) {
                            user = user1;
                            isLogin = true;
                            break;
                        }
                    }
                    if (isLogin) {
                        Toast.makeText(LoginActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Dang nhap thap bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
