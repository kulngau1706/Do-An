package com.example.appoderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appoderfood.DAO.NhanVienDAO;
import com.example.appoderfood.FragmentApp.DatePickerFragment;
import com.example.appoderfood.DTO.NhanVienDTO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText edTenDangNhapDK, edMatKhauDK, edNgaySinhDK, edCMNDDK;
    Button bntDongYDK, btnThoatDK;
    RadioGroup grGioiTinh;
    String sGioiTinh;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        edTenDangNhapDK = findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK = findViewById(R.id.edMatKhauDK);
        edNgaySinhDK = findViewById(R.id.edNgaySinhDK);
        edCMNDDK = findViewById(R.id.edCMNDDK);
        bntDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        grGioiTinh = findViewById(R.id.rgGioiTinh);

        bntDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edNgaySinhDK.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDK:
                String sTenDangNhap = edTenDangNhapDK.getText().toString();
                String sMatKhau = edMatKhauDK.getText().toString();
                switch (grGioiTinh.getCheckedRadioButtonId()){
                    case R.id.rdNam:
                        sGioiTinh = "Nam";
                        break;
                    case R.id.rdNu:
                        sGioiTinh = "Nữ";
                        break;
                }
                String sNgaySinh = edNgaySinhDK.getText().toString();
                int sCMND = Integer.parseInt(edCMNDDK.getText().toString());
                if(sTenDangNhap == null || sTenDangNhap.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.nhaptendangnhapdangky, Toast.LENGTH_SHORT).show();
                }else if(sMatKhau == null || sMatKhau.equals("")){
                    Toast.makeText(getApplicationContext(), R.string.nhapmatkhaudangky, Toast.LENGTH_SHORT).show();
                }else{
                    NhanVienDTO nhanVienDTO = new NhanVienDTO();
                    nhanVienDTO.setTENDN(sTenDangNhap);
                    nhanVienDTO.setMATKHAU(sMatKhau);
                    nhanVienDTO.setCMND(sCMND);
                    nhanVienDTO.setNGAYSINH(sNgaySinh);
                    nhanVienDTO.setGIOITINH(sGioiTinh);

                    nhanVienDAO.ThemNhanVien(nhanVienDTO);
                    long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
                    if(kiemtra !=0){
                        Toast.makeText(getApplicationContext(), R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), R.string.themthatbai, Toast.LENGTH_SHORT).show();
                    }
                }
            break;
            case R.id.btnThoatDK:
                finish();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edNgaySinhDK:
                if(hasFocus){
                    // xuat popup ngay sinh
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(),"Ngày sinh");
                };
                break;
        }
    }
}