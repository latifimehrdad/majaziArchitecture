package land.majazi.latifiarchitecure.utility.compress.video;

import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.io.File;

public class CompressVideo {

    //______________________________________________________________________________________________ CompressVideo
    public CompressVideo() {
    }
    //______________________________________________________________________________________________ CompressVideo


    //______________________________________________________________________________________________ compressVideo
    public void compressVideo(Activity context, Uri uri, File file, File compress, ML_VideoSlimmer.ProgressListener progressListener) {

        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(file.getPath());
            int width = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            int rotation = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));

            int desired_width = 0;
            int desired_height = 0;

            if (rotation == 0 || rotation == 180) {
                if (width < 852)
                    desired_width = width;
                else
                    desired_width = 852;
                desired_height = 486;
            } else {
                desired_width = 486;
                if (height < 852)
                    desired_height = height;
                else
                    desired_height = 852;
            }


            ML_VideoSlimmer.convertVideo(file.getPath(), compress.getPath(), desired_width, desired_height, desired_width * desired_height * 30, progressListener);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //______________________________________________________________________________________________ compressVideo

}
