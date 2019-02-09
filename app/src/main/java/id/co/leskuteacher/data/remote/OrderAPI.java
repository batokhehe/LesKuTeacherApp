package id.co.leskuteacher.data.remote;
import java.util.List;
import id.co.leskuteacher.data.remote.contracts.OrderClass;
import id.co.leskuteacher.model.WaitingOrder;
import id.co.leskuteacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class OrderAPI extends BaseAPI implements OrderClass {
    @Override
    public Maybe<List<WaitingOrder>> getWaitingOrderList() {
        return app.mAPIService.getWaitingOrderList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }
}
