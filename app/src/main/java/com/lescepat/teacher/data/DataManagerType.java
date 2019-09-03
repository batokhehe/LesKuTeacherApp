package com.lescepat.teacher.data;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.model.Presence;
import com.lescepat.teacher.model.UpcomingOrder;
import com.lescepat.teacher.model.WaitingOrder;
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
