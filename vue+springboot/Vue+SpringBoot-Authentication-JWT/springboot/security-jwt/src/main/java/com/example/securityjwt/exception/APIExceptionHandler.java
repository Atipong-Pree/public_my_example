package com.example.securityjwt.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {


    @ResponseBody  @ExceptionHandler(APIException.class)
    public ResponseEntity<APIExceptionResponseBody> handleServicesException(HttpServletRequest request , APIException apiEx){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        APIExceptionResponseBody apiExceptionResponseBody = new APIExceptionResponseBody();
        apiExceptionResponseBody.setHttpStatus(httpStatus);
        apiExceptionResponseBody.setTimestamp(new Date());
        apiExceptionResponseBody.setMessage(apiEx.getMessage());
        ResponseEntity<APIExceptionResponseBody> responseEntity = new ResponseEntity<>(apiExceptionResponseBody ,httpStatus);
        return responseEntity;

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.resolve(code);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }


}
