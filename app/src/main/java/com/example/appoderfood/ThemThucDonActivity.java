package com.example.appoderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.example.appoderfood.DAO.LoaiMonAnDAO;
import com.example.appoderfood.DAO.MonAnDAO;
import com.example.appoderfood.DTO.LoaiMonAnDTO;
import com.example.appoderfood.DTO.MonAnDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiThucDon;
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    String sDuongDanHinh;
    EditText edTenMonAn, edGiaTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon = findViewById(R.id.imThemLoaiThucDon);
        spinLoaiThucDon = findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = findViewById(R.id.imHinhAnhThucDon);
        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoaThemMonAn);
        edTenMonAn = findViewById(R.id.edThemTenMonAn);
        edGiaTien = findViewById(R.id.edThemGiaTien);

        HienThiSpinnerLoaiMonAn();

        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }

    private void HienThiSpinnerLoaiMonAn() {
        loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(ThemThucDonActivity.this, R.layout.custom_layout_spinloaithucdon, loaiMonAnDTOs);
        spinLoaiThucDon.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiMonAn = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiMonAn, REQUEST_CODE_THEMLOAITHUCDON);
                break;
            case R.id.imHinhAnhThucDon:
                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh, "Chọn hình thực đơn"), REQUEST_CODE_MOHINH);
                break;
            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiThucDon.getSelectedItemPosition();
                int maloai = loaiMonAnDTOs.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();

                if (tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")) {
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sDuongDanHinh);
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);
                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if (kiemtra) {
                        Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.loithemmonan), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnThoaThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEMLOAITHUCDON) {
            if (resultCode == Activity.RESULT_OK) {
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon", false);
                if (kiemtra) {
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (REQUEST_CODE_MOHINH == requestCode) {
            if (resultCode == Activity.RESULT_OK && data != null) {
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                    imHinhThucDon.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                sDuongDanHinh = data.getData().toString();
                imHinhThucDon.setImageURI(data.getData());
            }
        }
    }
}
