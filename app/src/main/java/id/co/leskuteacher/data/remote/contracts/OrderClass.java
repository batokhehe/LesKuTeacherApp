package id.co.leskuteacher.data.remote.contracts;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.WaitingOrder;
import io.reactivex.Maybe;

public interface OrderClass {
    Maybe<List<WaitingOrder>> getWaitingOrderList();

    Maybe<JsonObject> acceptOrder(int id);
}
