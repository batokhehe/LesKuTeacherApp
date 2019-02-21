package id.co.leskuteacher.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.Presence;
import io.reactivex.Maybe;

public interface PresenceClass {
    Maybe<List<Presence>> getPresenceList();

    Maybe<JsonObject> confirmPresence(int id, String uniqueCode);
}
