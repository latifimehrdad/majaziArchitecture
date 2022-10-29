package land.majazi.latifiarchitecure.compress.video;

public class ML_VideoSlimmer {

    public static ML_VideoSlimTask convertVideo(String srcPath, String destPath, int outputWidth, int outputHeight, int bitrate, ProgressListener listener) {
        ML_VideoSlimTask task = new ML_VideoSlimTask(listener);
        task.execute(srcPath, destPath, outputWidth, outputHeight, bitrate);
        return task;
    }


    public static interface ProgressListener {

        void onStart();
        void onFinish(boolean result);
        void onProgress(float progress);

    }
}
