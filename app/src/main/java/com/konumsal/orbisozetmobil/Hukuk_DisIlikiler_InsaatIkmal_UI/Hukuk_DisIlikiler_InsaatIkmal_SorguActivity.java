package com.konumsal.orbisozetmobil.Hukuk_DisIlikiler_InsaatIkmal_UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.tool.util.L;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import EntityLayer.Ortak.SCity;
import EntityLayer.Ortak.SKoyBelde;
import EntityLayer.Ortak.STown;
import EntityLayer.SendParametersForServer;
import EntityLayer.Sistem.SOrgBirim;
import EnumsLayer.LocalDataManager;
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

import static ToolLayer.RetrofirCertifica.getUnsafeOkHttpClient;
import static android.view.View.GONE;

public class Hukuk_DisIlikiler_InsaatIkmal_SorguActivity extends AppCompatActivity implements ExpandableLayout.OnExpansionUpdateListener  {
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
    LinearLayout baslikLinear1, baslikLinear2, baslikLinear3, baslikLinear4, sorgu_egitim_linear, yil_sorgu_linear,seflik_linear, ilce_linear, koy_linear;
    ConstraintLayout genel_sorgu_linear;

    Spinner il_spinner, ilce_spinner, koy_spinner;
    List<SCity> il_list;
    List<STown> ilce_list;
    List<SKoyBelde> koy_list;
    List<String> item_source_str_ilce;
    List<String> item_source_str_koy;
    int selected_il_index = 0, selected_ilce_index = 0, selected_koy_index = 0;
    int shared_il_index = 0, shared_ilce_index = 0, shared_koy_index = 0;
    Long secili_il_id = -1L, secili_ilce_id = -1L, secili_koy_id = -1L;
    int shared_bolge_index = 0, shared_mudurluk_index = 0, shared_seflik_index = 0;
    RadioButton genelMudRadioButton, bolgeRadioButton, teskilatRadioButton;
    RadioGroup birimRadioGrup;
    LocalDataManager localDataManager;
    private ExpandableLayout expandableLayout;
    private ImageView expandButton;
    LinearLayout linearLayout_bir, linearLayout_iki;


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
        shared_values();
        autocomplete_birim();

 
    }



    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            if (gelenSayfaId.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Dava Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(GONE);
                sorgu_egitim_linear.setVisibility(GONE);
                genel_sorgu_linear.setVisibility(View.VISIBLE);
                yil_sorgu_linear.setVisibility(GONE);

            }
            if (gelenEgitimPage.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Yurtdışı Protokol Listesi");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(GONE);
                sorgu_egitim_linear.setVisibility(GONE);
              //  genel_sorgu_linear.setVisibility(View.GONE);
            }

            if (gelenSayfaId.equalsIgnoreCase("2")) {
                getSupportActionBar().setTitle("Orman Yol Çalışmaları");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(View.VISIBLE);
                baslikLinear4.setVisibility(GONE);
                sorgu_egitim_linear.setVisibility(GONE);
                koy_linear.setVisibility(View.VISIBLE);
               // genel_sorgu_linear.setVisibility(View.VISIBLE);

            }


            if (gelenEgitimPage.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Eğitim Katılımcı Listesi");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(View.VISIBLE);
                sorgu_egitim_linear.setVisibility(GONE);
              //  genel_sorgu_linear.setVisibility(View.GONE);


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

    @SuppressLint("WrongViewCast")
    void Init() {
        bolge_spinner = (Spinner) findViewById(R.id.bolge_spinner);
        mudurluk_spinner = (Spinner) findViewById(R.id.mudurluk_spinner);
        seflik_spinner = (Spinner) findViewById(R.id.seflik_spinner);
        yil_spinner = (Spinner) findViewById(R.id.yil_spinner);
        egitim_yil_spinner = (Spinner) findViewById(R.id.egitim_yil_spinner);
        egitim_birim_auto = (AutoCompleteTextView) findViewById(R.id.birim_auto);
        

        expandableLayout = (ExpandableLayout) findViewById(R.id.expandable_layout);
        expandButton = (ImageView) findViewById(R.id.expand_button);
        expandableLayout.setOnExpansionUpdateListener(this);
        linearLayout_bir = (LinearLayout) findViewById(R.id.linear_bir);
        linearLayout_iki = (LinearLayout) findViewById(R.id.linear_iki);
        il_spinner = (Spinner) findViewById(R.id.il_spinner);
        ilce_spinner = (Spinner) findViewById(R.id.ilce_spinner);
        koy_spinner = (Spinner) findViewById(R.id.koy_spinner);
        bolge_spinner = (Spinner) findViewById(R.id.bolge_spinner);
        mudurluk_spinner = (Spinner) findViewById(R.id.mudurluk_spinner);
        seflik_spinner = (Spinner) findViewById(R.id.seflik_spinner);
        yil_spinner = (Spinner) findViewById(R.id.yil_spinner);
        genelMudRadioButton = (RadioButton) findViewById(R.id.radio_gen_mud);
        bolgeRadioButton = (RadioButton) findViewById(R.id.radio_bolge);
        teskilatRadioButton = (RadioButton) findViewById(R.id.radio_teskilat);
        birimRadioGrup = (RadioGroup) findViewById(R.id.birim_radio_group);
        ArrayList<String> years = new ArrayList<String>();
        years.add("");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        yil_spinner.setAdapter(adapter);
        yil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                    secili_yil = -1L;
                else
                    secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());

                localDataManager.setSharedPreference(getApplicationContext(), "yil", String.valueOf(yil_spinner.getSelectedItemId()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        item_source_str_ilce = new ArrayList<String>();
        item_source_str_koy = new ArrayList<String>();
        ilce_list = new ArrayList<STown>();
        koy_list = new ArrayList<SKoyBelde>();
        il_list = new ArrayList<SCity>();


        birimRadioGrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioGrupChange(group, checkedId);
            }
        });

        localDataManager = new LocalDataManager();


        baslikLinear1 = (LinearLayout) findViewById(R.id.birinci_baslik);
        baslikLinear2 = (LinearLayout) findViewById(R.id.ikinci_baslik);
        baslikLinear3 = (LinearLayout) findViewById(R.id.ucuncu_baslik);
        baslikLinear4 = (LinearLayout) findViewById(R.id.dorduncu_baslik);
        sorgu_egitim_linear = (LinearLayout) findViewById(R.id.egitim);
        genel_sorgu_linear = (ConstraintLayout) findViewById(R.id.genel_sorgu);
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

                bolge_spinner.setSelection(0);
                mudurluk_spinner.setSelection(0);
                seflik_spinner.setSelection(0);
                yil_spinner.setSelection(0);
                secili_mudurluk_id = -1L;
                secili_bolge_id = -1L;
                secili_seflik_id = -1L;
                secili_yil = -1L;
                il_spinner.setSelection(0);
                ilce_spinner.setSelection(0);
                koy_spinner.setSelection(0);
                secili_il_id=-1L;
                secili_ilce_id = -1L;
                secili_koy_id = -1L;

            }
        });

        ilce_linear = (LinearLayout) findViewById(R.id.ilce_linear);
        koy_linear = (LinearLayout) findViewById(R.id.koy_linear);
        seflik_linear = (LinearLayout) findViewById(R.id.seflik_linear);
        koy_linear.setVisibility(GONE);



    }


    ProgressDialog pd2;

    void getBalOrmaniServis() {

        if (yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());


        if (egitim_yil_spinner.getSelectedItem() == null || egitim_yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
            secili_yil = -1L;
        else
            secili_yil = Long.valueOf(egitim_yil_spinner.getSelectedItem().toString());

        ConfigData configData = new ConfigData(this);
        String url = configData.getSERVICURL() + "/";

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(4, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .build();
        RefrofitRestApi refrofitRestApi = retrofit.create(RefrofitRestApi.class);

        SendParametersForServer parameters = new SendParametersForServer();
        parameters.prmBolgeId = secili_bolge_id.toString();
        parameters.prmIsletmeId = secili_mudurluk_id.toString();
        parameters.prmSeflikId = secili_seflik_id.toString();
        parameters.prmIlId = secili_il_id.toString();
        parameters.prmIlceId = secili_ilce_id.toString();
        parameters.prmKoyId = secili_koy_id.toString();

        parameters.prmBirimId = "-1";
        if (!secili_bolge_id.toString().equalsIgnoreCase("-1"))
            parameters.prmBirimId = secili_bolge_id.toString();
        if (!secili_mudurluk_id.toString().equalsIgnoreCase("-1"))
            parameters.prmBirimId = secili_mudurluk_id.toString();
        if (!secili_seflik_id.toString().equalsIgnoreCase("-1"))
            parameters.prmBirimId = secili_seflik_id.toString();
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


            Call<List<YurtdisiProtokol>> call = refrofitRestApi.getYurtDisiListForMobil(parameters);
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

            Call<List<EgitimKatilimci>> call = refrofitRestApi.getGgitimKatilimciPersonelForMobil(parameters);
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



    void get_listview() {
        if (gelenSayfaId.equalsIgnoreCase("0")) {
            davaAdapter = new DavaAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_yedi, gelenDavaList);
            listview.setAdapter(davaAdapter);
            davaAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenEgitimPage.equalsIgnoreCase("0")) {
            yurtdisiProtokolAdapter = new YurtdisiProtokolAdapter(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, R.layout.item_sekiz, gelenYurtDisiProtokolList);
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

    void filtre_spinner(final List<String> gelenBolgeListString, final List<SOrgBirim> gelenBolgeList, final List<SOrgBirim> gelenMudList, final List<SOrgBirim> gelenSeflikList) {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, gelenBolgeListString);
        dataAdapter_bolge.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        bolge_spinner.setAdapter(dataAdapter_bolge);
        bolge_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = gelenBolgeListString.get(position);
                    if (valInfo != null) {
                        selected_bolge_index = position;

                        Long bolge_mud_id = gelenBolgeList.get(selected_bolge_index).getId();
                        secili_bolge_id = bolge_mud_id;
                        secili_mudurluk_id = -1L;
                        secili_seflik_id = -1L;

                        item_source_str_mudurluk.clear();
                        item_souce_mudurluk.clear();
                        item_source_str_seflik.clear();
                        item_source_seflik.clear();

                        item_souce_mudurluk.add(null);
                        item_source_str_mudurluk.add("");

                        for (SOrgBirim item : gelenMudList) {
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


                        if (!localDataManager.getValues(getApplicationContext(), "bolgeId").toString().equalsIgnoreCase(String.valueOf(selected_bolge_index))) {
                        } else {
                            selected_mudurluk_index = shared_mudurluk_index;
                            selected_seflik_index = shared_seflik_index;
                            mudurluk_spinner.setSelection(selected_mudurluk_index);

                        }
                    }
                } else {
                    secili_bolge_id = -1L;
                    secili_mudurluk_id = -1L;
                    secili_seflik_id = -1L;

                }
                localDataManager.setSharedPreference(getApplicationContext(), "bolgeId", String.valueOf(position));


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


                        for (SOrgBirim item : gelenSeflikList) {
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

                        if (!localDataManager.getValues(getApplicationContext(), "mudurlukId").toString().equalsIgnoreCase(String.valueOf(selected_mudurluk_index))) {
                        } else {
                            selected_seflik_index = shared_seflik_index;
                            seflik_spinner.setSelection(selected_seflik_index);
                        }

                    } else {
                        secili_mudurluk_id = -1L;
                        secili_seflik_id = -1L;
                    }
                }
                else {
                    secili_mudurluk_id = -1L;
                    secili_seflik_id = -1L;

                }
                localDataManager.setSharedPreference(getApplicationContext(), "mudurlukId", String.valueOf(position));

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
                localDataManager.setSharedPreference(getApplicationContext(), "seflikId", String.valueOf(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    void filtre_spinner_il_ilce_koy() {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.il_list_string);
        dataAdapter_bolge.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        il_spinner.setAdapter(dataAdapter_bolge);
        il_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = OrtakFunction.il_list_string.get(position);
                    if (valInfo != null) {
                        selected_il_index = position;

                        Long il_id = OrtakFunction.il_list.get(selected_il_index).getId();
                        secili_il_id = il_id;
                        secili_ilce_id = -1L;
                        secili_koy_id = -1L;

                        item_source_str_ilce.clear();
                        ilce_list.clear();
                        item_source_str_koy.clear();
                        koy_list.clear();

                        ilce_list.add(null);
                        item_source_str_ilce.add("");

                        for (STown item : OrtakFunction.ilce_list) {
                            if (item == null)
                                continue;
                            if (String.valueOf(item.getIlId()).equals(String.valueOf(il_id))) {
                                item_source_str_ilce.add(item.getAdi());
                                ilce_list.add(item);
                            }
                        }


                        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
                        dataAdapter_ilce.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        ilce_spinner.setAdapter(dataAdapter_ilce);
                        ilce_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
                        dataAdapter_koy.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        koy_spinner.setAdapter(dataAdapter_koy);
                        koy_spinner.setSelection(0);


                        if (!localDataManager.getValues(getApplicationContext(), "ilId").toString().equalsIgnoreCase(String.valueOf(selected_il_index))) {
                        } else {
                            selected_ilce_index = shared_ilce_index;
                            selected_koy_index = shared_koy_index;
                            ilce_spinner.setSelection(selected_ilce_index);

                        }

                    }
                } else {
                    secili_il_id = -1L;
                    secili_ilce_id = -1L;
                    secili_koy_id = -1L;

                }
                localDataManager.setSharedPreference(getApplicationContext(), "ilId", String.valueOf(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(il_spinner);
            popupWindow.setHeight(700);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }

        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
        dataAdapter_ilce.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);

        ilce_spinner.setAdapter(dataAdapter_ilce);
        ilce_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = item_source_str_ilce.get(position);
                    if (valInfo != null) {
                        selected_ilce_index = position;

                        Long ilce_id = ilce_list.get(selected_ilce_index).getId();
                        secili_ilce_id = ilce_id;
                        secili_koy_id = -1L;

                        item_source_str_koy.clear();
                        koy_list.clear();

                        koy_list.add(null);
                        item_source_str_koy.add("");


                        for (SKoyBelde item : OrtakFunction.koy_list) {
                            if (item == null)
                                continue;
                            if (String.valueOf(item.getMulkiYerAdi()).equalsIgnoreCase(ilce_list.get(selected_ilce_index).getAdi()) &&
                                    ilce_list.get(selected_ilce_index).getGorunum().contains(item.getIlAdi())) {
                                item_source_str_koy.add(item.getKoyAdi());
                                koy_list.add(item);
                            }
                        }


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
                        dataAdapter_koy.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        koy_spinner.setAdapter(dataAdapter_koy);
                        koy_spinner.setSelection(0);


                        if (!localDataManager.getValues(getApplicationContext(), "ilceId").toString().equalsIgnoreCase(String.valueOf(selected_ilce_index))) {
                        } else {
                            selected_koy_index = shared_koy_index;
                            koy_spinner.setSelection(selected_koy_index);
                        }
                    } else {
                        secili_ilce_id = -1L;
                        secili_koy_id = -1L;
                    }
                }
                localDataManager.setSharedPreference(getApplicationContext(), "ilceId", String.valueOf(position));
                localDataManager.setSharedPreference(getApplicationContext(), "koyId", String.valueOf(0));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
        dataAdapter_koy.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);

        koy_spinner.setAdapter(dataAdapter_koy);
        koy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String valInfo = item_source_str_koy.get(position);
                    if (valInfo != null) {
                        selected_koy_index = position;
                        secili_koy_id = koy_list.get(selected_koy_index).getId();
                    }
                } else {
                    secili_koy_id = -1L;
                }
                localDataManager.setSharedPreference(getApplicationContext(), "koyId", String.valueOf(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void radioGrupChange(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_gen_mud:
                filtre_spinner(OrtakFunction.genel_mud_bolge_list_string, OrtakFunction.genel_mud_bolge_list, OrtakFunction.genel_mud_mudurluk_list, OrtakFunction.genel_mud_seflik_list);
                localDataManager.setSharedPreference(getApplicationContext(), "radioButton", String.valueOf(R.id.radio_gen_mud));
                break;
            case R.id.radio_bolge:
                filtre_spinner(OrtakFunction.bolge_list_string, OrtakFunction.bolge_list, OrtakFunction.mudurluk_list,
                        OrtakFunction.seflik_list);
                localDataManager.setSharedPreference(getApplicationContext(), "radioButton", String.valueOf(R.id.radio_bolge));

                break;
            case R.id.radio_teskilat:
                filtre_spinner(OrtakFunction.teskilat_bolge_list_string, OrtakFunction.teskilat_bolge_list, OrtakFunction.teskilat_mudurluk_list,
                        OrtakFunction.teskilat_seflik_list);
                localDataManager.setSharedPreference(getApplicationContext(), "radioButton", String.valueOf(R.id.radio_teskilat));
                break;
        }

    }


    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        Log.d("ExpandableLayout", "State: " + state);
        expandButton.setRotation(expansionFraction * 90);
    }

    public void detayOnClick(View view) {
        expandableLayout.toggle();

        if (expandableLayout.getState() == 2) {
            linearLayout_iki.setVisibility(GONE);
            linearLayout_bir.setVisibility(GONE);
        } else {
            expandableLayout.collapse();

            linearLayout_iki.setVisibility(View.VISIBLE);
            linearLayout_bir.setVisibility(View.VISIBLE);

        }
    }

    public void shared_values() {

        shared_bolge_index = localDataManager.getValues(getApplicationContext(), "bolgeId");
        shared_mudurluk_index = localDataManager.getValues(getApplicationContext(), "mudurlukId");
        shared_seflik_index = localDataManager.getValues(getApplicationContext(), "seflikId");

        shared_il_index = localDataManager.getValues(getApplicationContext(), "ilId");
        shared_ilce_index = localDataManager.getValues(getApplicationContext(), "ilceId");
        shared_koy_index = localDataManager.getValues(getApplicationContext(), "koyId");

        birimRadioGrup.check(localDataManager.getValues(getApplicationContext(), "radioButton") == null ? null : localDataManager.getValues(getApplicationContext(), "radioButton"));
        if (localDataManager.getValues(getApplicationContext(), "radioButton").equals(R.id.radio_gen_mud))
            birimRadioGrup.check(R.id.radio_gen_mud);
        else if (localDataManager.getValues(getApplicationContext(), "radioButton").equals(R.id.radio_bolge))
            birimRadioGrup.check(R.id.radio_bolge);
        else if (localDataManager.getValues(getApplicationContext(), "radioButton").equals(R.id.radio_teskilat))
            birimRadioGrup.check(R.id.radio_teskilat);
        else
            filtre_spinner(OrtakFunction.bolge_list_string, OrtakFunction.bolge_list, OrtakFunction.mudurluk_list,
                    OrtakFunction.seflik_list);


        bolge_spinner.setSelection(shared_bolge_index);
        mudurluk_spinner.setSelection(shared_mudurluk_index);
        seflik_spinner.setSelection(shared_seflik_index);
        yil_spinner.setSelection(localDataManager.getValues(getApplicationContext(), "yil"));
        filtre_spinner_il_ilce_koy();
        il_spinner.setSelection(shared_il_index);
        ilce_spinner.setSelection(shared_ilce_index);
        koy_spinner.setSelection(shared_koy_index);

        birimRadioGrup.check(R.id.radio_bolge);
        if (shared_bolge_index != 0)
            secili_bolge_id = ((SOrgBirim) OrtakFunction.bolge_list.get(shared_bolge_index)).getId();
        else
            secili_bolge_id = -1L;
        if (shared_mudurluk_index != 0)
            secili_mudurluk_id = ((SOrgBirim) OrtakFunction.bolge_list.get(shared_mudurluk_index)).getId();
        else
            secili_mudurluk_id = -1L;
        if (shared_seflik_index != 0)
            secili_seflik_id = ((SOrgBirim) OrtakFunction.bolge_list.get(shared_seflik_index)).getId();
        else
            secili_seflik_id = -1L;

        if (shared_il_index != 0)
            secili_il_id = ((SCity) OrtakFunction.il_list.get(shared_il_index)).getId();
        else
            secili_il_id = -1L;
        if (shared_ilce_index != 0)
            secili_ilce_id = ((STown) OrtakFunction.ilce_list.get(shared_ilce_index)).getId();
        else
            secili_ilce_id = -1L;
        if (shared_koy_index != 0)
            secili_koy_id = ((SKoyBelde) OrtakFunction.koy_list.get(shared_koy_index)).getId();
        else
            secili_koy_id = -1L;
    }

}