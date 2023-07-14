package com.productmgt.ims.Exception;

public class ErrorResponse {
    private int statuscode;
    private String message;

    public ErrorResponse() {

    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(int statuscode, String message) {
        this.statuscode = statuscode;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(String message)
    {
        super();
        this.message = message;
    }

}
