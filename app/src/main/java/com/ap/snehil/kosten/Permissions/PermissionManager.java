package com.ap.snehil.kosten.Permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HP on 13-08-2017.
 */

public class PermissionManager {
    public static final String TAG ="permissionManger";

    static ArrayList<OnPermissionResultListener> listenerList
            = new ArrayList<>();

    public static void askForPermission(Activity act, String[] permissions,
                                        OnPermissionResultListener oprl) {
        Log.d(TAG, "askForPermission: **********************8");

        int reqCode = listenerList.size();
        listenerList.add(reqCode, oprl);


        ActivityCompat.requestPermissions(act, permissions, reqCode);
    }

    public interface OnPermissionResultListener {
        void onGranted (String permission);
        void onDenied(String permission);
    }

    public static void onRequestPermissionsResult(int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: *************************");

        try {
            OnPermissionResultListener oprl = listenerList.get(requestCode);

            for (int i = 0; i < permissions.length; i++) {

                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsResult: granted **********88");
                    oprl.onGranted(permissions[i]);
                }
                else {
                    Log.d(TAG, "onRequestPermissionsResult: denied *********************8");
                    oprl.onDenied(permissions[i]);
                    
                }
            }

        } catch (IndexOutOfBoundsException ioobe) {
            Log.d(TAG, "onRequestPermissionsResult: No Such listener registered");
        }


    }
}

