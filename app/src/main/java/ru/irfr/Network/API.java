package ru.irfr.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("irfr/13.zip")
    Call<ResponseBody> getFile();

}
