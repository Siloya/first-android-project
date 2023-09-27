package com.example.applogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "login.db";
    private static final int DB_VERSION = 2;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLDB) {
        sqLDB.execSQL("create Table users (username TEXT primary key, password TEXT)");
        sqLDB.execSQL("create Table contacts (contactname TEXT primary key, numbre TEXT,userbos TEXT, FOREIGN KEY(userbos) REFERENCES users(username))");

    }
    //cursor of contacts
    //public Cursor mycursor = SQLDB.rawQuery("select * from contacts ");

    @Override
    public void onUpgrade(SQLiteDatabase sqLDB, int oldV, int newV) {
        //sqLDB.execSQL("drop table if exists users ");
        //sqLDB.execSQL("drop table if exists contacts ");

        if (newV == 2) {
            sqLDB.execSQL("create Table contacts (contactname TEXT primary key, numbre TEXT,userbos TEXT, FOREIGN KEY(userbos) REFERENCES users(username))");
        }
    }

    public Boolean insertdata(String usernm, String pass) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cntv = new ContentValues();
        cntv.put("username", usernm);
        cntv.put("password", pass);
        long result = sqlDB.insert("users", null, cntv);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String usernm) {
        SQLiteDatabase SQLDB = this.getReadableDatabase();

        Cursor mcursor = SQLDB.rawQuery("select * from users where username =?", new String[]{usernm});
        if (mcursor.getCount() > 0) {
            return true;
        } else return false;
    }

    public Boolean checkpassname(String username, String pass) {
        SQLiteDatabase SQLDB = this.getReadableDatabase();

        //Cursor mcursor = SQLDB.rawQuery("select * from users where username= '" + username + "' and password = '" + pass + "'", null);
        Cursor mcursor = SQLDB.rawQuery("select * from users where username=? and password =?", new String[]{username, pass});
        if (mcursor.getCount() > 0) {
            return true;
        } else return false;
    }

    public Boolean insertcontact(String cname, String cnbr, String usernm) {
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        ContentValues cntv = new ContentValues();
        cntv.put("contactname", cname);
        cntv.put("numbre", cnbr);
        cntv.put("userbos", usernm);
        long result = sqlDB.insert("contacts", null, cntv);
        if (result == -1) return false;
        else
            return true;
        //kif bdi mr2 esm l user
    }
    /*public /*list* fetchmycontactinlist(String username){
       SQLiteDatabase SQLDB = this.getReadableDatabase();
      Cursor mycursor = SQLDB.rawQuery("select * from contacts where userbos= username");
        for (int i = 0; i < mycursor.getCount();i++){
        add list;
     *
        }return list    }
 */

    //or a cursor
    public Cursor fetchmycontact (String username){
        SQLiteDatabase SQLDB= this.getWritableDatabase();
        return SQLDB.rawQuery("select * from contacts where userbos= ?",new String[] {username});
    }

}
