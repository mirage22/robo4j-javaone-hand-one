package com.javaone.number42.fronthand;

/**
 * @author Miro Kopecky (@miragemiko)
 * @since 03.07.2016
 */
public class ClientFrontHandException extends RuntimeException{

    public ClientFrontHandException(String message) {
        super(message);
    }

    public ClientFrontHandException(String message, Throwable cause) {
        super(message, cause);
    }

}
