package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.data.local.WaitingOrderStorage;
import id.co.leskuteacher.data.remote.AuthAPI;
import id.co.leskuteacher.data.remote.OrderAPI;
import id.co.leskuteacher.model.WaitingOrder;
import io.reactivex.Maybe;
import io.reactivex.functions.Consumer;

public class DataManager implements DataManagerType
{
    private static DataManager dm;

    public static DataManager can () // or use, or call (?)
    {
        if (dm == null)
        {
            dm = new DataManager();
        }
        return dm;
    }

    private static AuthAPI     sAuthAPI     = new AuthAPI();
    private static OrderAPI sOrderAPI     = new OrderAPI();

    private static WaitingOrderStorage sWaitingStorage = new WaitingOrderStorage();

    @Override
    public Maybe<JsonObject> login(String email, String password, String regid)
    {
        return sAuthAPI.login(email, password, regid);
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id) { return sAuthAPI.forgotPassword(id);}

    //WAITING ORDER
    @Override
    public Maybe<List<WaitingOrder>> getWaitingOrderList ()
    {
        return Maybe.concat(sWaitingStorage.getList(), sOrderAPI.getWaitingOrderList().doOnSuccess(new Consumer<List<WaitingOrder>>()
        {
            @Override
            public void accept (List<WaitingOrder> waitingOrders) throws Exception
            {
                sWaitingStorage.addAll(waitingOrders);
            }
        })).firstElement();
    }
}
