package ToolLayer;

import java.util.List;

import EntityLayer.Kadastro.KdmDosya;
import EntityLayer.Oduh.BalOrmani;
import EntityLayer.Oduh.MesireYeri;
import EntityLayer.Oduh.UretimPaket;
import EntityLayer.Ozm.SucTutanagi;
import EntityLayer.SendParametersForServer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RefrofitRestApi {

    @POST("rest/OdunDisiRS/oduhDepoBalOrmaniService")
    Call<List<BalOrmani>>  getBalOrmaniService(@Body SendParametersForServer parameter);

    @POST("rest/UrunDisiRS/oduhDepoMesireService")
    Call<List<MesireYeri>>  getOduhDepoMesireService(@Body SendParametersForServer parameter);

    @POST("rest/OdunDisiRS/oduhDepoUretimPaketService")
    Call<List<UretimPaket>>  getOduhDepoUretimPaketService(@Body SendParametersForServer parameter);

    @POST("rest/KadastroRS/kdmDepoDosyaListForMobil")
    Call<List<KdmDosya>>  getKdmDepoDosyaListForMobil(@Body SendParametersForServer parameter);

    @POST("rest/SaglikDurumuRS/ozmDepoSucTutanagiForMobil")
    Call<List<SucTutanagi>>  getOzmDepoSucTutanagiForMobil(@Body SendParametersForServer parameter);





}
