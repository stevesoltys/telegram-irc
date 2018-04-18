package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.mixtape;

import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;

/**
 * @author Steve Soltys
 */
@Component
public class MixtapeFileDecoder extends FileDecoder {

    private static final long MAX_FILE_SIZE = 100 * (1024 * 1024);

    public static final String IDENTIFIER = "mixtape";

    private static final String BASE_URL = "https://mixtape.moe";

    private final Logger logger = LoggerFactory.getLogger(MixtapeFileDecoder.class);

    @Override
    public Optional<String> decode(String fileUrl) {

        try {
            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), outputStream);

            Optional<MixtapeResponse> responseOptional = upload(url, outputStream.toByteArray());

            if (responseOptional.isPresent()) {
                MixtapeResponse response = responseOptional.get();

                return Optional.ofNullable(response.getFiles().get(0).getUrl());
            }
        } catch (Exception ex) {
            logger.error("Could not upload file to " + BASE_URL, ex);
        }

        return Optional.empty();
    }

    private Optional<MixtapeResponse> upload(URL url, byte[] data) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RequestBody file = MultipartBody.create(MediaType.parse(APPLICATION_OCTET_STREAM_VALUE), data);
        MultipartBody.Part requestBody = MultipartBody.Part.createFormData("files[]", url.getFile(), file);

        MixtapeUploadService service = retrofit.create(MixtapeUploadService.class);
        Response<MixtapeResponse> response = service.upload(requestBody).execute();

        if (response.isSuccessful()) {
            return Optional.ofNullable(response.body());
        }

        return Optional.empty();
    }

    @Override
    public long maxFileSize() {
        return MAX_FILE_SIZE;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
