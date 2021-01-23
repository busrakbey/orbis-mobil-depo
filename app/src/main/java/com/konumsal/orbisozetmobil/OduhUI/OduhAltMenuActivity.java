package com.konumsal.orbisozetmobil.OduhUI;

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

public class OduhAltMenuActivity extends AppCompatActivity {

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
        setContentView(R.layout.ana_menu_alt_activity);

        Init();
        initToolBar();
    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.ip_alt_menu_list_ui_toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            getSupportActionBar().setTitle("Odundışı");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OduhAltMenuActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(OduhAltMenuActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(OduhAltMenuActivity.this, "Hata:" + e.toString(), false);
        }
    }


    private void Init() {


        listview= (GridView) findViewById(R.id.ip_alt_menu_list_listView);
        menuList = new ArrayList<String>();
        menuEkle();

        menuItemsAdapter = new MenuAdapter(OduhAltMenuActivity.this,R.layout.ana_menu_activity, menuList , 0);
        listview.setAdapter(menuItemsAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    Intent mintent = new Intent(OduhAltMenuActivity.this, OduhSorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE","0"); //bal ormani
                    startActivity(mintent);

                }
                else if(position == 1)
                {
                    Intent mintent = new Intent(OduhAltMenuActivity.this, OduhSorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE","1");   // mesire yeri
                    startActivity(mintent);
                }

                else if(position == 2)
                {
                    Intent mintent = new Intent(OduhAltMenuActivity.this, OduhSorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE","2");  // şehir ormanları
                    startActivity(mintent);
                }
                else if(position == 3)
                {
                    Intent mintent = new Intent(OduhAltMenuActivity.this, OduhSorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE","3");  // odun doışı orman ürünleri
                    startActivity(mintent);
                }
            }
        });
    }

    private void menuEkle() {
        menuList.add("Bal Ormanı Listesi");
        menuList.add("Mesire Yeri Listesi");
        menuList.add("Orman Ürünleri Listesi");

    }


}
