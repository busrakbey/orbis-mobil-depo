package com.konumsal.orbisozetmobil.Kdm_Str_BilgiSis_UI;


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

import com.esri.core.geometry.Line;
import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


import AdapterLayer.BilgiSistemleri.HaritaArsivAdapter;
import AdapterLayer.DisIliskiler.BirimAutoCompleteAdapter;
import AdapterLayer.Kadastro.KdmGerceklesmeAdapter;
import AdapterLayer.Strateji.TefKonularAdapter;
import DataLayer.Ortak.ConfigData;
import DataLayer.Sistem.SOrgBirim_Data;
import EntityLayer.BilgiSistemleri.HaritaArsiv;
import EntityLayer.Kadastro.KdmGerceklesme;
import EntityLayer.SendParametersForServer;
import EntityLayer.Sistem.SOrgBirim;
import EntityLayer.Strateji.TefKonular;
import ToolLayer.NullOnEmptyConverterFactory;
import ToolLayer.OrbisDefaultException;
import ToolLayer.RefrofitRestApi;
import ToolLayer.MessageBox;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kdm_Str_BilgiSis_SorgulamaActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner bolge_spinner, mudurluk_spinner, seflik_spinner, yil_spinner, harita_yil_spinner;
    Button sorgula_button, temizle_button;
    ProgressDialog pd_main;
    int selected_bolge_index = 0, selected_mudurluk_index = 0, selected_seflik_index = 0;
    Long secili_bolge_id = -1L, secili_mudurluk_id = -1L, secili_seflik_id = -1L, secili_yil = -1L;
    List<String> item_source_str_mudurluk;
    List<String> item_source_str_seflik;
    List<SOrgBirim> item_souce_mudurluk;
    List<SOrgBirim> item_source_seflik;
    KdmGerceklesmeAdapter kdmGerceklesmeAdapter;
    HaritaArsivAdapter haritaArsivAdapter;
    TefKonularAdapter tefKonularAdapter;
    List<KdmGerceklesme> gelenKdmGerceklesmeList;
    List<HaritaArsiv> gelenHaritaArsivList;
    List<TefKonular> gelenTefKonularList;
    AutoCompleteTextView bolge_auto, isletme_acuto;

    ListView listview;
    String gelenSayfaId;
    LinearLayout baslikLinear1, baslikLinear2, baslikLinear3, sorguharita_linear, genelsorgu_linear, kdm_ana_baslik_linear;
    LinearLayout mudurluk_linear, seflik_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.kdm_str_bilgisis_activity);

        Intent i = getIntent();
        gelenSayfaId = i.getStringExtra("MODE");

        Init();
        initToolBar();
        filtre_spinner();
        autocomplete_birim();
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
                getSupportActionBar().setTitle("Orman Kad. Gerçekleşme Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.GONE);
                genelsorgu_linear.setVisibility(View.VISIBLE);
                sorguharita_linear.setVisibility(View.GONE);
                kdm_ana_baslik_linear.setVisibility(View.VISIBLE);
                seflik_linear.setVisibility(View.GONE);
                mudurluk_linear.setVisibility(View.GONE);
            }
            if (gelenSayfaId.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Meşcere Taslak Üretim Durumu");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(View.GONE);
                genelsorgu_linear.setVisibility(View.GONE);
                sorguharita_linear.setVisibility(View.VISIBLE);
                kdm_ana_baslik_linear.setVisibility(View.GONE);
                seflik_linear.setVisibility(View.VISIBLE);
                mudurluk_linear.setVisibility(View.VISIBLE);


            }

            if (gelenSayfaId.equalsIgnoreCase("2")) {
                getSupportActionBar().setTitle("Teftiş Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.GONE);
                genelsorgu_linear.setVisibility(View.VISIBLE);
                sorguharita_linear.setVisibility(View.GONE);
                kdm_ana_baslik_linear.setVisibility(View.GONE);
                seflik_linear.setVisibility(View.GONE);
                mudurluk_linear.setVisibility(View.VISIBLE);

            }

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Kdm_Str_BilgiSis_SorgulamaActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata:" + e.toString(), false);
        }
    }

    void Init() {
        bolge_spinner = (Spinner) findViewById(R.id.bolge_spinner);
        mudurluk_spinner = (Spinner) findViewById(R.id.mudurluk_spinner);
        seflik_spinner = (Spinner) findViewById(R.id.seflik_spinner);
        yil_spinner = (Spinner) findViewById(R.id.yil_spinner);
        harita_yil_spinner = (Spinner) findViewById(R.id.egitim_yil_spinner);
        bolge_auto = (AutoCompleteTextView) findViewById(R.id.bolge_auto);
        isletme_acuto = (AutoCompleteTextView) findViewById(R.id.mudurluk_auto);
        sorguharita_linear = (LinearLayout) findViewById(R.id.egitim);
        genelsorgu_linear = (LinearLayout) findViewById(R.id.genel_sorgu);
        kdm_ana_baslik_linear = (LinearLayout) findViewById(R.id.kdm_ana_baslik);
        mudurluk_linear = (LinearLayout) findViewById(R.id.mudurluk_linear);
        seflik_linear = (LinearLayout) findViewById(R.id.seflik_linear);

        ArrayList<String> years = new ArrayList<String>();
        years.add("");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
        yil_spinner.setAdapter(adapter);
        harita_yil_spinner.setAdapter(adapter);

        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        baslikLinear1 = (LinearLayout) findViewById(R.id.birinci_baslik);
        baslikLinear2 = (LinearLayout) findViewById(R.id.ikinci_baslik);
        baslikLinear3 = (LinearLayout) findViewById(R.id.ucuncu_baslik);

        pd2 = new ProgressDialog(Kdm_Str_BilgiSis_SorgulamaActivity.this);
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
                harita_yil_spinner.setSelection(0);
                secili_mudurluk_id = -1L;
                secili_bolge_id = -1L;
                secili_seflik_id = -1L;
                bolge_auto.setText("");
                isletme_acuto.setText("");
                secilenBolgeId = -1L;
                secilenIsletmeId = -1L;
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


        if (harita_yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
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
        progressDoalog = new ProgressDialog(Kdm_Str_BilgiSis_SorgulamaActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();

        if (gelenSayfaId.equalsIgnoreCase("0")) {
            Call<List<KdmGerceklesme>> call = refrofitRestApi.getKdmProgramGerceklesmeListForMobil(parameters);
            call.enqueue(new Callback<List<KdmGerceklesme>>() {
                @Override
                public void onResponse(Call<List<KdmGerceklesme>> call, Response<List<KdmGerceklesme>> response) {
                    if (!response.isSuccessful()) {
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenKdmGerceklesmeList = response.body();
                        if (gelenKdmGerceklesmeList != null && gelenKdmGerceklesmeList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Kayıt bulunamamıştır..", false);
                    }
                }

                @Override
                public void onFailure(Call<List<KdmGerceklesme>> call, Throwable t) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {

            SendParametersForServer parameters_arsiv = new SendParametersForServer();
            if (secilenBolgeId != null)
                parameters_arsiv.prmBolgeId = secilenBolgeId.toString();
            else
                parameters_arsiv.prmBolgeId = "-1";
            if (secilenIsletmeId != null)
                parameters_arsiv.prmIsletmeId = secilenIsletmeId.toString();
            else
                parameters_arsiv.prmIsletmeId = "-1";
            parameters_arsiv.prmSeflikId = secili_seflik_id.toString();
            parameters_arsiv.prmYil = secili_yil.toString();
            Call<List<HaritaArsiv>> call = refrofitRestApi.getHaritaArsivDepoDosyaListForMobil(parameters_arsiv);
            call.enqueue(new Callback<List<HaritaArsiv>>() {
                @Override
                public void onResponse(Call<List<HaritaArsiv>> call, Response<List<HaritaArsiv>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenHaritaArsivList = response.body();
                        if (gelenHaritaArsivList != null && gelenHaritaArsivList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<HaritaArsiv>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {

            Call<List<TefKonular>> call = refrofitRestApi.getStrDepoTefKonularForMobilService(parameters);
            call.enqueue(new Callback<List<TefKonular>>() {
                @Override
                public void onResponse(Call<List<TefKonular>> call, Response<List<TefKonular>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenTefKonularList = response.body();
                        if (gelenTefKonularList != null && gelenTefKonularList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<TefKonular>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(Kdm_Str_BilgiSis_SorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });

        }
        if (gelenSayfaId.equalsIgnoreCase("3")) {

        }
    }


    void filtre_spinner() {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.bolge_list_string);
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


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
                        mudurluk_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
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


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(Kdm_Str_BilgiSis_SorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
            kdmGerceklesmeAdapter = new KdmGerceklesmeAdapter(Kdm_Str_BilgiSis_SorgulamaActivity.this, R.layout.item_on, gelenKdmGerceklesmeList);
            listview.setAdapter(kdmGerceklesmeAdapter);
            kdmGerceklesmeAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            haritaArsivAdapter = new HaritaArsivAdapter(Kdm_Str_BilgiSis_SorgulamaActivity.this, R.layout.item_dort, gelenHaritaArsivList);
            listview.setAdapter(haritaArsivAdapter);
            haritaArsivAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {
            tefKonularAdapter = new TefKonularAdapter(Kdm_Str_BilgiSis_SorgulamaActivity.this, R.layout.item_on, gelenTefKonularList);
            listview.setAdapter(tefKonularAdapter);
            tefKonularAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }

    }


    Long secilenBolgeId = -1L, secilenIsletmeId = -1L;
    List<SOrgBirim> org_birim_list;

    void autocomplete_birim() {

        SOrgBirim_Data data = new SOrgBirim_Data(Kdm_Str_BilgiSis_SorgulamaActivity.this);
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
        bolge_auto.setThreshold(2);
        bolge_auto.setAdapter(adapter);
        bolge_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // final MuhStokLokasyon dty = (MuhStokLokasyon) parent.getItemAtPosition(position);
                SOrgBirim dty = (SOrgBirim) parent.getAdapter().getItem(position);

                if (dty != null) {
                    secilenBolgeId = dty.getId();
                }
            }
        });

        isletme_acuto.setThreshold(2);
        isletme_acuto.setAdapter(adapter);
        isletme_acuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // final MuhStokLokasyon dty = (MuhStokLokasyon) parent.getItemAtPosition(position);
                SOrgBirim dty = (SOrgBirim) parent.getAdapter().getItem(position);

                if (dty != null) {
                    secilenIsletmeId = dty.getId();
                }
            }
        });


    }


}


