package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.model.WaitingOrder;
import io.reactivex.Maybe;

public interface DataManagerType {
    Maybe<JsonObject> login(String email, String password, String regid);

    Maybe<JsonObject> forgotPassword(String id);

    //WAITING ORDER
    Maybe<List<WaitingOrder>> getWaitingOrderList();

    Maybe<JsonObject> acceptOrder(int id);
}
