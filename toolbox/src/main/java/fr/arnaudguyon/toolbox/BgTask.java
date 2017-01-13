package fr.arnaudguyon.toolbox;

import android.os.AsyncTask;

/**
 * Created by aguyon on 13.01.17.
 */

public abstract class BgTask {

    private AsyncTask<Void, Void, Void> mTask;

    private BgTask() {
    }

    public BgTask(String name) {
        this();
        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                BgTask.this.doInBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                BgTask.this.onPostExecute();
            }

        };
    }

    public void execute() {
        mTask.execute();
    }
    protected abstract void doInBackground();
    protected abstract void onPostExecute();

}
