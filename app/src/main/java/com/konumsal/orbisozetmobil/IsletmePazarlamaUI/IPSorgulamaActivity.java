package com.konumsal.orbisozetmobil.IsletmePazarlamaUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.konumsal.orbisozetmobil.OrtakUI.OrtakFunction;
import com.konumsal.orbisozetmobil.R;



import org.apache.poi.hssf.record.formula.functions.T;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import AdapterLayer.IsletmePazarlama.DamgaAdapter;
import AdapterLayer.IsletmePazarlama.OdenekAdapter;
import AdapterLayer.IsletmePazarlama.SatisAdapter;
import AdapterLayer.IsletmePazarlama.UretimAdapter;
import DataLayer.Ortak.ConfigData;
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

public class IPSorgulamaActivity extends AppCompatActivity {
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
    DamgaAdapter damgaAdapter;
    OdenekAdapter odenekAdapter;
    SatisAdapter satisAdapter;
    UretimAdapter uretimAdapter;
    List<Odenek> gelenOdenekList;
    List<Satis> gelenSatisList;
    List<Uretim> gelenUretimList;
    List<Damga> gelenDamgaList;
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
    LinearLayout linearLayout_bir, linearLayout_iki, seflik_linear, ilce_linear, koy_linear;

    private PieChart chart;
    private BarChart chart2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ip_sorgulama_activity);

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
                getSupportActionBar().setTitle("Damga Listesi");
                baslikLinear1.setVisibility(View.VISIBLE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(GONE);

            }
            if (gelenSayfaId.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("Ödenek Listesi");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(View.VISIBLE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(GONE);
                seflik_linear.setVisibility(GONE);

            }
            if (gelenSayfaId.equalsIgnoreCase("2")) {
                getSupportActionBar().setTitle("Satış Listesi");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(View.VISIBLE);
                baslikLinear4.setVisibility(GONE);
                seflik_linear.setVisibility(GONE);

            }
            if (gelenSayfaId.equalsIgnoreCase("3")) {
                getSupportActionBar().setTitle("Üretim Listesi");
                baslikLinear1.setVisibility(GONE);
                baslikLinear2.setVisibility(GONE);
                baslikLinear3.setVisibility(GONE);
                baslikLinear4.setVisibility(View.VISIBLE);
            }

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IPSorgulamaActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(IPSorgulamaActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(IPSorgulamaActivity.this, "Hata:" + e.toString(), false);
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

        pd2 = new ProgressDialog(IPSorgulamaActivity.this);
        listview = (ListView) findViewById(R.id.oduh_listview);
        sorgula_button = (Button) findViewById(R.id.oduh_sorgula_button);
        sorgula_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                    getBalOrmaniServis();
                else
                    MessageBox.showAlert(IPSorgulamaActivity.this, "Lütfen yıl seçiniz..", false);


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
                secili_il_id = -1L;
                secili_ilce_id = -1L;
                secili_koy_id = -1L;

            }
        });

        ilce_linear = (LinearLayout) findViewById(R.id.ilce_linear);
        koy_linear = (LinearLayout) findViewById(R.id.koy_linear);
        seflik_linear = (LinearLayout) findViewById(R.id.seflik_linear);
        koy_linear.setVisibility(GONE);
        ilce_linear.setVisibility(GONE);

        chart = (PieChart) findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);
        chart.setCenterText(generateCenterText());
        chart.setCenterTextSize(10f);
        chart.setHoleRadius(45f);
        chart.setTransparentCircleRadius(50f);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);


        chart2 = (BarChart) findViewById(R.id.chart2);
        chart2.getDescription().setEnabled(false);

//        chart2.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart2.setPinchZoom(false);
        chart2.setDrawBarShadow(false);
        chart2.setDrawGridBackground(false);


        Legend l2 = chart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(true);
        l2.setYOffset(0f);
        l2.setXOffset(5f);
        l2.setYEntrySpace(0f);
        l2.setTextSize(8f);

        XAxis xAxis = chart2.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);


        YAxis leftAxis = chart2.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart2.getAxisRight().setEnabled(false);

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
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
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
        parameters.prmYil = secili_yil.toString();
        parameters.prmIlId = secili_il_id.toString();
        parameters.prmIlceId = secili_ilce_id.toString();
        parameters.prmKoyId = secili_koy_id.toString();

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(IPSorgulamaActivity.this);
        progressDoalog.setMessage("Lütfen bekleyiniz..");
        progressDoalog.setTitle("ORBİS");
        progressDoalog.setProgressStyle(ProgressDialog.BUTTON_NEGATIVE);
        progressDoalog.show();

        if (gelenSayfaId.equalsIgnoreCase("0")) {
            Call<List<Damga>> call = refrofitRestApi.getMobilDamga(parameters);
            call.enqueue(new Callback<List<Damga>>() {
                @Override
                public void onResponse(Call<List<Damga>> call, Response<List<Damga>> response) {
                    if (!response.isSuccessful()) {
                        progressDoalog.dismiss();
                        MessageBox.showAlert(IPSorgulamaActivity.this, "Servisle bağlantı sırasında hata oluştu...", false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenDamgaList = response.body();
                        if (gelenDamgaList != null && gelenDamgaList.size() > 0) {
                            get_listview();
                            piechart();

                        } else
                            MessageBox.showAlert(IPSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);
                    }
                }

                @Override
                public void onFailure(Call<List<Damga>> call, Throwable t) {
                    progressDoalog.dismiss();
                    MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            Call<List<Odenek>> call = refrofitRestApi.getMobilOdenek(parameters);
            call.enqueue(new Callback<List<Odenek>>() {
                @Override
                public void onResponse(Call<List<Odenek>> call, Response<List<Odenek>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenOdenekList = response.body();
                        if (gelenOdenekList != null && gelenOdenekList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(IPSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<Odenek>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {

            Call<List<Satis>> call = refrofitRestApi.getMobilSatis(parameters);
            call.enqueue(new Callback<List<Satis>>() {
                @Override
                public void onResponse(Call<List<Satis>> call, Response<List<Satis>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenSatisList = response.body();
                        if (gelenSatisList != null && gelenSatisList.size() > 0) {
                            get_listview();

                        } else
                            MessageBox.showAlert(IPSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<Satis>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });

        }
        if (gelenSayfaId.equalsIgnoreCase("3")) {

            Call<List<Uretim>> call = refrofitRestApi.getMobilUretim(parameters);
            call.enqueue(new Callback<List<Uretim>>() {
                @Override
                public void onResponse(Call<List<Uretim>> call, Response<List<Uretim>> response) {
                    if (!response.isSuccessful()) {
                        // textViewResult.setText("Code: " + response.code());
                        progressDoalog.dismiss();
                        MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + response.message(), false);
                        return;
                    }
                    if (response.isSuccessful()) {
                        progressDoalog.dismiss();
                        gelenUretimList = response.body();
                        if (gelenUretimList != null && gelenUretimList.size() > 0) {
                            get_listview();
                            //bar_chart();


                        } else
                            MessageBox.showAlert(IPSorgulamaActivity.this, "Kayıt bulunamamıştır..", false);

                    }
                }

                @Override
                public void onFailure(Call<List<Uretim>> call, Throwable t) {
                    progressDoalog.dismiss();
                    //textViewResult.setText(t.getMessage());
                    MessageBox.showAlert(IPSorgulamaActivity.this, "Hata Oluştu.. " + t.getMessage(), false);
                }
            });
        }
    }

    void get_listview() {
        if (gelenSayfaId.equalsIgnoreCase("0")) {
            damgaAdapter = new DamgaAdapter(IPSorgulamaActivity.this, R.layout.item_dort, gelenDamgaList);
            listview.setAdapter(damgaAdapter);
            damgaAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("1")) {
            odenekAdapter = new OdenekAdapter(IPSorgulamaActivity.this, R.layout.item_uc, gelenOdenekList);
            listview.setAdapter(odenekAdapter);
            odenekAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("2")) {
            satisAdapter = new SatisAdapter(IPSorgulamaActivity.this, R.layout.item_bes, gelenSatisList);
            listview.setAdapter(satisAdapter);
            satisAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
        if (gelenSayfaId.equalsIgnoreCase("3")) {
            uretimAdapter = new UretimAdapter(IPSorgulamaActivity.this, R.layout.item_uc, gelenUretimList);
            listview.setAdapter(uretimAdapter);
            uretimAdapter.notifyDataSetChanged();
            listview.setClickable(true);
        }
    }

    void filtre_spinner(final List<String> gelenBolgeListString, final List<SOrgBirim> gelenBolgeList, final List<SOrgBirim> gelenMudList, final List<SOrgBirim> gelenSeflikList) {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, gelenBolgeListString);
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


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);
                        mudurluk_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
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


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
                } else {
                    secili_mudurluk_id = -1L;
                    secili_seflik_id = -1L;

                }
                localDataManager.setSharedPreference(getApplicationContext(), "mudurlukId", String.valueOf(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.il_list_string);
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


                        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
                        dataAdapter_ilce.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        ilce_spinner.setAdapter(dataAdapter_ilce);
                        ilce_spinner.setSelection(0);


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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

        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
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


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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


        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(IPSorgulamaActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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


    void piechart() {

        chart.invalidate();
        if (gelenDamgaList != null && gelenDamgaList.size() > 0)
            chart.setData(generatePieData(null, null, null, gelenDamgaList));
        if (gelenUretimList != null)
            chart.setData(generatePieData(gelenUretimList, null, null, null));
        if (gelenSatisList != null)
            chart.setData(generatePieData(null, gelenSatisList, null, null));
        if (gelenOdenekList != null)
            chart.setData(generatePieData(null, null, gelenOdenekList, null));

    }


    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Damga\nListesi");
        s.setSpan(new RelativeSizeSpan(2f), 0, 0, 0);
        // s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        return s;
    }

    protected PieData generatePieData(List<Uretim> uretimList, List<Satis> satisList, List<Odenek> odenekList, List<Damga> damgaList) {
        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < damgaList.size(); i++) {
            if (damgaList.get(i).getDikili() != null)
                entries1.add(new PieEntry(damgaList.get(i).getDikili().setScale(2, RoundingMode.DOWN).floatValue(), "Dikili"));
            if (damgaList.get(i).getDikiliProgram() != null)
                entries1.add(new PieEntry(damgaList.get(i).getDikiliProgram().setScale(2, RoundingMode.DOWN).floatValue(), "Dikili\nProgram"));
            if (damgaList.get(i).getUretimeVerilen() != null)
                entries1.add(new PieEntry(damgaList.get(i).getUretimeVerilen().setScale(2, RoundingMode.DOWN).floatValue(), "Üretime\nVerilen"));
            if (damgaList.get(i).getToplamProgram() != null)
                entries1.add(new PieEntry(damgaList.get(i).getToplamProgram().setScale(2, RoundingMode.DOWN).floatValue(), "Toplam\nProgram"));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Damga Listesi");
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        ds1.setColors(colors);


        //  ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(11f);

        PieData d = new PieData(ds1);
        //  d.setValueTypeface(tf);

        return d;
    }

    void bar_chart() {

        float groupSpace = 0.04f;
        float barSpace = 0.01f; // x4 DataSet
        float barWidth = 0.05f; // x4 DataSet
        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();

        BarData data;
        BarDataSet set1, set2;
        for (int i = 0; i < gelenUretimList.size(); i++) {
            values1.add(new BarEntry(i, gelenUretimList.get(i).getGmiktar() != null ?
                    gelenUretimList.get(i).getGmiktar().setScale(2, RoundingMode.DOWN).floatValue() : null));
            values2.add(new BarEntry(i, gelenUretimList.get(i).getPmiktar() != null ?
                    gelenUretimList.get(i).getPmiktar().setScale(2, RoundingMode.DOWN).floatValue() : null));


            set1 = new BarDataSet(values1, "Gerçekleşme Miktarı");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Program Miktarı");
            set2.setColor(Color.rgb(164, 228, 251));
           /* set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));*/

            data = new BarData(set1);
            data.setValueFormatter(new LargeValueFormatter());

            chart2.setData(data);

        }


        /*if (chart2.getData() != null && chart2.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart2.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) chart2.getData().getDataSetByIndex(1);
            set1.setValues(values1);
            set2.setValues(values2);

            chart2.getData().notifyDataChanged();
            chart2.notifyDataSetChanged();

        } else {
            // create 4 DataSets
            set1 = new BarDataSet(values1, "Company A");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(values2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));
           /* set3 = new BarDataSet(values3, "Company C");
            set3.setColor(Color.rgb(242, 247, 158));
            set4 = new BarDataSet(values4, "Company D");
            set4.setColor(Color.rgb(255, 102, 0));*/

            /*BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());

            chart2.setData(data);
        }*/
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        /*set1.setColors(colors);
        set2.setColors(colors);
        set3.setColors(colors);
        set4.setColors(colors);*/

        chart2.getBarData().setBarWidth(barWidth);
        chart2.getXAxis().setAxisMinimum(0);
        chart2.getXAxis().setAxisMaximum(0 + chart2.getBarData().getGroupWidth(groupSpace, barSpace) * 2);
        chart2.groupBars(0, groupSpace, barSpace);
        chart2.invalidate();
    }
}






