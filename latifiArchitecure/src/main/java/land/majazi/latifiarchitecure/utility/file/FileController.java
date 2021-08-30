package land.majazi.latifiarchitecure.utility.file;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;

import com.isseiaoki.simplecropview.util.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static android.provider.MediaStore.MediaColumns.IS_DOWNLOAD;

public class FileController {

    //______________________________________________________________________________________________ FileController
    public FileController() {
    }
    //______________________________________________________________________________________________ FileController


    //______________________________________________________________________________________________ getNewFileName
    private String getNewFileName() {
        return new SimpleDateFormat("yyyy_MM_dd__HH_mm_ss", Locale.getDefault()).format(new Date());
    }
    //______________________________________________________________________________________________ getNewFileName


    //______________________________________________________________________________________________ getFileName
    public String getFileName(int type) {

        String timeStamp = getNewFileName();
        switch (type) {
            case MEDIA_TYPE_IMAGE:
                timeStamp = "IMG_" + timeStamp + ".jpg";
                break;
            case MEDIA_TYPE_VIDEO:
                timeStamp = "Video_" + timeStamp + ".mp4";
                break;
            default:
                return null;
        }
        return timeStamp;
    }
    //______________________________________________________________________________________________ getFileName


    //______________________________________________________________________________________________ getUriFromFile
    public Uri getUriFromFile(Activity activity, File file) {

        PackageInfo pInfo;
        String packageName = null;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            packageName = pInfo.packageName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        if (packageName == null)
            return null;
        else {
            String authority = packageName + ".provider";
            return FileProvider.getUriForFile(
                    activity,
                    authority, //(use your app signature + ".provider" )
                    file);
        }
    }
    //______________________________________________________________________________________________ getUriFromFile



    //______________________________________________________________________________________________ getOutputMediaFileUri
    public Uri getOutputMediaFileUri(Activity activity, String appName, int type) {

        PackageInfo pInfo;
        String packageName = null;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            packageName = pInfo.packageName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        if (packageName == null)
            return null;
        else {
            String authority = packageName + ".provider";
            return FileProvider.getUriForFile(activity, authority, getOutputMediaFile(type, appName));
        }
    }
    //______________________________________________________________________________________________ getOutputMediaFileUri



    //______________________________________________________________________________________________ getOutputMediaDirUri
    public Uri getOutputMediaDirUri(Activity activity, String appName, int type) {

        PackageInfo pInfo;
        String packageName = null;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            packageName = pInfo.packageName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }

        if (packageName == null)
            return null;
        else {
            String authority = packageName + ".provider";
            return FileProvider.getUriForFile(activity, authority, new File(getAppMediaFolder(type, appName)));
        }
    }
    //______________________________________________________________________________________________ getOutputMediaDirUri


    //______________________________________________________________________________________________ getOutputMediaFile
    public File getOutputMediaFile(int type, String appName) {

        String timeStamp = getNewFileName();
        File mediaFile;
        switch (type) {
            case MEDIA_TYPE_IMAGE:
                mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_IMAGE, appName) + File.separator + "IMG_" + timeStamp + ".jpg");
                break;
            case MEDIA_TYPE_VIDEO:
                mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_VIDEO, appName) + File.separator + "Video_" + timeStamp + ".mp4");
                break;
            case MEDIA_TYPE_DOCUMENT:
                mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_DOCUMENT, appName) + File.separator + "Document_" + timeStamp);
                break;
            case MEDIA_TYPE_NONE:
                mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_NONE, appName) + File.separator + "Download_" + timeStamp + ".apk");
                break;

            default:
                return null;
        }

        return mediaFile;
    }
    //______________________________________________________________________________________________ getOutputMediaFile


    //______________________________________________________________________________________________ getAppMediaFolder
    public String getAppMediaFolder(int type, String appName) {

        File mediaStorageDir = null;
        switch (type) {
            case MEDIA_TYPE_IMAGE:
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), appName);
                if (!mediaStorageDir.exists())
                    if (!mediaStorageDir.mkdirs()) {

                        return null;
                    }
                break;
            case MEDIA_TYPE_VIDEO:
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), appName);
                if (!mediaStorageDir.exists())
                    if (!mediaStorageDir.mkdirs()) {

                        return null;
                    }
                break;
            case MEDIA_TYPE_DOCUMENT:
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), appName);
                if (!mediaStorageDir.exists())
                    if (!mediaStorageDir.mkdirs()) {

                        return null;
                    }
                break;
            case MEDIA_TYPE_NONE:
                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), appName);
                if (!mediaStorageDir.exists())
                    if (!mediaStorageDir.mkdirs()) {

                        return null;
                    }
                break;
        }

        return mediaStorageDir.getPath();
    }
    //______________________________________________________________________________________________ getAppMediaFolder


    //______________________________________________________________________________________________ getPathImage
    public String getPathImage(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }
    //______________________________________________________________________________________________ getPathImage


    //______________________________________________________________________________________________ getPathVideo
    public String getPathVideo(Context context, Uri uri, String appName) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        String path = getAppMediaFolder(MEDIA_TYPE_VIDEO, appName);
        path = path + "/" + data;
        cursor.close();
        return path;
    }
    //______________________________________________________________________________________________ getPathVideo



    //______________________________________________________________________________________________ getPathDownload
    public String getPathDownload(Context context, Uri uri, String appName) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        String path = getAppMediaFolder(MEDIA_TYPE_NONE, appName);
        path = path + "/" + data;
        cursor.close();
        return path;
    }
    //______________________________________________________________________________________________ getPathDownload



    //______________________________________________________________________________________________ getMimeType
    public String getMimeType(Bitmap.CompressFormat format) {
        Logger.i("getMimeType CompressFormat = " + format);
        switch (format) {
            case JPEG:
                return "jpeg";
            case PNG:
                return "png";
        }
        return "png";
    }
    //______________________________________________________________________________________________ getMimeType


    //______________________________________________________________________________________________ createNewUri
    public Uri createNewUri(Context context, Bitmap.CompressFormat format, String appName) {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        FileController fileController = new FileController();
        String dirPath = fileController.getAppMediaFolder(MEDIA_TYPE_IMAGE, appName);
        fileController = null;
        String fileName = "scv" + title + "." + getMimeType(format);
        String path = dirPath + "/" + fileName;
        File file = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + getMimeType(format));
        values.put(MediaStore.Images.Media.DATA, path);
        long time = currentTimeMillis / 1000;
        values.put(MediaStore.MediaColumns.DATE_ADDED, time);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, time);
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }

        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }
    //______________________________________________________________________________________________ createNewUri


    //______________________________________________________________________________________________ createSaveUri
    public Uri createSaveUri(Context context, String appName) {
        Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
        return createNewUri(context, mCompressFormat, appName);
    }
    //______________________________________________________________________________________________ createSaveUri


}
