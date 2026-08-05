package com.ajaxjs.net.ftp;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Listener for tracking upload/download progress.
 */
@Data
@Slf4j
public class ProgressListener {
    /**
     * Name of the file being transferred.
     */
    private String fileName;

    /**
     * Number of bytes already read, in kilobytes.
     */
    private volatile long bytesRead;

    /**
     * Total content length, in kilobytes.
     */
    private volatile long contentLength;

    /**
     * Updates the progress with the current byte counts.
     *
     * @param aBytesRead     number of bytes already read
     * @param aContentLength total content length in bytes
     */
    public void update(long aBytesRead, long aContentLength) {
        bytesRead = aBytesRead / 1024L;
        contentLength = aContentLength / 1024L;
        // long megaBytes = aBytesRead / 1048576L;

        log.info("upload or download file: {}, size: {}/{}", fileName, aBytesRead, aContentLength);
    }

    /**
     * Copies data from the input stream to the output stream and reports progress.
     *
     * @param in   input stream to read from
     * @param out  output stream to write to
     * @param size expected total size of the data
     * @return total number of bytes copied
     */
    public long copy(InputStream in, OutputStream out, long size) {
        byte[] buffer = new byte[8192];
        long total = 0L;
        int res;

        try {
            while (true) {
                res = in.read(buffer);
                if (res == -1)
                    break;

                if (res > 0) {
                    total += res;

                    if (out != null) {
                        out.write(buffer, 0, res);
                        log.info("File size: {}, total: {}", size, total);
                        update(total, size);
                    }
                }
            }

            return total;
        } catch (IOException e) {
            return 0L;
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
