package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.leskuteacher.data.local.FinishedOrderStorage;
import id.co.leskuteacher.data.local.PresenceStorage;
import id.co.leskuteacher.data.local.UpcomingOrderStorage;
import id.co.leskuteacher.data.local.WaitingOrderStorage;
import id.co.leskuteacher.data.remote.AuthAPI;
import id.co.leskuteacher.data.remote.OrderAPI;
import id.co.leskuteacher.data.remote.PresenceAPI;
import id.co.leskuteacher.model.FinishedOrder;
import id.co.leskuteacher.model.Presence;
import id.co.leskuteacher.model.UpcomingOrder;
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
    private static PresenceAPI sPresenceAPI     = new PresenceAPI();

    private static WaitingOrderStorage sWaitingStorage = new WaitingOrderStorage();
    private static UpcomingOrderStorage sUpcomingStorage = new UpcomingOrderStorage();
    private static FinishedOrderStorage sFinishedOrderStorage = new FinishedOrderStorage();
    private static PresenceStorage sPresenceStorage = new PresenceStorage();

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

    @Override
    public Maybe<JsonObject> acceptOrder(int id)
    {
        return sOrderAPI.acceptOrder(id);
    }

    @Override
    public Maybe<JsonObject> declineOrder(int id)
    {
        return sOrderAPI.declineOrder(id);
    }


    //UPCOMING ORDER
    @Override
    public Maybe<List<UpcomingOrder>> getUpcomingOrderList ()
    {
        return Maybe.concat(sUpcomingStorage.getList(), sOrderAPI.getUpcomingOrderList().doOnSuccess(new Consumer<List<UpcomingOrder>>()
        {
            @Override
            public void accept (List<UpcomingOrder> upcomingOrders) throws Exception
            {
                sUpcomingStorage.addAll(upcomingOrders);
            }
        })).firstElement();
    }

    @Override
    public Maybe<JsonObject> confirmOrder(int id)
    {
        return sOrderAPI.confirmOrder(id);
    }

    @Override
    public Maybe<JsonObject> rescheduleOrder(int id)
    {
        return sOrderAPI.rescheduleOrder(id);
    }

    //DONE ORDER
    @Override
    public Maybe<List<FinishedOrder>> getDoneOrderList()
    {
        return Maybe.concat(sFinishedOrderStorage.getList(), sOrderAPI.getFinishedOrderList().doOnSuccess(new Consumer<List<FinishedOrder>>()
        {
            @Override
            public void accept (List<FinishedOrder> finishedOrders) throws Exception
            {
                sFinishedOrderStorage.addAll(finishedOrders);
            }
        })).firstElement();
    }

    //PRESENCE
    @Override
    public Maybe<List<Presence>> getPresenceList()
    {
        return Maybe.concat(sPresenceStorage.getList(), sPresenceAPI.getPresenceList().doOnSuccess(new Consumer<List<Presence>>()
        {
            @Override
            public void accept (List<Presence> presences) throws Exception
            {
                sPresenceStorage.addAll(presences);
            }
        })).firstElement();
    }

    @Override
    public Maybe<JsonObject> confirmPresence(int id, String uniqueCode)
    {
        return sPresenceAPI.confirmPresence(id, uniqueCode);
    }
}
