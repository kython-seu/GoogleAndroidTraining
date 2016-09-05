package trendmicro.okhttpntlm;

import android.os.Build;
import android.util.Log;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;


/**
 * Created by IBM on 2016/9/3.
 */
public class NTLMAuthenticator implements Authenticator {

    private static final String TAG = "NTLMAuthenticator";
    final NTLMEngineImpl engine = new NTLMEngineImpl();
    private final String domain;
    private final String username;
    private final String password;
    private final String ntlmMsg1;

    public NTLMAuthenticator(String username, String password, String domain) {
        this.domain = domain;
        this.username = username;
        this.password = password;
        String localNtlmMsg1 = null;
        try {
            localNtlmMsg1 = engine.generateType1Msg(domain, Build.DEVICE);
            Log.i(TAG, "NTLMAuthenticator: "+localNtlmMsg1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ntlmMsg1 = localNtlmMsg1;
    }

    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        final List<String> WWWAuthenticate = response.headers().values("WWW-Authenticate");
        if (WWWAuthenticate.contains("NTLM")) {
            return response.request().newBuilder().header("Authorization", "NTLM " + ntlmMsg1).build();
        }
        Log.i(TAG, "authenticate: comt to ---- --- ---- ");
        String ntlmMsg3 = null;
        try {
            ntlmMsg3 = engine.generateType3Msg(username, password, domain, "android-device", WWWAuthenticate.get(0).substring(5));
            Log.i(TAG, "authenticate: ntlmMsg3"+ntlmMsg3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.request().newBuilder().header("Authorization", "NTLM " + ntlmMsg3).build();
    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        return null;
    }
}
