package trendmicro.okhttpntlm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.apache.http.auth.AuthenticationException;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().
                    authenticator(new ProxyNTLMAuthenticator("kason_zhang", "zk_19921115", "trend")).build();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        //client.setAuthenticator(new NTLMAuthenticator(usr, pwd, dom));
    }
}
