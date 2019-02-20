package id.co.leskuteacher.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.leskuteacher.data.local.contracts.CacheContract;
import id.co.leskuteacher.data.local.contracts.RAGEContract;
import id.co.leskuteacher.model.UpcomingOrder;
import id.co.leskuteacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class UpcomingOrderStorage implements RAGEContract<UpcomingOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<UpcomingOrder>> getList() {
        List<UpcomingOrder> upcomingOrders = isCacheValid() ? Hawk.get(K.WAITING_ORDER_LIST, new ArrayList<UpcomingOrder>()) : null;
        return upcomingOrders == null ? Maybe.<List<UpcomingOrder>>empty() : Maybe.just(upcomingOrders).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<UpcomingOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<UpcomingOrder> objs) {

    }

    @Override
    public void add(UpcomingOrder obj) {

    }

    @Override
    public void edit(UpcomingOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
