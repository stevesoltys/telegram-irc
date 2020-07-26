package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.uguu.x0;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stevesoltys.telegramirc.protocol.telegram.message.decoder.FileDecoder;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

/**
 * @author Steve Soltys
 */
@Component
public class UguuFileDecoder extends FileDecoder {

    public static final String IDENTIFIER = "uguu";

    private static final long MAX_FILE_SIZE = 100 * (1024 * 1024);

    private static final String BASE_URL = "https://uguu.se";

    private final Logger logger = LoggerFactory.getLogger(UguuFileDecoder.class);

    @Override
    public Optional<String> decode(String fileUrl) {

        try {
            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), outputStream);

            return upload(url, outputStream.toByteArray());

        } catch (Exception ex) {
            logger.error("Could not upload file to " + BASE_URL, ex);
        }

        return Optional.empty();
    }

    private Optional<String> upload(URL url, byte[] data) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        RequestBody file = MultipartBody.create(MediaType.parse(MULTIPART_FORM_DATA), data);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("files[]", url.getFile(), file);

        UguuUploadService service = retrofit.create(UguuUploadService.class);
        Response<String> response = service.upload(requestBody).execute();

        try {
            if (response.isSuccessful() && response.body() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode objectNode = objectMapper.readTree(response.body());

                return Optional.ofNullable(objectNode.get("files").get(0).get("url").asText());
            }
        } catch (Exception ex) {
            logger.error("Could not decode Uguu file", ex);
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
