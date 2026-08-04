package com.ajaxjs.net.ftp;

import com.ajaxjs.net.ftp.sun.TelnetInputStream;
import com.ajaxjs.net.ftp.sun.TelnetOutputStream;
import com.ajaxjs.net.ftp.sun.ftp.FtpClient;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;

/**
 * FTP file upload and download helper.
 */
@Slf4j
public class UploadFtp extends FtpClient {
    /**
     * Creates a new FTP client connected to the specified server and port.
     *
     * @param server FTP server host name or IP address
     * @param port   FTP server port
     * @throws IOException if the connection cannot be established
     */
    public UploadFtp(String server, int port) throws IOException {
        super(server, port);
    }

    /**
     * Uploads a local file to the FTP server.
     *
     * @param source local file path to upload
     * @param target destination path on the FTP server
     */
    public void upload(String source, String target) {
        try {
            binary();

            try (TelnetOutputStream ftp = put(target);
                 InputStream file = Files.newInputStream(new File(source).toPath())) {
                BufferedInputStream in = new BufferedInputStream(file);

                new ProgressListener().copy(in, new BufferedOutputStream(ftp), in.available());
                log.info("Put file from {} to {}", source, target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloads a file from the FTP server to a local file.
     *
     * @param source path and file name on the FTP server
     * @param target local path to save the downloaded file
     */
    public void getFile(String source, String target) {
        try {
            binary();

            try (TelnetInputStream ftp = get(source);
                 OutputStream file = Files.newOutputStream(new File(target).toPath())) {

                ProgressListener listener = new ProgressListener();
                listener.setFileName(target);
                listener.copy(new BufferedInputStream(ftp), new BufferedOutputStream(file), getFileSize(source, ftp));

                log.info("Get file from {} to {}", source, target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the size of the specified file from the FTP server to calculate
     * download progress and percentage.
     *
     * @param source path and file name on the FTP server
     * @param ftp    FTP input stream for the file
     * @return file size in bytes, or 0 if it cannot be determined
     * @throws IOException if a communication error occurs
     */
    private int getFileSize(String source, TelnetInputStream ftp) throws IOException {
        sendServer("SIZE " + source + "\r\n");// sendServer followed by readServerResponse is required

        if (readServerResponse() == 213) {
            String msg = getResponseString();

            try {
                return Integer.parseInt(msg.substring(3).trim());
            } catch (Exception e) {
            }
        }

        return 0;
    }
}
