package com.bumble.retrofit;


import com.bumble.model.FuguPutUserDetailsResponse;
import com.bumble.model.UserInfoModel;
import com.bumble.model.promotional.PromotionResponse;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * ApiInterface
 */
public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/users/userLogin")
    Call<FuguPutUserDetailsResponse> putUserDetails(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/api/reseller/putUserDetails")
    Call<FuguPutUserDetailsResponse> putUserDetailsReseller(@FieldMap(encoded = false) Map<String, Object> map);


    @FormUrlEncoded
    @POST("/api/users/userlogout")
    Call<CommonResponse> logOut(@FieldMap Map<String, Object> map);


//    @FormUrlEncoded
//    @POST("/api/server/logException")
//    Call<CommonResponse> sendError(@FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST("/api/server/logException")
//    Call<CommonResponse> sendAckToServer(@FieldMap Map<String, Object> map);


    @GET("/requestCountryCodeGeoIP2")
    Call<UserInfoModel> getUserInfo();



    @FormUrlEncoded
    @POST("/api/users/customerLogin")
    Call<CommonResponse> customerLogin(@FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("/api/campaign/getCampaigns")
    Call<PromotionResponse> fetchMobilePush(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/api/campaign/clearCampaign")
    Call<CommonResponse> clearMobilePush(@FieldMap Map<String, Object> map);


    @GET()
    Call<IpResponse> showTheStations(@Url String url);
}
