package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import io.reactivex.Maybe;

public interface DataManagerType {
    Maybe<JsonObject> login(String email, String password, String regid);

    Maybe<JsonObject> forgotPassword(String id);
}
