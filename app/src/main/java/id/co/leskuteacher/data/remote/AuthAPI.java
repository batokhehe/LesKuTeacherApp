package id.co.leskuteacher.data.remote;

import com.google.gson.JsonObject;

import id.co.leskuteacher.data.remote.contracts.Authentication;
import id.co.leskuteacher.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class AuthAPI extends BaseAPI implements Authentication
{
    @Override
    public Maybe<JsonObject> login (String email, String password, String regid)
    {
        // TODO: define your own API URL
        return app.mAPIService.login(email, password, regid)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }

    @Override
    public void logout ()
    {
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id)
    {
        // TODO: define your own API URL
        return app.mAPIService.forgotPassword(id)
                              .retry(K.MAX_RETRIES)
                              .subscribeOn(Schedulers.io());

        // TODO: 7/28/17 find new helper for retryWhen ( rx 2.1.2 )
    }
}
