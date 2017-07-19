package cheqfast.gfin.wasys.com.br.coleta.endpoint;

import android.content.Context;
import android.os.Build;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import br.com.wasys.library.enumerator.DeviceHeader;
import br.com.wasys.library.enumerator.HttpStatus;
import br.com.wasys.library.enumerator.MediaType;
import br.com.wasys.library.exception.EndpointException;
import br.com.wasys.library.http.Error;
import br.com.wasys.library.utils.AndroidUtils;
import cheqfast.gfin.wasys.com.br.coleta.Application;
import cheqfast.gfin.wasys.com.br.coleta.BuildConfig;
import cheqfast.gfin.wasys.com.br.coleta.Dispositivo;
import cheqfast.gfin.wasys.com.br.coleta.R;
import retrofit2.Call;

/**
 * Created by pascke on 03/08/16.
 */
public class Endpoint {

    public static final String BASE_URL = BuildConfig.BASE_URL;

    public static Map<String, String> getHeaders() {
        Context context = Application.getContext();
        Map<String, String> headers = new HashMap<>();
        headers.put(DeviceHeader.DEVICE_SO.key, "Android");
        headers.put(DeviceHeader.DEVICE_APP.key, BuildConfig.APP);
        headers.put(DeviceHeader.DEVICE_MODEL.key, Build.MODEL);
        headers.put(DeviceHeader.DEVICE_SO_VERSION.key, Build.VERSION.RELEASE);
        headers.put(DeviceHeader.DEVICE_WIDTH.key, String.valueOf(AndroidUtils.getWidthPixels(context)));
        headers.put(DeviceHeader.DEVICE_HEIGHT.key, String.valueOf(AndroidUtils.getHeightPixels(context)));
        headers.put(DeviceHeader.DEVICE_APP_VERSION.key, String.valueOf(AndroidUtils.getVersionCode(context)));
        Dispositivo dispositivo = Dispositivo.current();
        if (dispositivo != null) {
            String token = dispositivo.getToken();
            if (StringUtils.isNotBlank(token)) {
                headers.put(DeviceHeader.DEVICE_TOKEN.key, token);
            }
        }
        return headers;
    }

    public static <T> T create(Class<T> clazz) {
        return create(clazz, null, null, null);
    }

    public static <T> T create(Class<T> clazz, Long timeout) {
        return create(clazz, timeout, timeout, timeout);
    }

    public static <T> T create(Class<T> clazz, Long readTimeout, Long writeTimeout, Long connectTimeout) {
        Map<String, String> headers = getHeaders();
        headers.put("Content-Type", MediaType.APPLICATION_JSON.value);
        return br.com.wasys.library.http.Endpoint.create(clazz, BASE_URL, headers, readTimeout, writeTimeout, connectTimeout);
    }

    public static <T> T execute(Call<T> call) throws EndpointException {
        Context context = Application.getContext();
        if (!AndroidUtils.isNetworkAvailable(context)) {
            Error error = new Error(HttpStatus.NOT_APPLY, context.getString(R.string.network_not_available));
            throw new EndpointException(error);
        }
        return br.com.wasys.library.http.Endpoint.execute(call);
    }
}
