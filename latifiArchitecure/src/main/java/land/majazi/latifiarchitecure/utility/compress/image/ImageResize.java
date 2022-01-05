package land.majazi.latifiarchitecure.utility.compress.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Size;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageResize {


    //______________________________________________________________________________________________ resizeImage
    public File resizeImage(File file) {

        int desired_size = 800;
        Size resize;
        Size size = getIMGSize(file);

        if (size.getHeight() > size.getWidth()) {
            if (size.getWidth() > desired_size) {
                double temp = (double)size.getWidth() / (double)desired_size;
                resize = new Size(800, (int) Math.round((double)size.getHeight() / temp));
            } else
                resize = size;
        } else {
            if (size.getHeight() > desired_size) {
                double temp = (double)size.getHeight() / (double)desired_size;
                resize = new Size((int) Math.round((double)size.getWidth() / temp), 800);
            } else
                resize = size;
        }


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, resize.getWidth(), resize.getHeight(), true);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            return null;
        }

        return file;

    }
    //______________________________________________________________________________________________ resizeImage



    //______________________________________________________________________________________________ getString
    private Size getIMGSize(File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new Size(options.outWidth, options.outHeight);
    }
    //______________________________________________________________________________________________ getString


}
