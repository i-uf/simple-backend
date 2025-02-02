package com.i_uf.backend;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * This Interface is a response from the {@link WebRouter}
 *
 * @since 1.0.0
 *
 * @author I_uf <i>(uf_developer@outlook.kr)</i>
 */
public interface WebResponse {
    int code();
    byte[] data();
    Map<String, List<String>> getHeader();

    static WebResponse create(int code, byte[] data, Map<String, List<String>> header) {
        return new WebResponse() {
            public int code() { return code; }
            public byte[] data() { return data; }
            public Map<String, List<String>> getHeader() { return header; }
        };
    }
    static WebResponse createFromString(int code, String data, Map<String, List<String>> header) {
        return create(code, data.getBytes(), header);
    }
    static WebResponse createFromStream(int code, InputStream stream, Map<String, List<String>> header) throws IOException {
        return create(code, stream.readAllBytes(), header);
    }
}
