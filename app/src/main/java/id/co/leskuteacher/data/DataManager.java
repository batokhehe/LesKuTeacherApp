package id.co.leskuteacher.data;

import com.google.gson.JsonObject;

import id.co.leskuteacher.data.remote.AuthAPI;
import io.reactivex.Maybe;

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

    @Override
    public Maybe<JsonObject> login(String email, String password, String regid)
    {
        return sAuthAPI.login(email, password, regid);
    }

    @Override
    public Maybe<JsonObject> forgotPassword (String id) { return sAuthAPI.forgotPassword(id);}
}
