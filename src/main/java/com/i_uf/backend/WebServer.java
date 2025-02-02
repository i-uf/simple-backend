package com.i_uf.backend;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;

/**
 * This Class boots the {@link HttpServer} and manages {@link WebRouter}s
 *
 * @see HttpServer
 * @see HttpsServer
 *
 * @since 1.0.0
 *
 * @author I_uf <i>(uf_developer@outlook.kr)</i>
 */
public class WebServer {
    private final InetSocketAddress address;
    private final boolean secure;

    protected final HttpServer server;

    public InetSocketAddress getAddress() { return address; }
    public boolean isSecure() { return secure; }

    /**
     * This Class boots the {@link HttpServer} and manages {@link WebRouter}s
     *
     * @param port port of the server address
     */
    public WebServer(int port) throws IOException {
        this(port, false);
    }

    /**
     * This Class boots the {@link HttpServer} and manages {@link WebRouter}s
     *
     * @param port port of the server address
     * @param secure enable secure mode ( https:// )
     * @param routers initial value of routers
     */
    public WebServer(int port, boolean secure, WebRouter... routers) throws IOException {
        this(port, secure, List.of(routers));
    }

    /**
     * This Class boots the {@link HttpServer} and manages {@link WebRouter}s
     *
     * @param port port of the server address
     * @param secure enable secure mode ( https:// )
     * @param routers initial value of routers
     */
    public WebServer(int port, boolean secure, Collection<WebRouter> routers) throws IOException {
        address = new InetSocketAddress(port);
        this.secure = secure;
        server = secure ?
                HttpsServer.create(new InetSocketAddress(port), 0) :
                HttpServer.create(new InetSocketAddress(port), 0);
    }
    /**
     * start the server
     */
    public void start() {
        server.start();
    }
    /**
     * stop the server
     * @param code exit code
     */
    public void stop(int code) {
        server.stop(code);
    }

    /**
     * send the response when requesting this path
     * @param path path
     * @param router responding to requests
     */
    public void addRouter(String path, WebRouter router) {
        server.createContext(path, e -> {
            WebResponse response  = router.applyRequest(WebRequest.create(e));
            e.getResponseHeaders().putAll(response.getHeader());
            e.sendResponseHeaders(response.code(), response.data().length);
            OutputStream body = e.getResponseBody();
            body.write(response.data());
            body.close();
        });
    }
}
