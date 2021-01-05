package ns.simple_web_server.files;

public enum FileExtensions {

    HTML(".html", "text/html; charset=UTF-8"),
    PNG(".png", "image/png"),
    JPEG(".jpeg", "image/jpeg"),
    GIF(".gif", "image/gif"),
    ICO(".ico", "image/vnd.microsoft.icon"),
    PDF(".pdf", "application/pdf"),
    CSS(".css", "text/css; charset=UTF-8"),
    MP3(".mp3", "audio/mpeg");

    private final String EXTENSION;
    private final String CONTENT_TYPE;

    FileExtensions(String extension, String contentType) {
        this.EXTENSION = extension;
        this.CONTENT_TYPE = contentType;
    }

    public static String getContentType(String extension) {
        for (FileExtensions value : values()) {
            if (extension.equals(value.EXTENSION)) {
                return value.CONTENT_TYPE;
            }
        }
        return "text/html; charset=UTF-8";
    }

}
