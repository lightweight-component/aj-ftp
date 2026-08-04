package com.ajaxjs.net.ftp.sun.misc;

/**
 * A class to signal exception from the RegexpPool class.
 *
 * @author James Gosling
 */
public class REException extends Exception {
    /**
     * Serial version UID for serialization.
     */
    private static final long serialVersionUID = 4656584872733646963L;

    /**
     * Constructs an REException with the specified detail message.
     *
     * @param s detail message
     */
    REException(String s) {
        super(s);
    }
}