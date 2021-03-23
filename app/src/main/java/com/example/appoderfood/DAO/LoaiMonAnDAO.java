package com.example.appoderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appoderfood.DTO.LoaiMonAnDTO;
import com.example.appoderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {

    SQLiteDatabase database;

    public LoaiMonAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMonAn(String tenloai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);
        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if (kiemtra != 0) {
            return true;

        } else {
            return false;
        }
    }

    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn() {
        List<LoaiMonAnDTO> loaiMonAnDTOs = new ArrayList<LoaiMonAnDTO>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOs.add(loaiMonAnDTO);

            cursor.moveToNext();
        }
        return loaiMonAnDTOs;
    }
    
}
