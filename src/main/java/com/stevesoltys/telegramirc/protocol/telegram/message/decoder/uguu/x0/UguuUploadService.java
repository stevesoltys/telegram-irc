package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.uguu.x0;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Steve Soltys
 */
public interface UguuUploadService {

    @Multipart
    @POST("/api.php?d=upload")
    Call<String> upload(@Part MultipartBody.Part body);
}
