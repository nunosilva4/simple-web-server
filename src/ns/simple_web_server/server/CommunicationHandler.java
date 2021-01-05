package ns.simple_web_server.server;

import ns.simple_web_server.files.FileExtensions;

import java.io.*;
import java.net.Socket;

public class CommunicationHandler implements Runnable {

    private final String HOME_PAGE = "home.html";
    private final String NOT_FOUND = "not_found.html";
    private final String RESOURCES = "resources/";
    private final String ROOT = "/";
    private final String GET = "GET";

    private final Socket socket;
    private DataOutputStream out;

    public CommunicationHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            String request = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
            returnPage(request);
            socket.close();

        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    private void returnPage(String request) throws IOException {

        if (request == null) {
            return;
        }

        String[] requestBody = request.split(" ");

        if (requestBody.length == 1 || !requestBody[0].equals("GET")) {
            return;
        }

        if (requestBody[1].equals(ROOT)) {
            requestBody[1] = HOME_PAGE;
        }

        File page = new File(RESOURCES + requestBody[1]);

        if (!page.canRead() || page.isDirectory()) {
            page = new File(RESOURCES + NOT_FOUND);
        }

        byte[] pageData = readFileData(page);

        out.writeBytes(page.getPath().equals(RESOURCES + NOT_FOUND) ?
                "HTTP/1.0 404 Not Found\r\n" +
                        "Content-Type: " + FileExtensions.getContentType(requestBody[1]) + "\r\n" +
                        "Content-Length: " + page.length() + "\r\n" +
                        "\r\n"
                :
                getHeader(requestBody[1], (int) page.length()));

        out.write(pageData);
        out.flush();
    }

    private String getHeader(String requestBody, int pageLength) {
        String requestedFileExtension = requestBody.substring(requestBody.lastIndexOf("."));
        String contentType = FileExtensions.getContentType(requestedFileExtension);

        return "HTTP/1.0 200 Document Follows\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + pageLength + "\r\n" +
                "\r\n";

    }

    private byte[] readFileData(File file) throws IOException {
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream readFile = new FileInputStream(file);
        readFile.read(fileData);
        readFile.close();

        return fileData;
    }
}
