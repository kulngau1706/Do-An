package com.example.appoderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appoderfood.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYDN, btnDangKyDN;
    EditText edTenDangNhapDN, edMatKhauDN;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        btnDongYDN = findViewById(R.id.btnDongYDN);
        btnDangKyDN = findViewById(R.id.btnDangKyDN);
        edTenDangNhapDN = findViewById(R.id.edTenDangNhapDN);
        edMatKhauDN = findViewById(R.id.edMatKhauDN);

        nhanVienDAO = new NhanVienDAO(this);
        btnDongYDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);

        HienThiButtonDangKyVaDongY();

    }
    private void HienThiButtonDangKyVaDongY(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if(kiemtra){
            btnDangKyDN.setVisibility(View.VISIBLE);
            btnDongYDN.setVisibility(View.VISIBLE);
        }else{
            btnDangKyDN.setVisibility(View.VISIBLE);
            btnDongYDN.setVisibility(View.VISIBLE);
        }
    }

    private void btnDongY(){
        String sTenDangNhap = edTenDangNhapDN.getText().toString();
        String sMatKhau = edMatKhauDN.getText().toString();
        boolean kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        if(kiemtra){
            Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            iTrangChu.putExtra("tendn",edTenDangNhapDN.getText().toString());
            startActivity(iTrangChu);
            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnDangKy(){
        Intent iDangKy = new Intent(DangNhapActivity.this, MainActivity.class);
        startActivity(iDangKy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDangKyVaDongY();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDN:
                btnDongY();
                break;
            case R.id.btnDangKyDN:
                btnDangKy();
                break;
        }
    }
}
