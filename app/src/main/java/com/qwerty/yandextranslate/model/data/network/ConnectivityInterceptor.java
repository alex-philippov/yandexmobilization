package com.qwerty.yandextranslate.model.data.network;

import android.content.Context;
import android.util.Log;

import com.qwerty.yandextranslate.common.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!Utils.isOnline(mContext)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        Response response = chain.proceed(builder.build());
        Log.d("tag_", response.toString());
        return response;
    }
}
