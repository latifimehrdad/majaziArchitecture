package land.majazi.latifiarchitecure.utility.compress.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import land.majazi.latifiarchitecure.models.MD_ImageSize;

public class ImageResize {


    //______________________________________________________________________________________________ ImageResize
    public ImageResize() {
    }
    //______________________________________________________________________________________________ ImageResize



    //______________________________________________________________________________________________ resizeImage
    public File resizeImage(File file) {

        int desired_size = 800;
        int resizeWidth = 0;
        int resizeHeight = 0;

        MD_ImageSize md_imageSize = getIMGSize(file);
        if (md_imageSize.getHeight() > md_imageSize.getWidth()) {
            if (md_imageSize.getWidth() > desired_size) {
                double temp = (double)md_imageSize.getWidth() / (double)desired_size;
                resizeWidth = 800;
                resizeHeight = (int) Math.round((double)md_imageSize.getHeight() / temp);
            } else {
                resizeWidth = md_imageSize.getWidth();
                resizeHeight = md_imageSize.getHeight();
            }
        } else {
            if (md_imageSize.getHeight() > desired_size) {
                double temp = (double)md_imageSize.getHeight() / (double)desired_size;
                resizeHeight = 800;
                resizeWidth = (int) Math.round((double)md_imageSize.getWidth() / temp);
            } else {
                resizeWidth = md_imageSize.getWidth();
                resizeHeight = md_imageSize.getHeight();
            }
        }


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        return file;

    }
    //______________________________________________________________________________________________ resizeImage



    //______________________________________________________________________________________________ getString
    private MD_ImageSize getIMGSize(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        return new MD_ImageSize(imageWidth, imageHeight);
    }
    //______________________________________________________________________________________________ getString


}
