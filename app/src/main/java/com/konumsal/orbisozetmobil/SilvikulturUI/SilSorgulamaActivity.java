package com.konumsal.orbisozetmobil.SilvikulturUI;

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

import AdapterLayer.Agaclandirma.AgacProjeAdapter;
import AdapterLayer.Agaclandirma.ToprakProjeAdapter;
import AdapterLayer.Silvikultur.BakimAdapter;
import AdapterLayer.Silvikultur.GenclestirmeAdapter;
import DataLayer.Ortak.ConfigData;
import EntityLayer.Agaclandirma.AgacProje;
import EntityLayer.Agaclandirma.ToprakProje;
import EntityLayer.SendParametersForServer;
import EntityLayer.SilviKultur.SilUygulama;
import EntityLayer.Sistem.SOrgBirim;
import ToolLayer.MessageBox;
import ToolLayer.NullOnEmptyConverterFactory;
import ToolLayer.RefrofitRestApi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SilSorgulamaActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner bolge_spinner, mudurluk_spinner, seflik_spinner, yil_spinner;
    Button sorgula_button, temizle_button;
    ProgressDialog pd_main;
    int selected_bolge_index = 0, selected_mudurluk_index = 0, selected_seflik_index = 0;
    Long secili_bolge_id = -1L, secili_mudurluk_id = -1L, secili_seflik_id = -1L, secili_yil = -1L;
    List<String> item_source_str_mudurluk;
    List<String> item_source_str_seflik;
    List<SOrgBirim> item_souce_mudurluk;
    List<SOrgBirim> item_source_seflik;
    GenclestirmeAdapter genclestirmeAdapter;
    BakimAdapter bakimAdapter;
    List<SilUygulama> gelenUygulamaList;
    ListView listview;
    String gelenSayfaId;
    LinearLayout baslikLinear1, baslikLinear2, birin_detay_linear, ikinci_detay_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sil_sorgulama_activity);

        Intent i = getIntent();
        gelenSayfaId = i.getStringExtra("MODE");

        Init();
        initToolBar();
        filtre_spinner();
        bolge_spinner.setSelection(0);
        mudurluk_spinner.setSelection(0);
        seflik_spinner.setSelection(0);
        yil_spinner.setSelection(0);
    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            if (gelenSayfaId.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Gençleştirme Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(View.GONE);
                birin_detay_linear.setVisibility(View.VISIBLE);
                ikinci_detay_linear.setVisibility(View.GONE);


            }
            if (gelenSayfaId.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Bakım Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                birin_detay_linear.setVisibility(View.GONE);
                ikinci_detay_linear.setVisibility(View.VISIBLE);

            }

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SilSorgulamaActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(SilSorgulamaActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(SilSorgulamaActivity.this, "Hata:" + e.toString(), false);
        }
    }

    void Init() {
        bolge_spinner = (Spinner) findViewById(R.id.bolge_spinner);
        mudurluk_spinner = (Spinner) findViewById(R.id.mudurluk_spinner);
        seflik_spinner = (Spinner) findViewById(R.id.seflik_spinner);
        yil_spinner = (Spinner) findViewById(R.id.yil_spinner);

        ArrayList<String> years = new ArrayList<String>();
        years.add("");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        yil_spinner.setAdapter(adapter);

        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        baslikLinear1 = (LinearLayout) findViewById(R.id.birinci_baslik);
        baslikLinear2 = (LinearLayout) findViewById(R.id.ikinci_baslik);
        birin_detay_linear = (LinearLayout) findViewById(R.id.birinci_baslik_detay);
        ikinci_detay_linear = (LinearLayout) findViewById(R.id.ikinci_baslik_detay);


        pd2 = new ProgressDialog(SilSorgulamaActivity.this);
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
                bolge_spinner.setSelection(0);
                mudurluk_spinner.setSelection(0);
                seflik_spinner.setSelection(0);
                yil_spinner.setSelection(0);
                secili_mudurluk_id = -1L;
                secili_bolge_id = -1L;
                secili_seflik_id = -1L;
                secili_yil = -1L;

            }
        });


    }


    ProgressDialog pd2;

    void getBalOrmaniServis() {

        if (yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());

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
        progressDoalog = new ProgressDialog(SilSorgulamaActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();

        Call<List<SilUygulama>> call = refrofitRestApi.getSilDepoAylikUygulamaSonucListForMobil(parameters);
        call.enqueue(new Callback<List<SilUygulama>>() {
            @Override
            public void onResponse(Call<List<SilUygulama>> call, Response<List<SilUygulama>> response) {
                if (!response.isSuccessful()) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(SilSorgulamaActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                    return;
                }
                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    gelenUygulamaList = response.body();
                    if (gelenUygulamaList != null && gelenUygulamaList.size() > 0) {
                        get_listview();

                    } else
                        MessageBox.showAlert(SilSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);
                }
            }

            @Override
            public void onFailure(Call<List<SilUygulama>> call, Throwable t) {
                progressDoalog.dismiss();
                MessageBox.showAlert(SilSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
            }
        });


    }


    void filtre_spinner() {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.bolge_list_string);
        dataAdapter_bolge.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        bolge_spinner.setAdapter(dataAdapter_bolge);
        bolge_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = OrtakFunction.bolge_list_string.get(position);
                    if (valInfo != null) {
                        selected_bolge_index = position;

                        Long bolge_mud_id = OrtakFunction.bolge_list.get(selected_bolge_index).getId();
                        secili_bolge_id = bolge_mud_id;
                        secili_mudurluk_id = -1L;
                        secili_seflik_id = -1L;

                        item_source_str_mudurluk.clear();
                        item_souce_mudurluk.clear();
                        item_source_str_seflik.clear();
                        item_source_seflik.clear();

                        item_souce_mudurluk.add(null);
                        item_source_str_mudurluk.add("");

                        for (SOrgBirim item : OrtakFunction.mudurluk_list) {
                            if (item == null)
                                continue;
                            if (String.valueOf(item.getUstId()).equals(String.valueOf(bolge_mud_id))) {
                                item_source_str_mudurluk.add(item.getAdi());
                                item_souce_mudurluk.add(item);
                            }
                        }


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
                        mudurluk_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
                        dataAdapter_seflik.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        seflik_spinner.setAdapter(dataAdapter_seflik);
                        seflik_spinner.setSelection(0);


                       /* if (OrtakFunction.s_org_birim_path.size() == 4) {
                            for (int i = 1; i < item_souce_mudurluk.size(); i++) {
                                if (String.valueOf(item_souce_mudurluk.get(i).getId()).equals(String.valueOf(OrtakFunction.s_org_birim_path.get(2)))) {
                                    mudurluk_spinner.setSelection(i);
                                    // mudurluk_spinner.setEnabled(false);
                                    break;
                                }
                            }
                        } else {
                            mudurluk_spinner.setSelection(0);
                        }*/


                    }
                } else {
                    secili_bolge_id = -1L;
                    secili_mudurluk_id = -1L;
                    secili_seflik_id = -1L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(bolge_spinner);
            popupWindow.setHeight(700);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }


      /*  for (Long item : OrtakFunction.s_org_birim_path)
            Log.v("birim", "=>" + item);*/

       /* if (OrtakFunction.s_org_birim_path.size() == 4) {
            for (int i = 1; i < OrtakFunction.bolge_list.size(); i++) {
                if (String.valueOf(OrtakFunction.bolge_list.get(i).getId()).equals(String.valueOf(OrtakFunction.s_org_birim_path.get(1)))) {
                    bolge_spinner.setSelection(i);
                    //bolge_spinner.setEnabled(false);
                    break;
                }
            }
        } else {
            bolge_spinner.setSelection(0);
        }*/


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);

        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
        mudurluk_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = item_source_str_mudurluk.get(position);
                    if (valInfo != null) {
                        selected_mudurluk_index = position;

                        Long mud_id = item_souce_mudurluk.get(selected_mudurluk_index).getId();
                        secili_mudurluk_id = mud_id;
                        secili_seflik_id = -1L;

                        item_source_str_seflik.clear();
                        item_source_seflik.clear();

                        item_source_seflik.add(null);
                        item_source_str_seflik.add("");


                        for (SOrgBirim item : OrtakFunction.seflik_list) {
                            if (item == null)
                                continue;
                            if (String.valueOf(item.getUstId()).equals(String.valueOf(mud_id))) {
                                item_source_str_seflik.add(item.getAdi());
                                item_source_seflik.add(item);
                            }
                        }


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
                        dataAdapter_seflik.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        seflik_spinner.setAdapter(dataAdapter_seflik);
                        seflik_spinner.setSelection(0);


                      /*  if (OrtakFunction.s_org_birim_path.size() == 4) {
                            for (int i = 1; i < item_source_seflik.size(); i++) {
                                if (String.valueOf(item_source_seflik.get(i).getId()).equals(String.valueOf(OrtakFunction.s_org_birim_path.get(3)))) {
                                    seflik_spinner.setSelection(i);
                                    //seflik_spinner.setEnabled(false);
                                    break;
                                }
                            }
                        } else {
                            seflik_spinner.setSelection(0);
                        }*/


                    } else {
                        secili_mudurluk_id = -1L;
                        secili_seflik_id = -1L;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(SilSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
        dataAdapter_seflik.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);

        seflik_spinner.setAdapter(dataAdapter_seflik);
        seflik_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = item_source_str_seflik.get(position);
                    if (valInfo != null) {
                        selected_seflik_index = position;
                        secili_seflik_id = item_source_seflik.get(selected_seflik_index).getId();
                    }
                } else {
                    secili_seflik_id = -1L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    void get_listview() {
        if (gelenSayfaId.equalsIgnoreCase("0")) {
            genclestirmeAdapter = new GenclestirmeAdapter(SilSorgulamaActivity.this, R.layout.item_on_uc, gelenUygulamaList);
            listview.setAdapter(genclestirmeAdapter);
            genclestirmeAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            bakimAdapter = new BakimAdapter(SilSorgulamaActivity.this, R.layout.item_on_bir, gelenUygulamaList);
            listview.setAdapter(bakimAdapter);
            bakimAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }

    }


}





