package id.co.leskuteacher.data.remote.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.WaitingOrder;
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
    @POST("auth/teacher/login/")
    Maybe<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("app_firebase_id") String regid
    );

    @GET ("users/{id}")
    Maybe<JsonObject> forgotPassword (@Path("id") String id);

    @GET ("teacher/order/waiting")
    Maybe<List<WaitingOrder>> getWaitingOrderList ();
}
