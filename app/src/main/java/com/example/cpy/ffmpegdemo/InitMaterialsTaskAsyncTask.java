package com.example.cpy.ffmpegdemo;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 初始化 复制 素材
 */
public class InitMaterialsTaskAsyncTask extends AsyncTask<Void, Void, Void> {


    private final WeakReference<OnMaterialInitListener> mListenerWRef;

    public InitMaterialsTaskAsyncTask(OnMaterialInitListener listener) {
        this.mListenerWRef = new WeakReference<>(listener);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Context context = AppApplication.getContext();
        copyMaterials(context);
        return null;
    }


    /**
     * 拷贝AR素材和美型素材
     */
    private void copyMaterials(Context context) {
        final File materialsDir = getMaterDir(context);

        //这里的IO操作应该放在Work线程去处理，千万不要学我偷懒啊！！！
        if (!materialsDir.exists() && materialsDir.mkdirs()) {
            try {
                AssetsUtil.copyDirectory(context.getAssets(), "audio", materialsDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static File getMaterDir(Context context){
        final File filesDir;
        final File[] externalFilesDirs = ContextUtil.getExternalFilesDirs(context);
        if (externalFilesDirs != null && externalFilesDirs.length > 0) {
            filesDir = externalFilesDirs[0];
        } else {
            filesDir = context.getFilesDir();
        }
        final File materialsDir = new File(filesDir, "audio");
        return materialsDir;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        OnMaterialInitListener listener = mListenerWRef.get();
        if (listener != null) {
            listener.onMaterialSuccess();
        }
    }


    /**
     * 素材初始化监听
     */
    public interface OnMaterialInitListener {
        /**
         * 素材加载成功
         */
        void onMaterialSuccess();
    }
}
