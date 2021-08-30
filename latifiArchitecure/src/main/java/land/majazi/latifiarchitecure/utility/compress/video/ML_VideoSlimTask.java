package land.majazi.latifiarchitecure.utility.compress.video;

import android.os.AsyncTask;

public class ML_VideoSlimTask extends AsyncTask<Object, Float, Boolean> {
    private ML_VideoSlimmer.ProgressListener mListener;


    public ML_VideoSlimTask(ML_VideoSlimmer.ProgressListener listener) {
        mListener = listener;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected Boolean doInBackground(Object... paths) {
        String sourcePath = (String)paths[0];
        String destinationPath = (String)paths[1];
        int nwidth = (Integer)paths[2];
        int nheight = (Integer)paths[3];
        int nbitrate = (Integer)paths[4];
        return new ML_VideoSlimEncoder().convertVideo(sourcePath, destinationPath, nwidth, nheight,nbitrate, percent -> publishProgress(percent));
    }

    @Override
    protected void onProgressUpdate(Float... percent) {
        super.onProgressUpdate(percent);
        if (mListener != null) {
            mListener.onProgress(percent[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (mListener != null) {
            if (result) {
                mListener.onFinish(true);
            } else {
                mListener.onFinish(false);
            }
        }
    }
}