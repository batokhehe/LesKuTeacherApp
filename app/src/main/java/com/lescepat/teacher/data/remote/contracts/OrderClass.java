package com.lescepat.teacher.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.model.UpcomingOrder;
import com.lescepat.teacher.model.WaitingOrder;
import io.reactivex.Maybe;

public interface OrderClass {
    Maybe<List<WaitingOrder>> getWaitingOrderList();

    Maybe<JsonObject> acceptOrder(int id);

    Maybe<JsonObject> declineOrder(int id);

    Maybe<List<UpcomingOrder>> getUpcomingOrderList();

    Maybe<JsonObject> confirmOrder(int id);

    Maybe<JsonObject> rescheduleOrder(int id);

    Maybe<List<FinishedOrder>> getFinishedOrderList();
}
