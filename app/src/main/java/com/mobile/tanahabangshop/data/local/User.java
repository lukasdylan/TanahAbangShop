package com.mobile.tanahabangshop.data.local;

import android.support.annotation.NonNull;

import com.mobile.tanahabangshop.CoreDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Lukas Dylan Adisurya on 25/02/2018.
 * If you had any question about this project, you can contact me via e-mail
 * lukas.dylan.adisurya@gmail.com
 */

@Table(database = CoreDatabase.class)
public class User extends BaseModel {
    @PrimaryKey(autoincrement = true, quickCheckAutoIncrement = true)
    private long id;

    @Column
    private String userName;

    @Column
    private long userPhone;

    public User(){

    }

    public User(long id, String userName, long userPhone) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    @NonNull
    public static List<User> getUserList(){
        return SQLite.select().from(User.class).queryList();
    }

    @Override
    public String toString() {
        return "User : \nName: "+userName+"\nPhone Number: "+userPhone;
    }
}
