package com.lescepat.teacher.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import com.lescepat.teacher.data.local.contracts.CacheContract;
import com.lescepat.teacher.data.local.contracts.RAGEContract;
import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class FinishedOrderStorage implements RAGEContract<FinishedOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<FinishedOrder>> getList() {
        List<FinishedOrder> waitingOrders = isCacheValid() ? Hawk.get(K.DONE_ORDER_LIST, new ArrayList<FinishedOrder>()) : null;
        return waitingOrders == null ? Maybe.<List<FinishedOrder>>empty() : Maybe.just(waitingOrders).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<FinishedOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<FinishedOrder> objs) {

    }

    @Override
    public void add(FinishedOrder obj) {

    }

    @Override
    public void edit(FinishedOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
