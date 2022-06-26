package com.example.restreword.exception;

import com.example.restreword.common.ResponseTemplate;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        logger.info(ex.getClass().getName());

        return ResponseTemplate.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SettingNotFoundException.class)
    public final ResponseEntity<Object> handleSettingNotFoundExceptions(Exception ex, WebRequest request) {
        logger.info(ex.getClass().getName());

        return ResponseTemplate.fail(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // lỗi validate input người dùng
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final List<FieldNotValid> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new FieldNotValid(error.getField(),
                    String.format("%s %s", error.getField(), error.getDefaultMessage())));
        }
//        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
        ResponseTemplate<List<FieldNotValid>> responseTemplate =
                new ResponseTemplate<>(false, "Invalid form", errors);

        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    // thường dùng cho websocket, khi kết nối vào port đã có ứng dụng sử dụng
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return ResponseTemplate.fail(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    // lỗi khi dữ liệu người dùng nhập trong body khác với kiểu dữ liệu mà backend mong muốn
    // backend muốn id là số mà người dùng nhập là chữ
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        return ResponseTemplate.fail(error, HttpStatus.BAD_REQUEST);
    }

    // khi request không phải là multipart/form-data hoặc lỗi do backend không
    // được định cấu hình chính xác để xử lý các yêu cầu multipart
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final String error = ex.getRequestPartName() + " part is missing";
        return ResponseTemplate.fail(error, HttpStatus.BAD_REQUEST);
    }

    // thiếu paramenter trong requset ?a=&b=
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final String error = ex.getParameterName() + " parameter is missing";
        return ResponseTemplate.fail(error, HttpStatus.BAD_REQUEST);
    }

    // @PathVariable sai kiểu dữ liệu
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        return ResponseTemplate.fail(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());

        return ResponseTemplate.fail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        return ResponseTemplate.fail(error, HttpStatus.NOT_FOUND);
    }

    // 405
    // phương thức request không hỗ trợ
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        return ResponseTemplate.fail(builder.toString(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        return ResponseTemplate.fail(builder.toString(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // 500

//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
//        logger.info(ex.getClass().getName());
//
//        return ResponseTemplate.fail("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
