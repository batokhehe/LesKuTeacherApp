package com.lescepat.teacher.data.remote;
import com.google.gson.JsonObject;

import java.util.List;
import com.lescepat.teacher.data.remote.contracts.OrderClass;
import com.lescepat.teacher.model.FinishedOrder;
import com.lescepat.teacher.model.UpcomingOrder;
import com.lescepat.teacher.model.WaitingOrder;
import com.lescepat.teacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class OrderAPI extends BaseAPI implements OrderClass {
    @Override
    public Maybe<List<WaitingOrder>> getWaitingOrderList() {
        return app.mAPIService.getWaitingOrderList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> acceptOrder(int id) {
        return app.mAPIService.acceptOrder(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> declineOrder(int id) {
        return app.mAPIService.declineOrder(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<UpcomingOrder>> getUpcomingOrderList() {
        return app.mAPIService.getUpcomingOrderList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> confirmOrder(int id) {
        return app.mAPIService.confirmOrder(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> rescheduleOrder(int id) {
        return app.mAPIService.rescheduleOrder(id).retry(1).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<FinishedOrder>> getFinishedOrderList() {
        return app.mAPIService.getFinishedOrderList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }
}
