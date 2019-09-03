package com.lescepat.teacher.data.remote.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.model.Presence;
import com.lescepat.teacher.model.UpcomingOrder;
import com.lescepat.teacher.model.WaitingOrder;
import io.reactivex.Maybe;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface LeskuAPIService
{
    @FormUrlEncoded
    @GET
        //dynamic URL
    Maybe<JsonObject> dynamicRequest(@Url String url);

    /*
    * below are dummy URLs. Please change it into your API endpoints
    * TODO: replace below URLs with your own
    */

    @FormUrlEncoded
    @POST("auth/teacher/login")
    Maybe<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("app_firebase_id") String regid
    );

    @GET ("users/{id}")
    Maybe<JsonObject> forgotPassword (@Path("id") String id);

    @GET ("teacher/order/waiting")
    Maybe<List<WaitingOrder>> getWaitingOrderList ();

    @FormUrlEncoded
    @POST ("teacher/order/accept_order")
    Maybe<JsonObject> acceptOrder (
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST ("teacher/order/decline_order")
    Maybe<JsonObject> declineOrder (
            @Field("id") int id
    );

    @GET ("teacher/order/upcoming")
    Maybe<List<UpcomingOrder>> getUpcomingOrderList ();

    @FormUrlEncoded
    @POST ("teacher/schedule/confirm_schedule")
    Maybe<JsonObject> confirmOrder (
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST ("teacher/schedule/reschedule")
    Maybe<JsonObject> rescheduleOrder (
            @Field("id") int id
    );

    @GET ("teacher/order/finished")
    Maybe<List<FinishedOrder>> getFinishedOrderList ();

    //PRESENCE
    @GET ("teacher/presence")
    Maybe<List<Presence>> getPresenceList ();

    @FormUrlEncoded
    @POST ("teacher/presence/confirm")
    Maybe<JsonObject> confirmPresence (
            @Field("id") int id,
            @Field("unique_code") String uniqueCode
    );
}
