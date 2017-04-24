package com.qwerty.yandextranslate.model.data.network.api;

import com.qwerty.yandextranslate.model.data.network.responses.DetectLanguageResponse;
import com.qwerty.yandextranslate.model.data.network.responses.SupportLanguageResponse;
import com.qwerty.yandextranslate.model.data.network.responses.TranslationResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateApi {
    @GET("getLangs")
    Observable<SupportLanguageResponse> getSupportLanguages(@Query("key") String key,
                                                            @Query("ui") String ui);

    @GET("detect")
    Observable<DetectLanguageResponse> detectLanguage(@Query("key") String key,
                                                      @Query("text") String text);

    @GET("translate")
    Observable<TranslationResponse> translate(@Query("key") String key,
                                                        @Query("text") String text,
                                                        @Query("lang") String lang);
    @GET("translate")
    Observable<TranslationResponse> translateWithLanguageDetect(@Query("key") String key,
                                                                @Query("text") String text,
                                                                @Query("options") int option);
}
