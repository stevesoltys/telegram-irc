package com.stevesoltys.telegramirc.protocol.telegram.message.decoder.mixtape;

/**
 * @author Steve Soltys
 */
public class MixtapeFile {

    private String hash;

    private String name;

    private String url;

    private long size;

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public long getSize() {
        return size;
    }
}
