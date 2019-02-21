package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.FinishedOrder;
import id.co.leskuteacher.model.Presence;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.model.WaitingOrder;
import io.reactivex.Maybe;

public interface DataManagerType {
    Maybe<JsonObject> login(String email, String password, String regid);

    Maybe<JsonObject> forgotPassword(String id);

    //WAITING ORDER
    Maybe<List<WaitingOrder>> getWaitingOrderList();

    Maybe<JsonObject> acceptOrder(int id);

    Maybe<JsonObject> declineOrder(int id);

    //UPCOMING ORDER
    Maybe<List<UpcomingOrder>> getUpcomingOrderList();

    Maybe<JsonObject> confirmOrder(int id);

    Maybe<JsonObject> rescheduleOrder(int id);

    //DONE ORDER
    Maybe<List<FinishedOrder>> getDoneOrderList();

    //PRESENCE
    Maybe<List<Presence>> getPresenceList();

    Maybe<JsonObject> confirmPresence(int id, String uniqueCode);
}
