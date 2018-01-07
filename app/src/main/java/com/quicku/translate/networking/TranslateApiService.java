package com.quicku.translate.networking;

import com.quicku.translate.models.LanguageDetectResponse;
import com.quicku.translate.models.LanguageTranslationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateApiService {
    @GET("detect")
    Call<LanguageDetectResponse> detectLanguage(@Query("key") String key,
                                                @Query("text") String text);

    @GET("translate")
    Call<LanguageTranslationResponse> translateLanguage(@Query("key") String key,
                                                        @Query("text") String text,
                                                        @Query("lang") String lang);
}
