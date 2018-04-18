package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.mixtape;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Steve Soltys
 */
public interface MixtapeUploadService {

    @Multipart
    @POST("upload.php")
    Call<MixtapeResponse> upload(@Part MultipartBody.Part body);
}
