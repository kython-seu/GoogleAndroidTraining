package trendmicro.okhttpntlm;

import android.os.Build;
import android.util.Log;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;

import java.io.IOException;
import java.util.List;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


/**
 * Created by IBM on 2016/9/2.
 */
public class ProxyNTLMAuthenticator implements Authenticator {

    private static final String TAG = ProxyNTLMAuthenticator.class.getSimpleName();

    private static final String PROXY_AUTHENTICATE_TYPE = "NTLM";
    private static final String NTLM_MSG_WORKSTATION_NAME = Build.DEVICE;

    private final NTLMEngine engine = new NTLMEngineImpl();
    private final String domain;
    private final String username;
    private final String password;
    private final String type1Msg;

    public ProxyNTLMAuthenticator(String username, String password, String domain)
            throws AuthenticationException {
        this.domain = domain;
        this.username = username;
        this.password = password;
        try {
            type1Msg = engine.generateType1Msg(domain, NTLM_MSG_WORKSTATION_NAME);
        } catch (NTLMEngineException e) {
            throw new AuthenticationException("Exception by creating 'type1' message part",
                    e.getCause());
        }
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if(response.request().method().equals("GET")) {
            Log.d(TAG, "POST->GET");
        }

        Log.d(TAG, "Original request for proxy " + response.request().method());
        final List<String> authHeader = response.headers().values("Proxy-Authenticate");

        if (authHeader.contains(PROXY_AUTHENTICATE_TYPE)) {
            Log.d(TAG, "Sending proxy authentication message (1): " + type1Msg);

            Request request = response.request().newBuilder()
                    .header("Proxy-Authenticate", buildProxyAuthHttpHeader(type1Msg))
                    .build();

            return request;
        }

        // if (authHeader.get(0).startsWith(PROXY_AUTHENTICATE_TYPE)) {
        // Removes starting with "NTLM\s"
        String type2Msg = authHeader.get(0).substring(PROXY_AUTHENTICATE_TYPE.length()).trim();
        String type3Msg = null;
        try {
            type3Msg = engine.generateType3Msg(username, password, domain,
                    NTLM_MSG_WORKSTATION_NAME, type2Msg);
        } catch (NTLMEngineException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Sending proxy authentication message (3): " + type3Msg);

        String method = response.request().method();
        Log.d(TAG, "Request method is " + method);
        Request request = response.request().newBuilder()
                .header("Proxy-Authenticate", buildProxyAuthHttpHeader(type3Msg))
                .build();
        Log.d(TAG, "Intercepted Request for proxy " + request);

        return request;
        // }

        // Do not intercept
        // Request request = response.request();
        // return request;
    }

    private String buildProxyAuthHttpHeader(String message) {
        return String.format("%s %s", PROXY_AUTHENTICATE_TYPE, message);
    }
}
