package com.ajaxjs.net.ftp;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;

/**
 * Test class for FTP client functionality.
 */
public class TestFTP {
    /**
     * Tests file upload to FTP server.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testUpload() throws IOException {
        String localZip = getTestResourceFilePath("re.zip");
        UploadFtp client = new UploadFtp("test.rebex.net", 21);
        client.login("demo", "password");
        client.upload(localZip, "/upload/re.zip");
        client.closeServer();
    }

    /**
     * Tests file download from FTP server.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testDownload() throws IOException {
        UploadFtp ftp = new UploadFtp("test.rebex.net", 21);
        ftp.login("demo", "password");
        ftp.getFile("/readme.txt", "c:\\temp\\readme.txt");
    }

    private static String getTestResourceFilePath(String resourceName) throws IOException {
        ClassLoader cl = TestFTP.class.getClassLoader();
        URL url = cl.getResource(resourceName);
        if (url == null)
            throw new IOException("Test resource not found: " + resourceName);

        if ("file".equalsIgnoreCase(url.getProtocol())) {
            try {
                System.out.println("file");
                return Paths.get(url.toURI()).toString();
            } catch (Exception e) {
                throw new IOException("Cannot resolve test resource path: " + resourceName, e);
            }
        }

        try (InputStream in = cl.getResourceAsStream(resourceName)) {
            if (in == null)
                throw new IOException("Test resource not found: " + resourceName);

            Path tmp = Files.createTempFile("aj-ftp-", "-" + resourceName);
            tmp.toFile().deleteOnExit();
            Files.copy(in, tmp, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return tmp.toString();
        }
    }
}
