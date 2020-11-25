package ToolLayer;

import java.util.List;

import EntityLayer.Agaclandirma.AgacProje;
import EntityLayer.Agaclandirma.ToprakProje;
import EntityLayer.Amenajman.Amenajman;
import EntityLayer.BilgiSistemleri.HaritaArsiv;
import EntityLayer.IsletmePazarlama.Damga;
import EntityLayer.IsletmePazarlama.Odenek;
import EntityLayer.IsletmePazarlama.Satis;
import EntityLayer.IsletmePazarlama.Uretim;
import EntityLayer.Kadastro.KdmDosya;
import EntityLayer.Kadastro.KdmGerceklesme;
import EntityLayer.Muhasebe.MaliHesaplar;
import EntityLayer.Oduh.BalOrmani;
import EntityLayer.Oduh.MesireYeri;
import EntityLayer.Oduh.UretimPaket;
import EntityLayer.OrmanIdaresi.OrmanIdaresi;
import EntityLayer.Oym.Yangin;
import EntityLayer.Ozm.SucTutanagi;
import EntityLayer.SendParametersForServer;
import EntityLayer.Strateji.TefKonular;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RefrofitRestApi {

    @POST("rest/OdunDisiRS/oduhDepoBalOrmaniService")
    Call<List<BalOrmani>>  getBalOrmaniService(@Body SendParametersForServer parameter);

    @POST("rest/UrunDisiRS/oduhDepoMesireService")
    Call<List<MesireYeri>>  getOduhDepoMesireService(@Body SendParametersForServer parameter);

    @POST("rest/OdunDisiRS/oduhDepoUretimPaketService")
    Call<List<UretimPaket>>  getOduhDepoUretimPaketService(@Body SendParametersForServer parameter);

    @POST("rest/StratejiRS/strDepoTefKonularForMobilService")
    Call<List<TefKonular>>  getStrDepoTefKonularForMobilService(@Body SendParametersForServer parameter);

    @POST("rest/KadastroRS/kdmDepoDosyaListForMobil")
    Call<List<KdmDosya>>  getKdmDepoDosyaListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/KadastroRS/kdmProgramGerceklesmeListForMobil")
    Call<List<KdmGerceklesme>>  getKdmProgramGerceklesmeListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/SaglikDurumuRS/ozmDepoSucTutanagiForMobil")
    Call<List<SucTutanagi>>  getOzmDepoSucTutanagiForMobil(@Body SendParametersForServer parameter);

    @POST("rest/IsletmePazarlamaRS/mobilDamga")
    Call<List<Damga>>  getMobilDamga(@Body SendParametersForServer parameter);

    @POST("rest/IsletmePazarlamaRS/mobilUretim")
    Call<List<Uretim>>  getMobilUretim(@Body SendParametersForServer parameter);

    @POST("rest/IsletmePazarlamaRS/mobilSatis")
    Call<List<Satis>>  getMobilSatis(@Body SendParametersForServer parameter);

    @POST("rest/IsletmePazarlamaRS/mobilOdenek")
    Call<List<Odenek>>  getMobilOdenek(@Body SendParametersForServer parameter);

    @POST("rest/AgaclandirmaRS/agacProjeGerceklesmeListForMobil")
    Call<List<AgacProje>>  getAgacProjeGerceklesmeListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/AgaclandirmaRS/toprakProjeGerceklesmeListForMobil")
    Call<List<ToprakProje>>  getToprakProjeGerceklesmeListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/muhasebeRS/haritaArsivDepoDosyaListForMobil")
    Call<List<HaritaArsiv>>  getHaritaArsivDepoDosyaListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/muhasebeRS/getOzetHesaplar")
    Call<List<MaliHesaplar>>  getOzetHesaplar(@Body SendParametersForServer parameter);

    @POST("rest/ormanYanginlariRS/oymDepoYanginForMobil")
    Call<List<Yangin>>  getOymDepoYanginForMobil(@Body SendParametersForServer parameter);


    @POST("rest/GeoPortalRS/getBirimBazliAlan")
    Call<List<OrmanIdaresi>>  getBirimBazliAlan(@Body SendParametersForServer parameter);




    @POST("rest/GeoPortalRS/-------")
    Call<List<Amenajman>>  getAmenajman(@Body SendParametersForServer parameter);  ////// SERVİSİ EKLENMEDİİİİ

}
