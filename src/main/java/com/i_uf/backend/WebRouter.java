package com.i_uf.backend;

import java.io.IOException;
/**
 * This Interface is a Router of the {@link WebServer}
 * It responds a {@link WebResponse} to {@link WebRequest}
 *
 * @since 1.0.0
 *
 * @author I_uf <i>(uf_developer@outlook.kr)</i>
 */
public interface WebRouter {
    WebResponse applyRequest(WebRequest request) throws IOException;
}