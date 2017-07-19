package br.com.wasys.library.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import org.apache.commons.lang3.ArrayUtils;

import br.com.wasys.library.Application;

/**
 * Created by pascke on 05/09/16.
 */
public abstract class Service {

    public static boolean isPermissionGranted(String... permissions) {
        boolean granted = false;
        if (ArrayUtils.isNotEmpty(permissions)) {
            granted = true;
            Context context = Application.getContext();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
        }
        return granted;
    }
}
