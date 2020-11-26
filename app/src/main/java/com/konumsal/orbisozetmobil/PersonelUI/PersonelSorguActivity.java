package com.konumsal.orbisozetmobil.PersonelUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import AdapterLayer.DisIliskiler.BirimAutoCompleteAdapter;
import AdapterLayer.Personel.PersonelSayiAdapter;
import DataLayer.Ortak.ConfigData;
import DataLayer.Sistem.SOrgBirim_Data;
import EntityLayer.DestekHizmetleri.PersonelGider;
import EntityLayer.Personel.PersonelSayi;
import EntityLayer.SendParametersForServer;
import EntityLayer.Sistem.SOrgBirim;
import ToolLayer.MessageBox;
import ToolLayer.NullOnEmptyConverterFactory;
import ToolLayer.OrbisDefaultException;
import ToolLayer.RefrofitRestApi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonelSorguActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner egitim_yil_spinner;
    Button sorgula_button, temizle_button;
    ProgressDialog pd_main;
    int selected_bolge_index = 0, selected_mudurluk_index = 0, selected_seflik_index = 0;
    Long secili_bolge_id = -1L, secili_mudurluk_id = -1L, secili_seflik_id = -1L, secili_yil = -1L;
    List<String> item_source_str_mudurluk;
    List<String> item_source_str_seflik;
    List<SOrgBirim> item_souce_mudurluk;
    List<SOrgBirim> item_source_seflik;
    PersonelSayiAdapter personelSayiAdapter;
    List<PersonelSayi> gelenPersonelSayiList;

    AutoCompleteTextView egitim_birim_auto;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.personel);


        Init();
        initToolBar();
        autocomplete_birim();

        egitim_yil_spinner.setSelection(0);
    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);

            getSupportActionBar().setTitle("İşçi ve Memur Listesi");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonelSorguActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(PersonelSorguActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(PersonelSorguActivity.this, "Hata:" + e.toString(), false);
        }
    }

    void Init() {

        egitim_yil_spinner = (Spinner) findViewById(R.id.egitim_yil_spinner);
        egitim_birim_auto = (AutoCompleteTextView) findViewById(R.id.birim_auto);


        ArrayList<String> years = new ArrayList<String>();
        years.add("");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        egitim_yil_spinner.setAdapter(adapter);

        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        pd2 = new ProgressDialog(PersonelSorguActivity.this);
        listview = (ListView) findViewById(R.id.oduh_listview);
        sorgula_button = (Button) findViewById(R.id.oduh_sorgula_button);
        sorgula_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBalOrmaniServis();

            }
        });

        temizle_button = (Button) findViewById(R.id.oduh_temizle_button);
        temizle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                egitim_yil_spinner.setSelection(0);
                secili_mudurluk_id = -1L;
                secili_bolge_id = -1L;
                secili_seflik_id = -1L;
                secili_yil = -1L;
                egitim_birim_auto.setText("0");
                secilenBirimId = -1L;

            }
        });


    }


    ProgressDialog pd2;

    void getBalOrmaniServis() {

        if (egitim_yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(egitim_yil_spinner.getSelectedItem().toString());

        ConfigData configData = new ConfigData(this);
        String url = configData.getSERVICURL() + "/";

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        RefrofitRestApi refrofitRestApi = retrofit.create(RefrofitRestApi.class);

        SendParametersForServer parameters = new SendParametersForServer();
        parameters.prmBolgeId = secili_bolge_id.toString();
        parameters.prmIsletmeId = secili_mudurluk_id.toString();
        parameters.prmSeflikId = secili_seflik_id.toString();
        parameters.prmYil = secili_yil.toString();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(PersonelSorguActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();


        Call<List<PersonelSayi>> call = refrofitRestApi.depoGetPersonelSayisiForMobil(parameters);
        call.enqueue(new Callback<List<PersonelSayi>>() {
            @Override
            public void onResponse(Call<List<PersonelSayi>> call, Response<List<PersonelSayi>> response) {
                if (!response.isSuccessful()) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(PersonelSorguActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                    return;
                }
                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    gelenPersonelSayiList = response.body();
                    if (gelenPersonelSayiList != null && gelenPersonelSayiList.size() > 0) {
                        get_listview();

                    } else
                        MessageBox.showAlert(PersonelSorguActivity.this, "Kayıt bulunamamıştır..", false);
                }
            }

            @Override
            public void onFailure(Call<List<PersonelSayi>> call, Throwable t) {
                progressDoalog.dismiss();
                MessageBox.showAlert(PersonelSorguActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
            }
        });


    }

    void get_listview() {

        personelSayiAdapter = new PersonelSayiAdapter(PersonelSorguActivity.this, R.layout.item_sekiz, gelenPersonelSayiList);
        listview.setAdapter(personelSayiAdapter);
        personelSayiAdapter.notifyDataSetChanged();
        listview.setClickable(true);


    }

    Long secilenBirimId = -1L;
    List<SOrgBirim> org_birim_list;

    void autocomplete_birim() {

        SOrgBirim_Data data = new SOrgBirim_Data(PersonelSorguActivity.this);
        org_birim_list = new ArrayList<SOrgBirim>();
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT * FROM S_ORG_BIRIM");
        try {
            org_birim_list = new ArrayList<SOrgBirim>();
            org_birim_list = data.loadFromQuery(sqlStr.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }


        BirimAutoCompleteAdapter adapter = new BirimAutoCompleteAdapter(this, R.layout.activity_main, R.layout.mr_simple_spinner_dropdown_item, org_birim_list);
        adapter.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        egitim_birim_auto.setThreshold(2);
        egitim_birim_auto.setAdapter(adapter);


        egitim_birim_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // final MuhStokLokasyon dty = (MuhStokLokasyon) parent.getItemAtPosition(position);
                SOrgBirim dty = (SOrgBirim) parent.getAdapter().getItem(position);

                if (dty != null) {
                    secilenBirimId = dty.getId();
                }
            }
        });

    }
}
