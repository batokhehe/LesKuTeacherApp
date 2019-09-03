package com.lescepat.teacher.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.data.remote.contracts.PresenceClass;
import com.lescepat.teacher.model.Presence;
import com.lescepat.teacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class PresenceAPI extends BaseAPI implements PresenceClass {
    @Override
    public Maybe<List<Presence>> getPresenceList() {
        return app.mAPIService.getPresenceList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> confirmPresence(int id, String uniqueCode) {
        return app.mAPIService.confirmPresence(id, uniqueCode).retry(1).subscribeOn(Schedulers.io());
    }

}
