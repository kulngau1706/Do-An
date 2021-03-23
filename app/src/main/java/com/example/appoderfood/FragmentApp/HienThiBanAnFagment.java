package com.example.appoderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appoderfood.CustomAdapter.AdapterHienThiBanAn;
import com.example.appoderfood.DAO.BanAnDAO;
import com.example.appoderfood.DTO.BanAnDTO;
import com.example.appoderfood.R;
import com.example.appoderfood.ThemBanAnActivity;
import com.example.appoderfood.TrangChuActivity;

import java.util.List;

public class HienThiBanAnFagment extends Fragment {

    public static int RESQUEST_CODE_THEM = 111;
    GridView gvHienThiBanAn;
    List<BanAnDTO> banAnDTOList;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.banan);

        gvHienThiBanAn = view.findViewById(R.id.gvHienBanAn);
        banAnDAO = new BanAnDAO(getActivity());
       // banAnDTOList = banAnDAO.LayTatCaBanAn();

        HienThiDanhSachBanAn();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
        itThemBanAn.setIcon(R.drawable.thembanan);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn, RESQUEST_CODE_THEM);
                break;
        }
        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hienthibanan, banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESQUEST_CODE_THEM) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem", true);
                if (kiemtra) {
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
