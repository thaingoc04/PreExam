package com.example.dethuchi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseTaxi extends SQLiteOpenHelper {
    public static final String TableName = "DBThuChi";
    public static final String Id = "Id";
    public static final String TenKhoan = "TenKhoan";
    public static final String NgayThang = "NgayThang";
    public static final String SoTien = "SoTien";
    public static final String Logic = "Logic";


    public DatabaseTaxi(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer Primary key, "
                + TenKhoan + " Text, "
                + NgayThang + " Text, "
                + SoTien + " Integer, "
                + Logic + " Integer )";
        sqLiteDatabase.execSQL(sqlCreate);
    }

    //Cập nhật bảng version mới
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //xoa bang TableContact da co
        sqLiteDatabase.execSQL("Drop table if exists " + TableName);
        //Tao lai
        onCreate(sqLiteDatabase);
    }


    // Lay tat ca cac dong cua bang TableConTact tra ve dang Arraylist
    public ArrayList<ThuChi> getAllContact()
    {
        ArrayList<ThuChi> list = new ArrayList<>();
        //cau truy van
        String sql = "Select * from " + TableName;
        //lay doi tuong csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chay cau truy van tra ve dang cursor
        Cursor cursor = db.rawQuery(sql, null);
        //tao ArrayList<Contact> de tra ve
        if(cursor != null)
            while(cursor.moveToNext())
            {
                ThuChi contact = new ThuChi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
                        ,cursor.getInt(4));
                list.add(contact);
            }
        return list;
    }


    //ham them 1 contact vao bang TableContact
    public void addContact(ThuChi contact)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, contact.getId());
        values.put(TenKhoan, contact.getTenKhoan());
        values.put(NgayThang, contact.getNgayThang());
        values.put(SoTien, contact.getSoTien());
        values.put(Logic, contact.isLogic());
        db.insert(TableName, null,values);
        db.close();
    }

//    // Cập nhật lại Contact
//    public void updateContact(int id, ThuChi contact)
//    {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues value = new ContentValues();
//        value.put(TenKhoan,contact.getTenKhoan());
//        value.put(NgayThang,contact.getNgayThang());
//        value.put(DonGia,contact.getDonGia());
//        value.put(PhanTram,contact.getPhanTram());
//        db.update(TableName,value,"Id="+id,null);
//        db.close();
//    }

    //Xóa đi 1 Contact
    public void deleteContact(int id){
        SQLiteDatabase sqLite = getWritableDatabase();
        sqLite.delete(TableName,"Id="+id,null);
        sqLite.close();
    }

}