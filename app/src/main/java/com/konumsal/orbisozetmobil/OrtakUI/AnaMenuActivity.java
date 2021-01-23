package com.konumsal.orbisozetmobil.OrtakUI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.reflect.TypeToken;
import com.konumsal.orbisozetmobil.Agaclandirma.AgacAltMenuActivity;
import com.konumsal.orbisozetmobil.DestekHizmetleriUI.MuhAltMenuActivity;
import com.konumsal.orbisozetmobil.EizinUI.EizinAltMenuActivity;
import com.konumsal.orbisozetmobil.Fidanlik_Orkoy_UI.Fidanlik_Orkoy_SorguActivity;
import com.konumsal.orbisozetmobil.Hukuk_DisIlikiler_InsaatIkmal_UI.DisIliskilerAltMenuActivity;
import com.konumsal.orbisozetmobil.Hukuk_DisIlikiler_InsaatIkmal_UI.Hukuk_DisIlikiler_InsaatIkmal_SorguActivity;
import com.konumsal.orbisozetmobil.IsletmePazarlamaUI.IPAltMenuActivity;
import com.konumsal.orbisozetmobil.Kdm_Str_BilgiSis_UI.Kdm_Str_BilgiSis_SorgulamaActivity;
import com.konumsal.orbisozetmobil.OduhUI.OduhAltMenuActivity;
import com.konumsal.orbisozetmobil.OrmIdaresi_Amenajman_Yangin_UI.OrmIdaresi_Amenajman_Yangin_SorguActivity;
import com.konumsal.orbisozetmobil.OzmUI.OzmAltMenuActivity;
import com.konumsal.orbisozetmobil.PersonelUI.PersonelSorguActivity;
import com.konumsal.orbisozetmobil.R;
import com.konumsal.orbisozetmobil.SilvikulturUI.SilAltMenuActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import AdapterLayer.Ortak.AnaMenuAdapter;
import DataLayer.Fidanlik.FidanAjaxTurData;
import DataLayer.IsletmePazarlama.MuhStokLokasyon_Data;
import DataLayer.Ortak.GpBolme_Data;
import DataLayer.Ortak.MobilGuzergah_Data;
import DataLayer.Ortak.OrtakAgacTuru_Data;
import DataLayer.Ortak.OrtakIsKalemleri_Data;
import DataLayer.Ortak.OrtakKamera_Data;
import DataLayer.Ortak.OrtakKisiHareket_Data;
import DataLayer.Ortak.OrtakKisi_Data;
import DataLayer.Ortak.OrtakNotKonu_Data;
import DataLayer.Ortak.OrtakNot_Data;
import DataLayer.Ortak.OrtakOdunTuru_Data;
import DataLayer.Ortak.OtherUsers_Data;
import DataLayer.Ortak.SCity_Data;
import DataLayer.Ortak.SKoyBelde_Data;
import DataLayer.Ortak.STown_Data;
import DataLayer.Ortak.SanatYapi_Data;
import DataLayer.Ortak.Unvan_Data;
import DataLayer.Ortak.User_Data;
import DataLayer.Ortak.YolBilgi_Data;
import DataLayer.Sistem.SCalisan_Data;
import DataLayer.Sistem.SKullanici_Data;
import DataLayer.Sistem.SModulKodDeger_Data;
import DataLayer.Sistem.SOrgBirim_Data;
import EntityLayer.Ortak.SCity;
import EntityLayer.Ortak.SKoyBelde;
import EntityLayer.Ortak.STown;
import EntityLayer.Ortak.User;
import EntityLayer.Sistem.SOrgBirim;
import EnumsLayer.LocalDataManager;
import ToolLayer.MessageBox;
import ToolLayer.OrbisDefaultException;

public class AnaMenuActivity extends AppCompatActivity implements ExpandableLayout.OnExpansionUpdateListener  {

    GridView lst_alt_menu_listview;
    Toolbar toolbar;
    List<String> menuList;
    AnaMenuAdapter menuItemsAdapter;
    public int kontrol = 0;
    ArrayList<SOrgBirim> all_list_SOrgBirim;
    Type typeOf_SOrgBirim = null;
    String all_jsonSOrgBirim = "";
    List<SOrgBirim> org_birim_list;
    int selected_bolge_index = 0, selected_mudurluk_index = 0, selected_seflik_index = 0;
    int shared_bolge_index = 0, shared_mudurluk_index = 0, shared_seflik_index = 0;
    Long secili_bolge_id = -1L, secili_mudurluk_id = -1L, secili_seflik_id = -1L, secili_yil = -1L;
    List<String> item_source_str_mudurluk;
    List<String> item_source_str_seflik;
    List<SOrgBirim> item_souce_mudurluk;
    List<SOrgBirim> item_source_seflik;
    RadioButton genelMudRadioButton, bolgeRadioButton, teskilatRadioButton;
    RadioGroup birimRadioGrup;
    LocalDataManager localDataManager;
    Spinner bolge_spinner, mudurluk_spinner, seflik_spinner, yil_spinner;
    Spinner il_spinner, ilce_spinner, koy_spinner;
    List<SCity> il_list;
    List<STown> ilce_list;
    List<SKoyBelde> koy_list;
    List<String> item_source_str_ilce;
    List<String> item_source_str_koy;
    int selected_il_index = 0, selected_ilce_index = 0, selected_koy_index = 0;
    int shared_il_index = 0, shared_ilce_index = 0, shared_koy_index = 0;
    Long secili_il_id = -1L, secili_ilce_id = -1L, secili_koy_id = -1L;
    private ProgressDialog pd = null;

    private ExpandableLayout expandableLayout;
    private ImageView expandButton;
    LinearLayout linearLayout_bir, linearLayout_iki;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ana_menu_activity);


        Init();
        initToolBar();
        if (kontrol == 0 && OrtakFunction.s_org_birim_path == null) {
            kontrol = 1;
            Log.v("home2", "2");
            ortakVeriIndir("1");

        } else {
            // new getBaseDataServiceForSorgulamalar().execute();
        }

        if (OrtakFunction.mContext == null)
            OrtakFunction.mContext = getBaseContext();


        pd = ProgressDialog.show(
                AnaMenuActivity.this, "", "İlgili veriler yükleniyor..", true);
   //     pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getBolgeMudurlukSeflik();


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
                //     yil_spinner.setSelection(localDataManager.getValues(getApplicationContext(), "yil"));
                filtre_spinner_il_ilce_koy();
                il_spinner.setSelection(shared_il_index);
                ilce_spinner.setSelection(shared_ilce_index);
                koy_spinner.setSelection(shared_koy_index);
            }
        });


    }

    private void initToolBar() {
        try {

            toolbar = (Toolbar) findViewById(R.id.ip_alt_menu_list_ui_toolbar);
            setSupportActionBar(toolbar);
            //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnaMenuActivity.this.finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showAlert(AnaMenuActivity.this, "Hata:" + e.toString(), false);

        } catch (Throwable e) {
            e.printStackTrace();
            MessageBox.showAlert(AnaMenuActivity.this, "Hata:" + e.toString(), false);
        }
    }


    private void menuEkle() {
        menuList.add("Ağaçlandırma ve Toprak Muhafaza");
        menuList.add("Amenajman");
        menuList.add("Bilgi Sistemleri");
        menuList.add("Destek Hizmetleri");
        menuList.add("Dış İlişkiler");
        menuList.add("Fidanlık");
        menuList.add("Hukuk");
        menuList.add("İşletme ve Pazarlama");
        menuList.add("İnşaat ve İkmal");
        menuList.add("Izin İrtifak");
        menuList.add("Kadastro ve Mülkiyet");
        menuList.add("Odun Dışı");
        menuList.add("Orköy");
        menuList.add("Orman Zararlarıyla Mücadele");
        menuList.add("Orman İdaresi");
        menuList.add("Orman Yangınları");
        menuList.add("Personel");
        menuList.add("Silvikültür");
        menuList.add("Teftiş");

    }


    private void Init() {
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
        item_source_str_mudurluk = new ArrayList<String>();
        item_source_str_seflik = new ArrayList<String>();
        item_souce_mudurluk = new ArrayList<SOrgBirim>();
        item_source_seflik = new ArrayList<SOrgBirim>();

        item_source_str_ilce = new ArrayList<String>();
        item_source_str_koy = new ArrayList<String>();
        ilce_list = new ArrayList<STown>();
        koy_list = new ArrayList<SKoyBelde>();
        il_list = new ArrayList<SCity>();


        yil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (yil_spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                    secili_yil = -1L;
                else
                    secili_yil = Long.valueOf(yil_spinner.getSelectedItem().toString());

                localDataManager.setSharedPreference(getApplicationContext(), "yil", String.valueOf(secili_yil));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        birimRadioGrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioGrupChange(group, checkedId);
            }
        });

        localDataManager = new LocalDataManager();

        lst_alt_menu_listview = (GridView) findViewById(R.id.ip_alt_menu_list_listView);
        menuList = new ArrayList<String>();
        menuEkle();
        //pd2 = new ProgressDialog(AnaMenuActivity.this);
        all_list_SOrgBirim = new ArrayList<SOrgBirim>();
        typeOf_SOrgBirim = new TypeToken<List<SOrgBirim>>() {
        }.getType();
        //menuItemsAdapter = new ArrayAdapter<String>(this, android.R.layout. , menuList);

        menuItemsAdapter = new AnaMenuAdapter(AnaMenuActivity.this, R.layout.ana_menu_activity, menuList, 0);
        lst_alt_menu_listview.setAdapter(menuItemsAdapter);

        lst_alt_menu_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent mintent = new Intent(AnaMenuActivity.this, AgacAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 0);
                    startActivity(mintent);

                } else if (position == 1) {
                    Intent mintent = new Intent(AnaMenuActivity.this, OrmIdaresi_Amenajman_Yangin_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "1");  /// AMENAJMAN
                    startActivity(mintent);
                } else if (position == 2) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Kdm_Str_BilgiSis_SorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "1");  // bilgi sistemleri
                    startActivity(mintent);
                } else if (position == 3) {
                    Intent mintent = new Intent(AnaMenuActivity.this, MuhAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 3);
                    startActivity(mintent);
                } else if (position == 4) {
                    Intent mintent = new Intent(AnaMenuActivity.this, DisIliskilerAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "1"); // dış ilişkliler
                    startActivity(mintent);
                } else if (position == 5) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Fidanlik_Orkoy_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "0");  //fidanlık
                    startActivity(mintent);
                } else if (position == 6) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "0");  /// hukuk
                    startActivity(mintent);
                } else if (position == 7) {
                    Intent mintent = new Intent(AnaMenuActivity.this, IPAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 7);
                    startActivity(mintent);
                } else if (position == 8) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Hukuk_DisIlikiler_InsaatIkmal_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "2");
                    startActivity(mintent);
                } else if (position == 9) {
                    Intent mintent = new Intent(AnaMenuActivity.this, EizinAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 9);
                    startActivity(mintent);
                } else if (position == 10) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Kdm_Str_BilgiSis_SorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "0"); // kadastro
                    startActivity(mintent);
                } else if (position == 11) {
                    Intent mintent = new Intent(AnaMenuActivity.this, OduhAltMenuActivity.class);
                    mintent.putExtra("MODE", 11);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mintent);
                } else if (position == 12) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Fidanlik_Orkoy_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "1");  ///orkoy
                    startActivity(mintent);
                } else if (position == 13) {
                    Intent mintent = new Intent(AnaMenuActivity.this, OzmAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 13);
                    startActivity(mintent);
                } else if (position == 14) {
                    Intent mintent = new Intent(AnaMenuActivity.this, OrmIdaresi_Amenajman_Yangin_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "0");  //// ORMAN İDARESİ
                    startActivity(mintent);
                } else if (position == 15) {
                    Intent mintent = new Intent(AnaMenuActivity.this, OrmIdaresi_Amenajman_Yangin_SorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "2");  ///YANGIN
                    startActivity(mintent);
                } else if (position == 16) {
                    Intent mintent = new Intent(AnaMenuActivity.this, PersonelSorguActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 16);
                    startActivity(mintent);
                } else if (position == 17) {
                    Intent mintent = new Intent(AnaMenuActivity.this, SilAltMenuActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", 17);
                    startActivity(mintent);
                } else if (position == 18) {
                    Intent mintent = new Intent(AnaMenuActivity.this, Kdm_Str_BilgiSis_SorgulamaActivity.class);
                    mintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mintent.putExtra("MODE", "2"); // teftis
                    startActivity(mintent);
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu1, menu);
        Log.v("main", "options menu");
        return true;
    }

    OrtakAyarlar oa;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Log.v("home1", "1");
                ortakVeriIndir("0");
                return true;

            case R.id.menu2:
                Log.v("home2", "1");
                if (oa == null)
                    oa = new OrtakAyarlar();
                oa.showAyarlarPage(AnaMenuActivity.this);

                return true;

            case R.id.menu3:
                Log.v("home4", "1");

                new AlertDialog.Builder(AnaMenuActivity.this)
                        .setTitle("Orbis Mobile Sistem Bilgisi")
                        .setMessage("Cihazınızdaki kullanıcı verileri kalıcı olarak silinecektir.Onaylıyor musunuz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {
                                try {
                                    verileri_sil();
                                } catch (OrbisDefaultException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void ortakVeriIndir(String ilk_giris) {
        Intent mintent = new Intent(AnaMenuActivity.this, ConfigSettingsActivity.class);
        mintent.putExtra("ilk_giris", ilk_giris);
        startActivity(mintent);
    }


    private void verileri_sil() throws OrbisDefaultException {


        SModulKodDeger_Data sModulKodDegerData = new SModulKodDeger_Data(AnaMenuActivity.this);
        SCalisan_Data sCalisanData = new SCalisan_Data(AnaMenuActivity.this);
        SOrgBirim_Data sOrgBirimData = new SOrgBirim_Data(AnaMenuActivity.this);
        SKullanici_Data sKullanici_data = new SKullanici_Data(AnaMenuActivity.this);
        OrtakAgacTuru_Data ortakAgacTuruData = new OrtakAgacTuru_Data(AnaMenuActivity.this);

        OrtakOdunTuru_Data ortakOdunTuruData = new OrtakOdunTuru_Data(AnaMenuActivity.this);
        Unvan_Data unvanData = new Unvan_Data(AnaMenuActivity.this);

        OtherUsers_Data otherUsersData = new OtherUsers_Data(AnaMenuActivity.this);
        GpBolme_Data gpBolmeData = new GpBolme_Data(AnaMenuActivity.this);
        MuhStokLokasyon_Data muhStokLokasyonData = new MuhStokLokasyon_Data(AnaMenuActivity.this);
        OrtakKisi_Data ortakKisiData = new OrtakKisi_Data(AnaMenuActivity.this);
        User_Data userData = new User_Data(AnaMenuActivity.this, new User());

        OrtakNot_Data ortakNotData = new OrtakNot_Data(AnaMenuActivity.this);
        OrtakNotKonu_Data ortakNotKonuData = new OrtakNotKonu_Data(AnaMenuActivity.this);
        OrtakIsKalemleri_Data ortakIsKalemleriData = new OrtakIsKalemleri_Data(AnaMenuActivity.this);
        MobilGuzergah_Data mobilGuzergahData = new MobilGuzergah_Data(AnaMenuActivity.this);
        OrtakKisiHareket_Data ortakKisiHareketData = new OrtakKisiHareket_Data(AnaMenuActivity.this);


        STown_Data sTownData = new STown_Data(AnaMenuActivity.this);

        OrtakKamera_Data ortakKameraData = new OrtakKamera_Data(AnaMenuActivity.this);
        FidanAjaxTurData fidanAjaxTurData = new FidanAjaxTurData(AnaMenuActivity.this);
        SanatYapi_Data sanatYapiData = new SanatYapi_Data(AnaMenuActivity.this);
        YolBilgi_Data yolBilgiData = new YolBilgi_Data(AnaMenuActivity.this);


        userData.clearDatabaseTable();
        sModulKodDegerData.clearDatabaseTable();
        sCalisanData.clearDatabaseTable();
        sOrgBirimData.clearDatabaseTable();
        sKullanici_data.clearDatabaseTable();
        ortakAgacTuruData.clearDatabaseTable();
        ortakOdunTuruData.clearDatabaseTable();
        unvanData.clearDatabaseTable();
        otherUsersData.clearDatabaseTable();
        gpBolmeData.clearDatabaseTable();
        muhStokLokasyonData.clearDatabaseTable();
        ortakKisiData.clearDatabaseTable();
        ortakNotData.clearDatabaseTable();
        ortakNotKonuData.clearDatabaseTable();
        ortakIsKalemleriData.clearDatabaseTable();
        mobilGuzergahData.clearDatabaseTable();
        ortakKisiHareketData.clearDatabaseTable();
        sTownData.clearDatabaseTable();
        ortakKameraData.clearDatabaseTable();
        fidanAjaxTurData.clearDatabaseTable();
        sanatYapiData.clearDatabaseTable();
        yolBilgiData.clearDatabaseTable();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        System.exit(0);

    }


    void getBolgeMudurlukSeflik() {

        SOrgBirim_Data data = new SOrgBirim_Data(AnaMenuActivity.this);
        List<SOrgBirim> milli_park_bolge_mud = new ArrayList<SOrgBirim>();
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT * FROM S_ORG_BIRIM");
        try {
            org_birim_list = new ArrayList<SOrgBirim>();
            org_birim_list = data.loadFromQuery(sqlStr.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }
        for (SOrgBirim item : org_birim_list) {
            if (item.getKategori() != null) {
                if (String.valueOf(item.getKategori()).equals("5")) {

                    if (!item.getAdi().contains("DOĞA")) {
                        OrtakFunction.bolge_list.add(item);
                        OrtakFunction.bolge_list_string.add(item.getAdi());
                    } else {
                        milli_park_bolge_mud.add(item);
                    }

                } else if (String.valueOf(item.getKategori()).equals("6")) {
                    OrtakFunction.mudurluk_list.add(item);
                    OrtakFunction.mudurluk_list_string.add(item.getAdi());
                } else if (String.valueOf(item.getKategori()).equals("7")) {
                    OrtakFunction.seflik_list.add(item);
                    OrtakFunction.seflik_list_string.add(item.getAdi());
                }
                if (String.valueOf(item.getKategori()).equals("6") || String.valueOf(item.getKategori()).equals("9")) {
                    OrtakFunction.mudurluk_list_fid.add(item);
                    OrtakFunction.mudurluk_list_string_fid.add(item.getAdi());
                }

                if (String.valueOf(item.getKategori()).equals("7") || String.valueOf(item.getKategori()).equals("12")) {
                    OrtakFunction.seflik_list_fid.add(item);
                    OrtakFunction.seflik_list_string_fid.add(item.getAdi());
                }

                if (item.getUstId() != null && item.getUstId().toString().equalsIgnoreCase("1769")) {
                    OrtakFunction.genel_mud_mudurluk_list.add(item);
                    OrtakFunction.genel_mud_mudurluk_list_string.add(item.getAdi());
                }
                if (item.getId().toString().equalsIgnoreCase("1769")) {
                    OrtakFunction.genel_mud_bolge_list.add(item);
                    OrtakFunction.genel_mud_bolge_list_string.add(item.getAdi());
                }

                if (String.valueOf(item.getKategori()).equals("-1") || String.valueOf(item.getKategori()).equals("0") || String.valueOf(item.getKategori()).equals("1")
                        || String.valueOf(item.getKategori()).equals("2") || String.valueOf(item.getKategori()).equals("5")) {
                    OrtakFunction.teskilat_bolge_list.add(item);
                    OrtakFunction.teskilat_bolge_list_string.add(item.getAdi());
                }

            }
            Log.v("BIRIM", "=>" + item.getAdi());
        }


        Collections.sort(OrtakFunction.bolge_list_string);
        Collections.sort(OrtakFunction.bolge_list, new Comparator<SOrgBirim>() {
            @Override
            public int compare(final SOrgBirim object1, final SOrgBirim object2) {
                if (object1 != null && object2 != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                return 0;
            }
        });

        for (int i = 0; i < milli_park_bolge_mud.size(); i++) {
            OrtakFunction.bolge_list.add(milli_park_bolge_mud.get(i));
            OrtakFunction.bolge_list_string.add(milli_park_bolge_mud.get(i).getAdi());
        }

        getAltBirim();


        SCity_Data sCity_data = new SCity_Data(AnaMenuActivity.this);
        StringBuilder sqlStr1 = new StringBuilder();
        sqlStr1.append("SELECT * FROM S_IL");


        try {
            il_list = new ArrayList<SCity>();
            il_list = sCity_data.loadFromQuery(sqlStr1.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }

        OrtakFunction.il_list.add(null);
        OrtakFunction.il_list_string.add("");
        for (SCity item : il_list) {
            if (item.getAdi() != null) {
                OrtakFunction.il_list.add(item);
                OrtakFunction.il_list_string.add(item.getAdi());
                Log.v("il", "=>" + item.getAdi());

            }
        }


        STown_Data sTown_data = new STown_Data(AnaMenuActivity.this);
        StringBuilder sqlStr2 = new StringBuilder();
        sqlStr2.append("SELECT * FROM S_ILCE");
        try {
            ilce_list = new ArrayList<STown>();
            ilce_list = sTown_data.loadFromQuery(sqlStr2.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }


        SKoyBelde_Data sKoyBelde_data = new SKoyBelde_Data(AnaMenuActivity.this);
        StringBuilder sqlStr3 = new StringBuilder();
        sqlStr3.append("SELECT * FROM S_KOY_BELDE");
        try {
            koy_list = new ArrayList<SKoyBelde>();
            koy_list = sKoyBelde_data.loadFromQuery(sqlStr3.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }


        OrtakFunction.ilce_list.add(null);
        OrtakFunction.ilce_list_string.add("");
        for (STown item : ilce_list) {
            if (item.getAdi() != null) {
                OrtakFunction.ilce_list.add(item);
                OrtakFunction.ilce_list_string.add(item.getAdi());
                Log.v("ilçe", "=>" + item.getAdi());

            }
        }


        OrtakFunction.koy_list.add(null);
        OrtakFunction.koy_list_string.add("");
        for (SKoyBelde item : koy_list) {
            if (item.getKoyAdi() != null) {
                OrtakFunction.koy_list.add(item);
                OrtakFunction.koy_list_string.add(item.getKoyAdi());
                Log.v("köy", "=>" + item.getKoyAdi());

            }
        }

        Log.v("köy_size", "=>" + koy_list.size());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Collections.sort(OrtakFunction.ilce_list_string);
        Collections.sort(OrtakFunction.ilce_list, new Comparator<STown>() {
            @Override
            public int compare(final STown object1, final STown object2) {
                if (object1 != null && object2 != null && object2.getAdi() != null && object1.getAdi() != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                Log.v("köy_sort", "=>" + koy_list.size());

                return 0;

            }
        });

        Collections.sort(OrtakFunction.il_list_string, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(OrtakFunction.il_list, new Comparator<SCity>() {
            @Override
            public int compare(final SCity object1, final SCity object2) {
                if (object1 != null && object2 != null && object2.getAdi() != null && object1.getAdi() != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                Log.v("köy_sort2 ", "=>" + koy_list.size());

                return 0;
            }
        });

    }


    void getAltBirim() {

        SOrgBirim_Data data = new SOrgBirim_Data(AnaMenuActivity.this);
        List<SOrgBirim> milli_park_bolge_mud = new ArrayList<SOrgBirim>();
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT * FROM S_ORG_BIRIM");
        try {
            org_birim_list = new ArrayList<SOrgBirim>();
            org_birim_list = data.loadFromQuery(sqlStr.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }
        for (SOrgBirim item : org_birim_list) {
            //  if (item.getKategori() != null) {

            for (SOrgBirim i : OrtakFunction.genel_mud_mudurluk_list) {
                if (i.getId().toString().equalsIgnoreCase(item.getUstId().toString())) {
                    OrtakFunction.genel_mud_seflik_list.add(item);
                    OrtakFunction.genel_mud_seflik_list_string.add(item.getAdi());
                }
            }


            for (SOrgBirim i : OrtakFunction.teskilat_bolge_list) {
                if (i.getId().toString().equalsIgnoreCase(item.getUstId().toString())) {
                    OrtakFunction.teskilat_mudurluk_list.add(item);
                    OrtakFunction.teskilat_mudurluk_list_string.add(item.getAdi());
                }
            }

            Log.v("BIRIM", "=>" + item.getAdi());
        }


        getAltBirim2();

    }


    void getAltBirim2() {

        SOrgBirim_Data data = new SOrgBirim_Data(AnaMenuActivity.this);
        List<SOrgBirim> milli_park_bolge_mud = new ArrayList<SOrgBirim>();
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append("SELECT * FROM S_ORG_BIRIM");
        try {
            org_birim_list = new ArrayList<SOrgBirim>();
            org_birim_list = data.loadFromQuery(sqlStr.toString());
        } catch (OrbisDefaultException e) {
            e.printStackTrace();
        }
        for (SOrgBirim item : org_birim_list) {
            //if (item.getKategori() != null) {

            for (SOrgBirim i : OrtakFunction.teskilat_mudurluk_list) {
                if (i.getId().toString().equalsIgnoreCase(item.getUstId().toString())) {
                    OrtakFunction.teskilat_seflik_list.add(item);
                    OrtakFunction.teskilat_seflik_list_string.add(item.getAdi());
                }
            }
            //    }
            Log.v("BIRIM", "=>" + item.getAdi());
        }

        if (OrtakFunction.genel_mud_seflik_list != null && OrtakFunction.genel_mud_seflik_list.size() > 0 && OrtakFunction.genel_mud_seflik_list.get(0) != null)
            OrtakFunction.genel_mud_seflik_list.add(0, null);
        if (OrtakFunction.genel_mud_seflik_list_string != null && OrtakFunction.genel_mud_seflik_list_string.size() > 0 && !OrtakFunction.genel_mud_seflik_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.genel_mud_seflik_list_string.add(0, "");

        if (OrtakFunction.teskilat_mudurluk_list != null && OrtakFunction.teskilat_mudurluk_list.size() > 0 && OrtakFunction.teskilat_mudurluk_list.get(0) != null)
            OrtakFunction.teskilat_mudurluk_list.add(0, null);
        if (OrtakFunction.teskilat_mudurluk_list_string != null && OrtakFunction.teskilat_mudurluk_list_string.size() > 0 && !OrtakFunction.teskilat_mudurluk_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.teskilat_mudurluk_list_string.add(0, "");

        if (OrtakFunction.bolge_list != null && OrtakFunction.bolge_list.size() > 0 && OrtakFunction.bolge_list.get(0) != null)
            OrtakFunction.bolge_list.add(0, null);
        if (OrtakFunction.bolge_list_string != null && OrtakFunction.bolge_list_string.size() > 0 && !OrtakFunction.bolge_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.bolge_list_string.add(0, "");

        if (OrtakFunction.genel_mud_bolge_list != null && OrtakFunction.genel_mud_bolge_list.size() > 0 && OrtakFunction.genel_mud_bolge_list.get(0) != null)
            OrtakFunction.genel_mud_bolge_list.add(0, null);
        if (OrtakFunction.genel_mud_bolge_list_string != null && OrtakFunction.genel_mud_bolge_list_string.size() > 0 && !OrtakFunction.genel_mud_bolge_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.genel_mud_bolge_list_string.add(0, "");

        if (OrtakFunction.genel_mud_mudurluk_list != null && OrtakFunction.genel_mud_mudurluk_list.size() > 0 && OrtakFunction.genel_mud_mudurluk_list.get(0) != null)
            OrtakFunction.genel_mud_mudurluk_list.add(0, null);
        if (OrtakFunction.genel_mud_mudurluk_list_string != null && OrtakFunction.genel_mud_mudurluk_list_string.size() > 0 && !OrtakFunction.genel_mud_mudurluk_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.genel_mud_mudurluk_list_string.add(0, "");

        if (OrtakFunction.teskilat_bolge_list != null && OrtakFunction.teskilat_bolge_list.size() > 0 && OrtakFunction.teskilat_bolge_list.get(0) != null)
            OrtakFunction.teskilat_bolge_list.add(0, null);
        if (OrtakFunction.teskilat_bolge_list_string != null && OrtakFunction.teskilat_bolge_list_string.size() > 0 && !OrtakFunction.teskilat_bolge_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.teskilat_bolge_list_string.add(0, "");

        if (OrtakFunction.teskilat_seflik_list != null && OrtakFunction.teskilat_seflik_list.size() > 0 && OrtakFunction.teskilat_seflik_list.get(0) != null)
            OrtakFunction.teskilat_seflik_list.add(0, null);
        if (OrtakFunction.teskilat_seflik_list_string != null && OrtakFunction.teskilat_seflik_list_string.size() > 0 && !OrtakFunction.teskilat_seflik_list_string.get(0).equalsIgnoreCase(""))
            OrtakFunction.teskilat_seflik_list_string.add(0, "");

        Collections.sort(OrtakFunction.bolge_list_string);
        Collections.sort(OrtakFunction.mudurluk_list_string);
        Collections.sort(OrtakFunction.seflik_list_string);
        Collections.sort(OrtakFunction.genel_mud_bolge_list_string);
        Collections.sort(OrtakFunction.genel_mud_mudurluk_list_string);
        Collections.sort(OrtakFunction.genel_mud_seflik_list_string);

        Collections.sort(OrtakFunction.teskilat_seflik_list, new Comparator<SOrgBirim>() {
            @Override
            public int compare(final SOrgBirim object1, final SOrgBirim object2) {
                if (object1 != null && object2 != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                return 0;
            }
        });


        Collections.sort(OrtakFunction.teskilat_bolge_list_string);
        Collections.sort(OrtakFunction.teskilat_bolge_list, new Comparator<SOrgBirim>() {
            @Override
            public int compare(final SOrgBirim object1, final SOrgBirim object2) {
                if (object1 != null && object2 != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                return 0;
            }
        });

        Collections.sort(OrtakFunction.teskilat_mudurluk_list_string);
        Collections.sort(OrtakFunction.teskilat_mudurluk_list, new Comparator<SOrgBirim>() {
            @Override
            public int compare(final SOrgBirim object1, final SOrgBirim object2) {
                if (object1 != null && object2 != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                return 0;
            }
        });


        Collections.sort(OrtakFunction.genel_mud_mudurluk_list_string);
        Collections.sort(OrtakFunction.genel_mud_mudurluk_list, new Comparator<SOrgBirim>() {
            @Override
            public int compare(final SOrgBirim object1, final SOrgBirim object2) {
                if (object1 != null && object2 != null)
                    return object1.getAdi().compareTo(object2.getAdi());
                return 0;
            }
        });


    }


    void filtre_spinner(final List<String> gelenBolgeListString, final List<SOrgBirim> gelenBolgeList, final List<SOrgBirim> gelenMudList, final List<SOrgBirim> gelenSeflikList) {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, gelenBolgeListString);
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


                        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
                        dataAdapter_mudurluk.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        mudurluk_spinner.setAdapter(dataAdapter_mudurluk);


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
                        dataAdapter_seflik.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        seflik_spinner.setAdapter(dataAdapter_seflik);
                        seflik_spinner.setSelection(0);


                        if (!localDataManager.getValues(getApplicationContext(), "bolgeId").toString().equalsIgnoreCase(String.valueOf(selected_bolge_index))) {
                            mudurluk_spinner.setSelection(0);
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
                localDataManager.setSharedPreference(getApplicationContext(), "bolgeId", String.valueOf(selected_bolge_index));

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


        ArrayAdapter<String> dataAdapter_mudurluk = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_mudurluk);
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


                        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
                        dataAdapter_seflik.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        seflik_spinner.setAdapter(dataAdapter_seflik);

                        if (!localDataManager.getValues(getApplicationContext(), "mudurlukId").toString().equalsIgnoreCase(String.valueOf(selected_mudurluk_index))) {
                            seflik_spinner.setSelection(0);
                        } else {
                            selected_seflik_index = shared_seflik_index;
                            seflik_spinner.setSelection(selected_seflik_index);
                        }

                    } else {
                        secili_mudurluk_id = -1L;
                        secili_seflik_id = -1L;
                    }
                }
                localDataManager.setSharedPreference(getApplicationContext(), "mudurlukId", String.valueOf(selected_mudurluk_index));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_seflik = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_seflik);
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
                localDataManager.setSharedPreference(getApplicationContext(), "seflikId", String.valueOf(selected_seflik_index));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    void filtre_spinner_il_ilce_koy() {
        ArrayAdapter<String> dataAdapter_bolge = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, OrtakFunction.il_list_string);
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


                        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
                        dataAdapter_ilce.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        ilce_spinner.setAdapter(dataAdapter_ilce);


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
                        dataAdapter_koy.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        koy_spinner.setAdapter(dataAdapter_koy);
                        koy_spinner.setSelection(0);


                        if (!localDataManager.getValues(getApplicationContext(), "ilId").toString().equalsIgnoreCase(String.valueOf(selected_il_index))) {
                            ilce_spinner.setSelection(0);
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
                localDataManager.setSharedPreference(getApplicationContext(), "ilId", String.valueOf(selected_il_index));

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

        ArrayAdapter<String> dataAdapter_ilce = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_ilce);
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


                        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
                        dataAdapter_koy.setDropDownViewResource(R.layout.mr_simple_spinner_dropdown_item);
                        koy_spinner.setAdapter(dataAdapter_koy);

                        if (!localDataManager.getValues(getApplicationContext(), "ilceId").toString().equalsIgnoreCase(String.valueOf(selected_ilce_index))) {
                            koy_spinner.setSelection(0);
                        } else {
                            selected_koy_index = shared_koy_index;
                            koy_spinner.setSelection(selected_koy_index);
                        }
                    } else {
                        secili_ilce_id = -1L;
                        secili_koy_id = -1L;
                    }
                }
                localDataManager.setSharedPreference(getApplicationContext(), "ilceId", String.valueOf(selected_ilce_index));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter_koy = new ArrayAdapter<String>(AnaMenuActivity.this, android.R.layout.simple_spinner_item, item_source_str_koy);
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
                localDataManager.setSharedPreference(getApplicationContext(), "koyId", String.valueOf(selected_koy_index));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (pd.isShowing())
            pd.dismiss();


    }


    void radioGrupChange(RadioGroup group, int checkedId) {
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
            linearLayout_iki.setVisibility(View.VISIBLE);
            linearLayout_bir.setVisibility(View.VISIBLE);
        } else {
            expandableLayout.collapse();

            linearLayout_iki.setVisibility(View.GONE);
            linearLayout_bir.setVisibility(View.GONE);

        }
    }

}
