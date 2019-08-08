package top.wefor.nowview.data.http;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Thanks to
 * https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
 *
 * Created by ice on 3/13/16.
 */
public final class NowApi {

    private static Retrofit.Builder get(String baseUrl) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS);

        httpClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            @NonNull
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                String reqStr = request.toString();
                Logger.i(reqStr);
                Response response = chain.proceed(request);
                String respStr = response.toString();
                Logger.i(respStr);
                return response;
            }
        });
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(httpClientBuilder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        ;

        return builder;
    }

}
