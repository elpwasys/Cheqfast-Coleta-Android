package cheqfast.gfin.wasys.com.br.coleta;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by pascke on 05/09/16.
 */
public class Permission {

    public static final String[] SCAN;
    public static final String[] PHONE;
    public static final String[] STORAGE;
    public static final String[] LOCATION;

    static  {
        PHONE = new String[] { Manifest.permission.READ_PHONE_STATE };
        STORAGE = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        LOCATION = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        SCAN = merge(Permission.STORAGE, Manifest.permission.CAMERA);
    }

    public static String[] merge(String[] permitions, String... permition) {
        return ArrayUtils.addAll(permitions, permition);
    }

    public static void throwIfScanNotGranted() {
        if (!isScanGranted()) {
            throw new SecurityException("Permission denied (missing camera and storage permission?)");
        }
    }

    public static void throwIfStorageNotGranted() {
        if (!isStorageGranted()) {
            throw new SecurityException("Permission denied (missing storage permission?)");
        }
    }

    public static boolean isScanGranted() {
        Context context = Application.getContext();
        for (String permission : SCAN) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public static boolean isStorageGranted() {
        Context context = Application.getContext();
        for (String permission : STORAGE) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
