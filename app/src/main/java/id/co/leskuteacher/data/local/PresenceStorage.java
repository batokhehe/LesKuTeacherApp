package id.co.leskuteacher.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.leskuteacher.data.local.contracts.CacheContract;
import id.co.leskuteacher.data.local.contracts.RAGEContract;
import id.co.leskuteacher.model.Presence;
import id.co.leskuteacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class PresenceStorage implements RAGEContract<Presence, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<Presence>> getList() {
        List<Presence> presences = isCacheValid() ? Hawk.get(K.PRESENCE_LIST, new ArrayList<Presence>()) : null;
        return presences == null ? Maybe.<List<Presence>>empty() : Maybe.just(presences).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Presence> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<Presence> objs) {

    }

    @Override
    public void add(Presence obj) {

    }

    @Override
    public void edit(Presence obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
