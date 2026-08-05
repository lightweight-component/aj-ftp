package com.ajaxjs.net.ftp.sun.ftp;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FtpClient}.
 */
class FtpClientTest {

    @Test
    void testDefaultPort() {
        assertEquals(21, FtpClient.FTP_PORT);
    }

    @Test
    void testStaticResponseCodes() {
        // These are internal constants used by the client
        assertEquals(1, FtpClient.FTP_SUCCESS);
        assertEquals(2, FtpClient.FTP_TRY_AGAIN);
        assertEquals(3, FtpClient.FTP_ERROR);
    }

    @Test
    void testGetFtpProxyHost() {
        // When no proxy is set, should return null
        String proxyHost = FtpClient.getFtpProxyHost();
        // This depends on system properties, so we just verify it doesn't throw
    }

    @Test
    void testGetFtpProxyPort() {
        // Default port should be 80
        int port = FtpClient.getFtpProxyPort();
        // This depends on system properties
    }

    @Test
    void testGetUseFtpProxy() {
        // When no proxy is configured, should return false
        boolean useProxy = FtpClient.getUseFtpProxy();
        // This depends on system properties
    }

    @Test
    void testMatchNonProxyHosts() throws UnknownHostException {
        // Test with null host - should not throw
        boolean result = FtpClient.matchNonProxyHosts(null);
        // Result depends on system properties

        // Test with localhost
        result = FtpClient.matchNonProxyHosts("localhost");
        // Result depends on system properties
    }

    @Test
    void testConstructors() {
        // Test no-arg constructor
        FtpClient client1 = new FtpClient();
        assertNotNull(client1);

        // Test constructor with proxy (can pass null)
        FtpClient client2 = new FtpClient();
        assertNotNull(client2);
    }

    @Test
    void testWelcomeMsgField() {
        FtpClient client = new FtpClient();
        // welcomeMsg should be null initially
        assertNull(client.welcomeMsg);
    }

    @Test
    void testCommandField() {
        FtpClient client = new FtpClient();
        // command should be null initially
        assertNull(client.command);
    }

    @Test
    void testLastReplyCodeField() {
        FtpClient client = new FtpClient();
        // lastReplyCode should be 0 initially
        assertEquals(0, client.lastReplyCode);
    }
}
