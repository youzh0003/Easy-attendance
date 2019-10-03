package com.zhiyong.easy_attendance.data.remote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.blankj.utilcode.util.DeviceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.data.local.PreferenceHelper;
import com.zhiyong.easy_attendance.data.local.RealmHelper;
import com.zhiyong.easy_attendance.utils.DateUtils;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public interface RetrofitService {
    // Example of API
//    @GET("units/paired")
//    Flowable<List<Unit>> getUnits();

    class Creator {
        private static Interceptor cacheInterceptor;

        public static Retrofit newRetrofitInstance(final Context context,
                                                   final boolean isNetworkAvailable,
                                                   final RealmHelper realmHelper,
                                                   PreferenceHelper preferenceHelper) {
            cacheInterceptor = chain -> {
                Request request = chain.request();
                if (!isNetworkAvailable) {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                return originalResponse;
            };
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Setup cache
            File httpCacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "OKHttpCache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            @SuppressLint("TimberArgCount") OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addNetworkInterceptor(cacheInterceptor)
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addInterceptor(chain -> {
                        PackageInfo pInfo = null;
                        try {
                            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        final String accessToken = preferenceHelper.getAccessToken();
                        final String unitId = "xyz";
                        String version = pInfo.versionName;
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .header("X-ENZAN-UUID", DeviceUtils.getAndroidID())
                                .header("X-ENZAN-OS-INDEX", "android")
                                .header("X-ENZAN-OS-VERSION", Build.VERSION.RELEASE)
                                .header("X-ENZAN-SERVICE-TYPE", "CHI")
                                .header("X-ENZAN-APP-VERSION", version)
                                .header("X-ENZAN-LANGUAGE", Locale.getDefault().getLanguage())
                                .header("X-ENZAN-DEVICE-MODEL", DeviceUtils.getModel())
                                .header("Authorization", "Bearer " + accessToken);
                        if (original.header("X-ENZAN-UNIT-ID") == null
                                || original.header("X-ENZAN-UNIT-ID").isEmpty()) {
                            builder.header("X-ENZAN-UNIT-ID", unitId);
                        }
                        Timber.d("X-ENZAN-UUID: " + DeviceUtils.getAndroidID());
                        Request request = builder
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat(DateUtils.PATTERN_REPORT)
                    .create();

            String ENDPOINT = context.getResources().getString(R.string.api_end_point);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit;
        }
    }
}
