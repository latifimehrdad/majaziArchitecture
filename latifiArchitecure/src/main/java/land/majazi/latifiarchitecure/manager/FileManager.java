package land.majazi.latifiarchitecure.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.isseiaoki.simplecropview.util.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class FileManager {

    //______________________________________________________________________________________________ FileController
    public FileManager() {
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
            case MEDIA_TYPE_NONE:
                mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_NONE, appName) + File.separator + "Download_" + timeStamp + ".apk");
                break;

            default:
                return null;
        }

        return mediaFile;
    }
    //______________________________________________________________________________________________ getOutputMediaFile


    //______________________________________________________________________________________________ getOutputDOCUMENTFile
    public File getOutputDOCUMENTFile(String appName, String fileName) {

        String timeStamp = getNewFileName();
        File mediaFile;
        if (fileName == null || fileName.isEmpty())
            mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_DOCUMENT, appName) + File.separator + "Document_" + timeStamp);
        else
            mediaFile = new File(getAppMediaFolder(MEDIA_TYPE_DOCUMENT, appName) + File.separator + fileName);

        return mediaFile;
    }
    //______________________________________________________________________________________________ getOutputDOCUMENTFile


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
    public String getPathImage(Context context, Uri uri, String appName) {
/*        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();*/
        String s = "";
        try {
            s = PathUtil.getPath(context, uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (s == null || s.isEmpty()) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) return null;
            cursor.moveToFirst();
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            String path = getAppMediaFolder(MEDIA_TYPE_IMAGE, appName);
            s = path + "/" + data;
            cursor.close();
        }

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
        FileManager fileManager = new FileManager();
        String dirPath = fileManager.getAppMediaFolder(MEDIA_TYPE_IMAGE, appName);
        fileManager = null;
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


    //______________________________________________________________________________________________ PathUtil
    public static class PathUtil {
        /*
         * Gets the file path of the given Uri.
         */
        @SuppressLint("NewApi")
        public static String getPath(Context context, Uri uri) throws URISyntaxException {
            final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
            String selection = null;
            String[] selectionArgs = null;
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            // deal with different Uris.
            if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    uri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("image".equals(type)) {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    selection = "_id=?";
                    selectionArgs = new String[]{split[1]};
                }
            }
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }


        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
    }
    //______________________________________________________________________________________________ PathUtil

}


