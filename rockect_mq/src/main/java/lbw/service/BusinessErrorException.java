package lbw.service;

import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BusinessErrorException extends Exception {

    private static final long serialVersionUID = -7480022450501760611L;


    public BusinessErrorException(String message) {
        super(message);
    }

    public BusinessErrorException(String message, Exception e) {
        super(message, e);
    }
    // get set方法
}