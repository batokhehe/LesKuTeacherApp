package com.lescepat.teacher.data.remote.contracts;

import com.google.gson.JsonObject;

import io.reactivex.Maybe;

public interface Authentication
{
    Maybe<JsonObject> login(String email, String password, String regid);

    void logout();

    Maybe<JsonObject> forgotPassword(String id);
}
