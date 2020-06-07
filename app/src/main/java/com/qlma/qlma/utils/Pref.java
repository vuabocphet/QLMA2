package com.qlma.qlma.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlma.qlma.base.Const;
import com.qlma.qlma.ui.user.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Pref implements Const {

    private static Pref pref;

    private SharedPreferences sharedPreferences;

    public static Pref get() {
        if (pref == null) {
            pref = new Pref();
        }
        return pref;
    }

    public void init(Context context) {
        if (this.sharedPreferences == null) {
            this.sharedPreferences = context.getSharedPreferences("QLMA", Context.MODE_PRIVATE);
        }
    }

    public boolean putUser(User user) {
        List<User> listUser = getListUser();
        if (listUser == null) {
            listUser = new ArrayList<>();
        } else if (!listUser.isEmpty()) {
            if (listUser.contains(user)) {
                return false;
            }
        }
        listUser.add(user);
        if (this.sharedPreferences == null) {
            return false;
        }
        this.sharedPreferences.edit().putString(KEY_LIST_USER, new Gson().toJson(listUser)).apply();
        return true;
    }

    public List<User> getListUser() {
        Type type = new TypeToken<List<User>>() {
        }.getType();
        String string = this.sharedPreferences.getString(KEY_LIST_USER, "");
        List<User> o = new Gson().fromJson(string, type);
        return TextUtils.isEmpty(string) ? new ArrayList<User>() : o;
    }

}
