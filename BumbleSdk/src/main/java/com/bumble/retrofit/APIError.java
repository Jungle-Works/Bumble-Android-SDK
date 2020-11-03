package com.bumble.retrofit;

/**
 * APIError
 */
public class APIError {

    private int statusCode;
    private int code;
    private String message;
    private String error;
    private String error_message;
    private String status;
    private Object data;
    private int type;

    /**
     * @param statusCode
     * @param message
     */
    public APIError(int statusCode, String message, int type) {
        this.message = message;
        this.statusCode = statusCode;
        this.type=type;
    }


    /**
     * @return
     */
    public int getStatusCode() {
        if (statusCode == 0)
            if (code != 0)
                return code;
            else
                statusCode = 900;
        return statusCode;
    }

    /**
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * @return
     */
    public String getMessage() {
        if (message == null) {
            if (error != null)
                return error;
            else if (error_message != null)
                return error_message;
            else
                return ResponseResolver.UNEXPECTED_ERROR_OCCURRED;
        } else
            return message;
    }


    /**
     * @return
     */
    public boolean isEmptyObject() {
        if (statusCode == 0 && code == 0 && message == null && error == null && error_message == null && status == null && data == null)
            return true;
        else
            return false;
    }
}
