package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.mixtape;

import java.util.List;

/**
 * @author Steve Soltys
 */
public class MixtapeResponse {

    private boolean success;

    private List<MixtapeFile> files;

    public boolean isSuccess() {
        return success;
    }

    public List<MixtapeFile> getFiles() {
        return files;
    }
}
