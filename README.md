```java
try {

  final int port = 8080;
  WebServer server = new WebServer(port);
  
  WebRouter homepage = req -> WebResponse.createFromString(200, "Hello, World!", Map.of());
  
  server.addRouter("/", homepage);
  
  server.start();

} catch(IOException e) {

  throw new RuntimeException(e);

}
```
