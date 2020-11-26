package com.konumsal.orbisozetmobil.Hukuk_DisIlikiler_InsaatIkmal_UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.konumsal.orbisozetmobil.R;

import java.util.ArrayList;
import java.util.List;

import AdapterLayer.Ortak.MenuAdapter;
import ToolLayer.MessageBox;

public class DisIliskilerAltMenuActivity extends AppCompatActivity {

    Toolbar toolbar;
    ProgressDialog pd_main;
    GridView listview;
    List<String> menuList;
    MenuAdapter menuItemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ana_menu_activity);

        Init();
        initToolBar();

    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.ip_alt_menu_list_ui_toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            getSupportActionBar().setTitle("Dış İlişkiler");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisIliskilerAltMenuActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(DisIliskilerAltMenuActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(DisIliskilerAltMenuActivity.this, "Hata:" + e.toString(), false);
        }
    }


    private void Init() {


        listview= (GridView) findViewById(R.id.ip_alt_menu_list_listView);
        menuList = new ArrayList<String>();
        menuEkle();

        menuItemsAdapter = new MenuAdapter(DisIliskilerAltMenuActivity.this,R.layout.ana_menu_activity, menuList , 2);
        listview.setAdapter(menuItemsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    Intent mintent = new Intent(DisIliskilerAltMenuActivity.this, Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("EGITIM","0"); // yturtdisi
                    startActivity(mintent);

                }
                else if(position == 1)
                {
                    Intent mintent = new Intent(DisIliskilerAltMenuActivity.this, Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("EGITIM","1");   // egt katılımcı
                    startActivity(mintent);
                }


            }
        });
    }

    private void menuEkle() {
        menuList.add("Yurtdışı Protokol Listesi");
        menuList.add("Eğitim Katılımcı Listesi");


    }


}

