package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.x0;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Steve Soltys
 */
public interface X0UploadService {

    @Multipart
    @POST("/")
    Call<String> upload(@Part MultipartBody.Part body);
}
