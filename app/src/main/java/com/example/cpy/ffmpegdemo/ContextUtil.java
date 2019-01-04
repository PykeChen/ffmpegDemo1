package com.example.cpy.ffmpegdemo;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 *
 */
public final class ContextUtil {

    private ContextUtil() {
        throw new IllegalAccessError();
    }

    public static File[] getExternalFilesDirs(@NonNull final Context context) {
        return getExternalFilesDirs(context, null);
    }

    /**
     * @param context Context.
     * @param type The type of files directory to return.  May be null for
     * the root of the files directory or one of
     * the following Environment constants for a subdirectory:
     * {@link Environment#DIRECTORY_MUSIC},
     * {@link Environment#DIRECTORY_PODCASTS},
     * {@link Environment#DIRECTORY_RINGTONES},
     * {@link Environment#DIRECTORY_ALARMS},
     * {@link Environment#DIRECTORY_NOTIFICATIONS},
     * {@link Environment#DIRECTORY_PICTURES}, or
     * {@link Environment#DIRECTORY_MOVIES}.
     *
     * @see Context#getExternalFilesDirs(String)
     * @see Environment#getExternalStorageState(File)
     */
    @Nullable
    public static File[] getExternalFilesDirs(@NonNull final Context context,
                                              @Nullable final String type) {
        File[] files = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            final File externalFilesDir = context.getExternalFilesDir(type);
            if (externalFilesDir != null) {
                files = new File[] {externalFilesDir};
            }
        } else {
            files = context.getExternalFilesDirs(type);
        }
        return files;
    }

}
