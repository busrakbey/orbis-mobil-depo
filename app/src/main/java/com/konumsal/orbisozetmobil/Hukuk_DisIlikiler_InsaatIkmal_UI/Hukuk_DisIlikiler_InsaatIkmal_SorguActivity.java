package com.konumsal.orbisozetmobil.Hukuk_DisIlikiler_InsaatIkmal_UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.tool.util.L;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.esri.core.geometry.Line;
import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import AdapterLayer.DisIliskiler.BirimAutoCompleteAdapter;
import AdapterLayer.DisIliskiler.EgitimKatilimciAdapter;
import AdapterLayer.DisIliskiler.YurtdisiProtokolAdapter;
import AdapterLayer.Hukuk.DavaAdapter;
import AdapterLayer.InsaatIkmal.InikYolAdapter;
import DataLayer.Ortak.ConfigData;
import DataLayer.Sistem.SOrgBirim_Data;
import EntityLayer.DisIliskiler.EgitimKatilimci;
import EntityLayer.DisIliskiler.YurtdisiProtokol;
import EntityLayer.Hukuk.Dava;
import EntityLayer.InsaatIkmal.InikYol;
import EntityLayer.SendParametersForServer;
import EntityLayer.Sistem.SOrgBirim;
import ToolLayer.MessageBox;
import ToolLayer.NullOnEmptyConverterFactory;
import ToolLayer.OrbisDefaultException;
import ToolLayer.RefrofitRestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Hukuk_DisIlikiler_InsaatIkmal_SorguActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner bolge_spinner, mudurluk_spinner, seflik_spinner, yil_spinner, egitim_yil_spinner;
    Button sorgula_button, temizle_button;
    ProgressDialog pd_main;
    int selected_bolge_index = 0, selected_mudurluk_index = 0, selected_seflik_index = 0;
    Long secili_bolge_id = -1L, secili_mudurluk_id = -1L, secili_seflik_id = -1L, secili_yil = -1L;
    List<String> item_source_str_mudurluk;
    List<String> item_source_str_seflik;
    List<SOrgBirim> item_souce_mudurluk;
    List<SOrgBirim> item_source_seflik;
    DavaAdapter davaAdapter;
    YurtdisiProtokolAdapter yurtdisiProtokolAdapter;
    InikYolAdapter ınikYolAdapter;
    EgitimKatilimciAdapter egitimKatilimciAdapter;
    List<Dava> gelenDavaList;
    List<YurtdisiProtokol> gelenYurtDisiProtokolList;
    List<InikYol> gelenYolList;
    List<EgitimKatilimci> gelenEgitimKatilimciList;
    AutoCompleteTextView egitim_birim_auto;
    String gelenSayfaId = " ", gelenEgitimPage = " ";

    ListView listview;
    LinearLayout baslikLinear1, baslikLinear2, baslikLinear3, sorgu_egitim_linear, genel_sorgu_linear, yil_sorgu_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.hukuk_disiliskiler_insaatikmal);

        Intent i = getIntent();
        if (i.getStringExtra("MODE") != null)
            gelenSayfaId = i.getStringExtra("MODE");
        if (i.getStringExtra("EGITIM") != null)
            gelenEgitimPage = i.getStringExtra("EGITIM");

        Init();
        initToolBar();
        filtre_spinner();
        autocomplete_birim();

        bolge_spinner.setSelection(0);
        mudurluk_spinner.setSelection(0);
        seflik_spinner.setSelection(0);
        yil_spinner.setSelection(0);
        egitim_yil_spinner.setSelection(0);
    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            if (gelenSayfaId.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Dava Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.GONE);
                sorgu_egitim_linear.setVisibility(View.GONE);
                genel_sorgu_linear.setVisibility(View.VISIBLE);
                yil_sorgu_linear.setVisibility(View.GONE);

            }
            if (gelenEgitimPage.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Yurtdışı Protokol Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(View.GONE);
                sorgu_egitim_linear.setVisibility(View.VISIBLE);
                genel_sorgu_linear.setVisibility(View.GONE);
            }

            if (gelenSayfaId.equalsIgnoreCase("2")) {
                getSupportActionBar().setTitle("Orman Yol Çalışmaları");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.VISIBLE);
                sorgu_egitim_linear.setVisibility(View.GONE);
                genel_sorgu_linear.setVisibility(View.VISIBLE);

            }


            if (gelenEgitimPage.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Eğitim Katılımcı Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(View.GONE);
                sorgu_egitim_linear.setVisibility(View.VISIBLE);
                genel_sorgu_linear.setVisibility(View.GONE);


            }

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata:" + e.toString(), false);
        }
    }

    void Init() {
        bolge_spinner = (Spinner) findViewById(R.id.bolge_spinner);
        mudurluk_spinner = (Spinner) findViewById(R.id.mudurluk_spinner);
        seflik_spinner = (Spinner) findViewById(R.id.seflik_spinner);
        yil_spinner = (Spinner) findViewById(R.id.yil_spinner);
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
        yil_spinner.setAdapter(adapter);
        egitim_yil_spinner.setAdapter(adapter);

        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        baslikLinear1 = (LinearLayout) findViewById(R.id.birinci_baslik);
        baslikLinear2 = (LinearLayout) findViewById(R.id.ikinci_baslik);
        baslikLinear3 = (LinearLayout) findViewById(R.id.ucuncu_baslik);
        sorgu_egitim_linear = (LinearLayout) findViewById(R.id.egitim);
        genel_sorgu_linear = (LinearLayout) findViewById(R.id.genel_sorgu);
        yil_sorgu_linear= (LinearLayout) findViewById(R.id.yil_sorgu);

        pd2 = new ProgressDialog(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this);
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

        if (yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());


        if (egitim_yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());

        ConfigData configData = new ConfigData(this);
        String url = configData.getSERVICURL() + "/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RefrofitRestApi refrofitRestApi = retrofit.create(RefrofitRestApi.class);

        SendParametersForServer parameters = new SendParametersForServer();
        parameters.prmBolgeId = secili_bolge_id.toString();
        parameters.prmIsletmeId = secili_mudurluk_id.toString();
        parameters.prmSeflikId = secili_seflik_id.toString();
        parameters.prmYil = secili_yil.toString();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();

        if (gelenSayfaId.equalsIgnoreCase("0")) {
            Call<List<Dava>> call = refrofitRestApi.getDavaBilgileriListForMobil(parameters);
            call.enqueue(new Callback<List<Dava>>() {
                @Override
                public void onResponse(Call<List<Dava>> call, Response<List<Dava>> response) {
                    if (!response.isSuccessful()) {
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenDavaList = response.body();
                        if (gelenDavaList != null && gelenDavaList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Kayıt bulunamamıştır..", false);
                    }
                }

                @Override
                public void onFailure(Call<List<Dava>> call, Throwable t) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenEgitimPage.equalsIgnoreCase("0")) {

            SendParametersForServer parameters_egitim = new SendParametersForServer();
            if (secilenBirimId != null)
                parameters_egitim.prmBirimId = secilenBirimId.toString();
            else
                parameters_egitim.prmBirimId = "-1";
            parameters_egitim.prmYil = secili_yil.toString();
            Call<List<YurtdisiProtokol>> call = refrofitRestApi.getYurtDisiListForMobil(parameters_egitim);
            call.enqueue(new Callback<List<YurtdisiProtokol>>() {
                @Override
                public void onResponse(Call<List<YurtdisiProtokol>> call, Response<List<YurtdisiProtokol>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenYurtDisiProtokolList = response.body();
                        if (gelenYurtDisiProtokolList != null && gelenYurtDisiProtokolList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<YurtdisiProtokol>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {

            Call<List<InikYol>> call = refrofitRestApi.getInikYolCalismalariListForMobil(parameters);
            call.enqueue(new Callback<List<InikYol>>() {
                @Override
                public void onResponse(Call<List<InikYol>> call, Response<List<InikYol>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenYolList = response.body();
                        if (gelenYolList != null && gelenYolList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<InikYol>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });

        }
        if (gelenEgitimPage.equalsIgnoreCase("1")) {

            SendParametersForServer parameters_egitim = new SendParametersForServer();
            if (secilenBirimId != null)
                parameters_egitim.prmBirimId = secilenBirimId.toString();
            else
                parameters_egitim.prmBirimId = "-1";
            parameters_egitim.prmYil = secili_yil.toString();
            Call<List<EgitimKatilimci>> call = refrofitRestApi.getGgitimKatilimciPersonelForMobil(parameters_egitim);
            call.enqueue(new Callback<List<EgitimKatilimci>>() {
                @Override
                public void onResponse(Call<List<EgitimKatilimci>> call, Response<List<EgitimKatilimci>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                         gelenEgitimKatilimciList = response.body();
                        if (gelenEgitimKatilimciList != null && gelenEgitimKatilimciList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<EgitimKatilimci>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });

        }
    }


    void filtre_spinner() {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.bolge_list_string);
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


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
                        mudurluk_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
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


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
            davaAdapter = new DavaAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_yedi, gelenDavaList);
            listview.setAdapter(davaAdapter);
            davaAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenEgitimPage.equalsIgnoreCase("0")) {
            yurtdisiProtokolAdapter = new YurtdisiProtokolAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_dokuz, gelenYurtDisiProtokolList);
            listview.setAdapter(yurtdisiProtokolAdapter);
            yurtdisiProtokolAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {
            ınikYolAdapter = new InikYolAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_on_bir, gelenYolList);
            listview.setAdapter(ınikYolAdapter);
            ınikYolAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenEgitimPage.equalsIgnoreCase("1")) {
            egitimKatilimciAdapter = new EgitimKatilimciAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_uc, gelenEgitimKatilimciList);
            listview.setAdapter(egitimKatilimciAdapter);
            egitimKatilimciAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }

    }

    Long secilenBirimId = -1L;
    List<SOrgBirim> org_birim_list;

    void autocomplete_birim() {

        SOrgBirim_Data data = new SOrgBirim_Data(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this);
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