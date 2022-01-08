package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import land.majazi.latifiarchitecure.enums.EnumImageLoader;

public class ImageLoaderManager {


    private static EnumImageLoader imageLoaderType;

    //______________________________________________________________________________________________ configurationImageLoader
    public void configurationImageLoader(Context context, EnumImageLoader type, String token) {

        if (imageLoaderType == null)
            configurationImageLoaderToken(context, token);
        else {
            if (imageLoaderType != type)
                switch (type) {
                    case token:
                        configurationImageLoaderToken(context, token);
                        break;
                    case normal:
                        configurationImageLoaderNormal(context);
                        break;
                }
        }
    }
    //______________________________________________________________________________________________ configurationImageLoader


    //______________________________________________________________________________________________ configurationImageLoaderToken
    public void configurationImageLoaderToken(Context context, String token) {

        imageLoaderType = EnumImageLoader.token;

        Map<String, String> headers = new HashMap();
        headers.put("Authorization", token);

        try {
            ImageLoader.getInstance().destroy();
        }catch (Exception e) {

        }

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(500))
                .extraForDownloader(headers)
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(4)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCache(new LimitedAgeDiskCache(context.getCacheDir(), 1200))
                .imageDownloader(new AuthDownloader(context))
                .build();

        ImageLoader.getInstance().init(config);
    }
    //______________________________________________________________________________________________ configurationImageLoaderToken



    //______________________________________________________________________________________________ configurationImageLoaderNormal
    public void configurationImageLoaderNormal(Context context) {


        imageLoaderType = EnumImageLoader.normal;

        try {
            ImageLoader.getInstance().destroy();
        }catch (Exception e) {

        }

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(500))
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(4)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCache(new LimitedAgeDiskCache(context.getCacheDir(), 1200))
                .build();

        ImageLoader.getInstance().init(config);
    }
    //______________________________________________________________________________________________ configurationImageLoaderNormal


    //______________________________________________________________________________________________ getImageLoader
    public ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }
    //______________________________________________________________________________________________ getImageLoader


    //______________________________________________________________________________________________ AuthDownloader
    public class AuthDownloader extends BaseImageDownloader {

        public AuthDownloader(Context context){
            super(context);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
            HttpURLConnection conn = super.createConnection(url, extra);
            Map<String, String> headers = (Map<String, String>) extra;
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            return conn;
        }
    }
    //______________________________________________________________________________________________ AuthDownloader

}
