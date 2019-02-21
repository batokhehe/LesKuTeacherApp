package id.co.leskuteacher.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.data.remote.contracts.PresenceClass;
import id.co.leskuteacher.model.Presence;
import id.co.leskuteacher.utils.constants.K;
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
