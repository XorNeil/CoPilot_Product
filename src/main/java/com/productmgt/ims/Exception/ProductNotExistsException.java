package com.productmgt.ims.Exception;

public class ProductNotExistsException extends RuntimeException{

    private String msg="";
    static final long serialVersionUID = 1L;
    public ProductNotExistsException(String message) {
        super(message);
        msg=message;
    }

}
