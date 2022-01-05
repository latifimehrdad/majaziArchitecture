package land.majazi.latifiarchitecure.views.application;

import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

import land.majazi.latifiarchitecure.utility.AuthDownloader;

public class MlApplication extends MultiDexApplication {

    private static enumImageLoader imageLoaderType;

    //______________________________________________________________________________________________ configurationImageLoader
    public void configurationImageLoader(enumImageLoader type, String token) {

        if (imageLoaderType == null)
            configurationImageLoaderToken(token);
        else {
            if (imageLoaderType != type)
                switch (type) {
                    case token:
                        configurationImageLoaderToken(token);
                        break;
                    case normal:
                        configurationImageLoaderNormal();
                        break;
                }
        }
    }
    //______________________________________________________________________________________________ configurationImageLoader



    //______________________________________________________________________________________________ configurationImageLoaderToken
    public void configurationImageLoaderToken(String token) {

        imageLoaderType = enumImageLoader.token;

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


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(4)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCache(new LimitedAgeDiskCache(getCacheDir(), 1200))
                .imageDownloader(new AuthDownloader(getApplicationContext()))
                .build();

        ImageLoader.getInstance().init(config);
    }
    //______________________________________________________________________________________________ configurationImageLoaderToken



    //______________________________________________________________________________________________ configurationImageLoaderNormal
    public void configurationImageLoaderNormal() {


        imageLoaderType = enumImageLoader.normal;

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


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(4)
                .diskCacheSize(100 * 1024 * 1024)
                .diskCache(new LimitedAgeDiskCache(getCacheDir(), 1200))
                .build();

        ImageLoader.getInstance().init(config);
    }
    //______________________________________________________________________________________________ configurationImageLoaderNormal


    //______________________________________________________________________________________________ getImageLoader
    public ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }
    //______________________________________________________________________________________________ getImageLoader



}
