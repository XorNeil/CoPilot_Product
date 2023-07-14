package com.productmgt.ims.Exception;

public class ProductNameAlreadyExistsException extends RuntimeException{
    private String msg="";
    static final long serialVersionUID = 1L;
    public ProductNameAlreadyExistsException(String message) {
        super(message);
        msg=message;
    }
}
