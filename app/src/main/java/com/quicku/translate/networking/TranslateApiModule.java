package com.quicku.translate.networking;

import com.quicku.translate.BuildConfig;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TranslateApiModule {
    @Provides
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseUrl, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Provides
    public TranslateApiService provideApiService() {
        return provideRetrofit(BuildConfig.YANDEX_TRANSLATE_BASE_URL, provideClient())
                .create(TranslateApiService.class);
    }
}
