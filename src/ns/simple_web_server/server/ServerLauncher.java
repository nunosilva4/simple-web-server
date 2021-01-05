package ns.simple_web_server.server;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            new Server(8080).start();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
