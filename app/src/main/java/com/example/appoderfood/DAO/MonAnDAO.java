package com.example.appoderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appoderfood.DTO.MonAnDTO;
import com.example.appoderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {

    SQLiteDatabase database;

    public  MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAnDTO.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN, null, contentValues);
        if(kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
