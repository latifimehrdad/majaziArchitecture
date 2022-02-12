package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import land.majazi.latifiarchitecure.enums.IPvMode;
import okhttp3.Cache;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private Context context;
    private String Host;
    private Gson gson;

    public RetrofitManager(Context context, String Host, Gson gson) {
        this.context = context;
        this.Host = Host;
        this.gson = gson;
    }

    public retrofit2.Retrofit getRetrofit() {

        OkHttpClient okHttpClient = getOkHttpClient(getCache(getFile()), getHttpLoggingInterceptor(), getInterceptor());

        return new retrofit2.Retrofit.Builder()
                .baseUrl(Host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }



    public OkHttpClient getOkHttpClient(Cache cache, HttpLoggingInterceptor loggingInterceptor, Interceptor interceptor) {

        return new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .dns(new DnsSelector(IPvMode.IPV4_FIRST, Dns.SYSTEM))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(3,TimeUnit.MINUTES)
                .build();
    }


    public Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("device", "android" )
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


    public Cache getCache(File file) {
        return new Cache(file, 5 * 1000 * 1000);
    }

    public File getFile() {
        return new File(context.getCacheDir(), "Okhttp_cache");
    }

}
