package com.lescepat.teacher.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.model.Presence;
import io.reactivex.Maybe;

public interface PresenceClass {
    Maybe<List<Presence>> getPresenceList();

    Maybe<JsonObject> confirmPresence(int id, String uniqueCode);
}
