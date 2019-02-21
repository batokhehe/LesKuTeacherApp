package id.co.leskuteacher.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.FinishedOrder;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.model.WaitingOrder;
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
