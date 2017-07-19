package cheqfast.gfin.wasys.com.br.coleta.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cheqfast.gfin.wasys.com.br.coleta.Dispositivo;
import cheqfast.gfin.wasys.com.br.coleta.R;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 2000);
    }

    private void start() {
        Intent intent;
        Dispositivo dispositivo = Dispositivo.current();
        if (dispositivo != null) {
            intent = MainActivity.newIntent(this);
        } else {
            intent = LoginActivity.newIntent(this);
        }
        startActivity(intent);
        finish();
    }
}