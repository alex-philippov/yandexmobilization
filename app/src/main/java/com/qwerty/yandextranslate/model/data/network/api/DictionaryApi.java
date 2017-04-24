package com.qwerty.yandextranslate.model.data.network.api;

import com.qwerty.yandextranslate.model.data.network.responses.dictionary_response.DictionaryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DictionaryApi {
    @GET("lookup")
    Observable<DictionaryResponse> getDictionary(@Query("key") String key,
                                                 @Query("lang") String lang,
                                                 @Query("text") String text,
                                                 @Query("ui") String ui);
}
