package fr.arnaudguyon.toolbox;

import android.os.Handler;

/**
 * Created by aguyon on 13.01.17.
 */

public abstract class BgThread {

    private Handler mHandler;
    private Runnable mRunnable;
    private String mName;

    public BgThread() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                doInBackground();
                if (mHandler != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
            }
        };
    }

    public BgThread(String name) {
        this();
        mName = name;
    }

    public final void execute() {
        mHandler = new Handler();
        Thread thread = new Thread(mRunnable);
        if (mName != null) {
            thread.setName(mName);
        }
        thread.start();
    }

    protected abstract void doInBackground();
    protected abstract void onPostExecute();

}
