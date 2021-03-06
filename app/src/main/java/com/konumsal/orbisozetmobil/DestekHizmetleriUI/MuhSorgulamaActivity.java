package com.konumsal.orbisozetmobil.DestekHizmetleriUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.konumsal.orbisozetmobil.IsletmePazarlamaUI.IPSorgulamaActivity;
import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import AdapterLayer.DestekHizmetleri.MaliGelirAdapter;
import AdapterLayer.DestekHizmetleri.MaliGiderAdapter;
import AdapterLayer.DestekHizmetleri.MaliHesapAdapter;
import AdapterLayer.DestekHizmetleri.PersonelGiderAdapter;
import AdapterLayer.IsletmePazarlama.DamgaAdapter;
import AdapterLayer.IsletmePazarlama.OdenekAdapter;
import AdapterLayer.IsletmePazarlama.SatisAdapter;
import AdapterLayer.IsletmePazarlama.UretimAdapter;
import DataLayer.Ortak.ConfigData;
import EntityLayer.DestekHizmetleri.MaliGelir;
import EntityLayer.DestekHizmetleri.MaliGider;
import EntityLayer.DestekHizmetleri.MaliHesap;
import EntityLayer.DestekHizmetleri.PersonelGider;
import EntityLayer.IsletmePazarlama.Damga;
import EntityLayer.IsletmePazarlama.Odenek;
import EntityLayer.IsletmePazarlama.Satis;
import EntityLayer.IsletmePazarlama.Uretim;
import EntityLayer.Ortak.SCity;
import EntityLayer.Ortak.SKoyBelde;
import EntityLayer.Ortak.STown;
import EntityLayer.SendParametersForServer;
import EntityLayer.Sistem.SOrgBirim;
import EnumsLayer.LocalDataManager;
import ToolLayer.MessageBox;
import ToolLayer.NullOnEmptyConverterFactory;
import ToolLayer.RefrofitRestApi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ToolLayer.RetrofirCertifica.getUnsafeOkHttpClient;
import static android.view.View.GONE;

public class MuhSorgulamaActivity extends AppCompatActivity  {
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
    MaliGelirAdapter maliGelirAdapter;
    MaliGiderAdapter maliGiderAdapter;
    MaliHesapAdapter maliHesapAdapter;
    PersonelGiderAdapter personelGiderAdapter;
    List<MaliGelir> gelenMaliGelirList;
    List<MaliGider> gelenMaliGiderList;
    List<PersonelGider> gelenPersonelGiderList;
    List<MaliHesap> gelenMaliHesapList;
    ListView listview;
    String gelenSayfaId;
    LinearLayout baslikLinear1, baslikLinear2, baslikLinear3, baslikLinear4;

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
private ImageView expandButton;
    LinearLayout linearLayout_bir, linearLayout_iki,seflik_linear, ilce_linear, koy_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.muh_sorgulama_activity);

        Intent i = getIntent();
        gelenSayfaId = i.getStringExtra("MODE");


        Init();
        initToolBar();
        shared_values();
    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //  getSupportActionBar().setLogo(R.mipmap.ic_launcher_ogmlogo);
            if (gelenSayfaId.equalsIgnoreCase("0")) {
                getSupportActionBar().setTitle("Mali Gelir Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.GONE);
                baslikLinear4.setVisibility(View.GONE);

            }
            if (gelenSayfaId.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Mali Gider Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(View.GONE);
                baslikLinear4.setVisibility(View.GONE);

            }
            if (gelenSayfaId.equalsIgnoreCase("2")) {
                getSupportActionBar().setTitle("Mali Hesap Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.VISIBLE);
                baslikLinear4.setVisibility(View.GONE);
            }
            if (gelenSayfaId.equalsIgnoreCase("3")) {
                getSupportActionBar().setTitle("Personel Gider  Listesi");
                baslikLinear1.setVisibility(View.GONE);
                baslikLinear2.setVisibility(View.GONE);
                baslikLinear3.setVisibility(View.GONE);
                baslikLinear4.setVisibility(View.VISIBLE);
            }

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MuhSorgulamaActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata:" + e.toString(), false);
        }
    }

    void Init() {

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

        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

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

        pd2 = new ProgressDialog(MuhSorgulamaActivity.this);
        listview = (ListView) findViewById(R.id.oduh_listview);
        sorgula_button = (Button) findViewById(R.id.oduh_sorgula_button);
        sorgula_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                    getBalOrmaniServis();
                else
                    MessageBox.showAlert(MuhSorgulamaActivity.this, "Lütfen yıl seçiniz..", false);

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
        ilce_linear.setVisibility(GONE);
        seflik_linear.setVisibility(GONE);


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
        parameters.prmBolgeId = secili_bolge_id.toString().equalsIgnoreCase("-1") ? null  : secili_bolge_id.toString();
        parameters.prmIsletmeId = secili_mudurluk_id.toString().equalsIgnoreCase("-1") ? null  : secili_mudurluk_id.toString();
        parameters.prmSeflikId = secili_seflik_id.toString().equalsIgnoreCase("-1") ? null  : secili_seflik_id.toString();
        parameters.prmYil = secili_yil.toString();
        parameters.prmIlId = secili_il_id.toString();
        parameters.prmIlceId = secili_ilce_id.toString();
        parameters.prmKoyId = secili_koy_id.toString();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MuhSorgulamaActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();

        if (gelenSayfaId.equalsIgnoreCase("0")) {
            Call<List<MaliGelir>> call = refrofitRestApi.getMaliTabLoGelirler(parameters);
            call.enqueue(new Callback<List<MaliGelir>>() {
                @Override
                public void onResponse(Call<List<MaliGelir>> call, Response<List<MaliGelir>> response) {
                    if (!response.isSuccessful()) {
                        progressDoalog.dismiss();
                        MessageBox.showAlert(MuhSorgulamaActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenMaliGelirList = response.body();
                        if (gelenMaliGelirList != null && gelenMaliGelirList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(MuhSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);
                    }
                }

                @Override
                public void onFailure(Call<List<MaliGelir>> call, Throwable t) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            Call<List<MaliGider>> call = refrofitRestApi.getMaliTabloGiderler(parameters);
            call.enqueue(new Callback<List<MaliGider>>() {
                @Override
                public void onResponse(Call<List<MaliGider>> call, Response<List<MaliGider>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenMaliGiderList = response.body();
                        if (gelenMaliGiderList != null && gelenMaliGiderList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(MuhSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<MaliGider>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {

            Call<List<MaliHesap>> call = refrofitRestApi.getOzetHesaplar(parameters);
            call.enqueue(new Callback<List<MaliHesap>>() {
                @Override
                public void onResponse(Call<List<MaliHesap>> call, Response<List<MaliHesap>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenMaliHesapList = response.body();
                        if (gelenMaliHesapList != null && gelenMaliHesapList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(MuhSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<MaliHesap>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });

        }
        if (gelenSayfaId.equalsIgnoreCase("3")) {

            Call<List<PersonelGider>> call = refrofitRestApi.getPersonelHarcamalari(parameters);
            call.enqueue(new Callback<List<PersonelGider>>() {
                @Override
                public void onResponse(Call<List<PersonelGider>> call, Response<List<PersonelGider>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenPersonelGiderList = response.body();
                        if (gelenPersonelGiderList != null && gelenPersonelGiderList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(MuhSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<PersonelGider>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(MuhSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
    }



    void get_listview() {
        if (gelenSayfaId.equalsIgnoreCase("0")) {
            maliGelirAdapter = new MaliGelirAdapter(MuhSorgulamaActivity.this, R.layout.item_yedi, gelenMaliGelirList);
            listview.setAdapter(maliGelirAdapter);
            maliGelirAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            maliGiderAdapter = new MaliGiderAdapter(MuhSorgulamaActivity.this, R.layout.item_yedi, gelenMaliGiderList);
            listview.setAdapter(maliGiderAdapter);
            maliGiderAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {
            maliHesapAdapter = new MaliHesapAdapter(MuhSorgulamaActivity.this, R.layout.item_uc, gelenMaliHesapList);
            listview.setAdapter(maliHesapAdapter);
            maliHesapAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("3")) {
            personelGiderAdapter = new PersonelGiderAdapter(MuhSorgulamaActivity.this, R.layout.item_iki, gelenPersonelGiderList);
            listview.setAdapter(personelGiderAdapter);
            personelGiderAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
    }

    void filtre_spinner(final List<String> gelenBolgeListString, final List<SOrgBirim> gelenBolgeList, final List<SOrgBirim> gelenMudList, final List<SOrgBirim> gelenSeflikList) {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, gelenBolgeListString);
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


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
                        mudurluk_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
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


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
                }else{
                    secili_mudurluk_id = -1L;
                    secili_seflik_id = -1L;
                }
                localDataManager.setSharedPreference(getApplicationContext(), "mudurlukId", String.valueOf(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.il_list_string);
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


                        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
                        dataAdapter_ilce.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        ilce_spinner.setAdapter(dataAdapter_ilce);
                        ilce_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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

        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
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


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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


        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(MuhSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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






