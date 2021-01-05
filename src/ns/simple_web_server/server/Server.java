package ns.simple_web_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(500);

    public Server(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);
    }

    public void start() {
        while (true) {
            try {
                threadPool.submit(new CommunicationHandler(serverSocket.accept()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
