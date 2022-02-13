package jclipper.springboot.exception;

import jclipper.common.base.R;
import jclipper.common.enums.BaseErrorCode;
import jclipper.common.enums.CommonErrorCode;
import jclipper.common.enums.CustomErrorCode;
import jclipper.common.exception.*;
import jclipper.common.utils.CollectionUtils;
import jclipper.common.utils.HttpUtils;
import jclipper.common.utils.RemoteIpHelper;
import jclipper.springboot.alert.base.AlertNotice;
import jclipper.springboot.alert.base.NoticeMessage;
import jclipper.springboot.exception.configuration.ErrorAlertProperties;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 统一异常处理
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/4/21 13:57.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private AlertNotice alertNotice;

    @Value("${spring.application.name:unknown}")
    private String appName;

    @Resource
    private ErrorAlertProperties errorAlertProperties;

    private static final String KLOCK_TIMEOUT_EXCEPTION_NAME = "org.springframework.boot.autoconfigure.klock.handler.KlockTimeoutException";


    /**
     * Feign接口调用移除处理
     */
    @ExceptionHandler(value = FeignKeepErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity feignKeepErrorHandler(FeignKeepErrorException e) {
        HttpHeaders headers = new HttpHeaders();
        Map<String, Collection<String>> map = e.getHeaders();
        if (map != null) {
            map.forEach((k, v) -> {
                if (CollectionUtils.isNotEmpty(v)) {
                    headers.addAll(k, Lists.newArrayList(v));
                }
            });
        }
        return new ResponseEntity(e.getError(), headers, HttpStatus.valueOf(e.getHttpCode()));

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity defaultHandler(HttpServletRequest request, Exception e) {

        String trackId = recordRequestInfo(request);

        String message = getLocaleMessage(CommonErrorCode.SYSTEM_ERROR.getName());
        if (log.isErrorEnabled()) {
            log.error(String.format("[%s]%s", trackId, message), e);
        }
        if (e.getClass().getName().equals(KLOCK_TIMEOUT_EXCEPTION_NAME)) {
            return ResponseEntity.status(HttpStatus.LOCKED).body(R.error(CommonErrorCode.RESOURCE_LOCKED));
        }
        printWarnMessage(request, CommonErrorCode.SYSTEM_ERROR, e, true);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error(CommonErrorCode.SYSTEM_ERROR.getCode(), message));
    }

    @ExceptionHandler(value = SystemErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R systemErrorHandler(HttpServletRequest request, SystemErrorException e) {

        String message = null;
        BaseErrorCode error = e.getError();
        if (error != null) {
            message = error.getName();
        }
        if (message == null) {
            message = CommonErrorCode.SYSTEM_ERROR.getName();
        }
        printWarnMessage(request, e.getError(), e, true);
        return R.error(CommonErrorCode.SYSTEM_ERROR.getCode(), message);
    }

    @ExceptionHandler(value = {
            UnauthorizedException.class,
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R UnauthorizedHandler(HttpServletRequest request, UnauthorizedException e) {
        BaseErrorCode error = e.getError();
        int code = error.getCode();
        printWarnMessage(request, error, e, true);
        return R.error(code, getLocaleMessage(error.getName()));
    }

    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R ForbiddenHandler(HttpServletRequest request, ForbiddenException e) {

        printWarnMessage(request, e.getError(), e, false);
        return R.error(e.getError().getCode(), getLocaleMessage(e.getError().getName()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R MethodNotSupportedHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        BaseErrorCode errorCodeEnum = CommonErrorCode.METHOD_NOT_SUPPORTED;
        printWarnMessage(request, errorCodeEnum, e, false);
        return R.error(errorCodeEnum.getCode(), getLocaleMessage(errorCodeEnum.getName()));
    }

    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R httpMessageNotReadableParseExceptionHandler(HttpServletRequest request, HttpMessageNotReadableException e) {
        printWarnMessage(request, new CustomErrorCode(CommonErrorCode.REQUEST_PARAM_ERROR.getCode(), e.getMessage()), e, true);
        return R.error("参数格式错误，请检查");
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            DateTimeParseException.class,
            BindException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R argumentNotValidHandler(HttpServletRequest request, Exception e) {

        recordRequestInfo(request);

        CustomErrorCode errorCodeEnum = CustomErrorCode.wrapper(CommonErrorCode.REQUEST_PARAM_ERROR);

        BindingResult bindResult = null;
        if (e instanceof BindException) {
            bindResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        String msg = null;
        if (bindResult != null && bindResult.hasErrors()) {
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();

        }
        if (e instanceof DateTimeParseException) {
            DateTimeParseException pe = (DateTimeParseException) e;
            msg = String.format("参数'%s'转换格式错误", pe.getParsedString());
        }
        if (StringUtils.isEmpty(msg)) {
            msg = errorCodeEnum.getName();
        } else {
            errorCodeEnum.setName(msg);
        }
        printWarnMessage(request, errorCodeEnum, e, errorAlertProperties.isAlertWhenBizCode());

        return R.error(errorCodeEnum.getCode(), getLocaleMessage(msg));
    }

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R constraintViolationHandler(HttpServletRequest request, Exception e) {

        recordRequestInfo(request);

        BaseErrorCode errorCodeEnum = CommonErrorCode.REQUEST_PARAM_ERROR;
        String msg = "请求参数不符合";
        Optional<ConstraintViolation<?>> first = ((ConstraintViolationException) e).getConstraintViolations().stream().findFirst();
        if (first.isPresent()) {
            msg = first.get().getMessage();
        }

        printWarnMessage(request, errorCodeEnum, e, errorAlertProperties.isAlertWhenBizCode());
        return R.error(errorCodeEnum.getCode(), getLocaleMessage(msg));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MultipartException.class)
    public R resolveFileUploadException(HttpServletRequest request, MultipartException e) {
        printWarnMessage(request, CommonErrorCode.FILE_OVER_SIZE, e, errorAlertProperties.isAlertWhenBizCode());
        return R.error(CommonErrorCode.FILE_OVER_SIZE.getName());
    }


    @ExceptionHandler(value = BusinessRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R BadRequestHandler(HttpServletRequest request, BadRequestException e) {

        BaseErrorCode error = e.getError();
        R r = R.error(error.getCode(), getLocaleMessage(error.getName()));
        printWarnMessage(request, error, e, errorAlertProperties.isAlertWhenBizCode());

        return r;
    }

    private String recordRequestInfo(HttpServletRequest request) {
        String trackId = (String) request.getAttribute("trackId");
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String headers = HttpUtils.GSON.toJson(HttpUtils.getHeaders(request));

        if (StringUtils.isNotBlank(queryString)) {
            uri = uri + "?" + queryString;
        }

        if (log.isInfoEnabled()) {
            log.info(String.format("[%s]Request：%s %s；header: %s", trackId, request.getMethod(), uri, headers));
        }

        return trackId;
    }

    private String getLocaleMessage(String resource) {
        return resource;

//        String lang = ThreadLocalUtils.getLocale();
//        try {
//            return this.messageSource.getMessage(resource, null, new Locale(lang));
//        } catch (Exception e) {
//            return resource;
//        }


    }

    private void printWarnMessage(HttpServletRequest request, BaseErrorCode errorCode, Exception e, boolean isDingTalk) {

        String trackId = request.getHeader(HttpUtils.TRACE_ID_KEY);

        if (log.isErrorEnabled()) {
            String message = String.format("[%s]%s:%s[%s]",
                    trackId,
                    errorCode.getCode(),
                    errorCode.getName(),
                    e.getClass().getName()
            );
            log.error(message, e);
        }
        if (!isDingTalk) {
            return;
        }

        if (alertNotice.enabled()) {
            NoticeMessage message = new NoticeMessage();
            String host = request.getLocalAddr() + ":" + request.getServerPort();
            message.setAppName(appName);
            message.setEnv(errorAlertProperties.getEnv());
            message.setHost(host);
            message.setTime(LocalDateTime.now());
            message.setClient(RemoteIpHelper.getRemoteIpFrom(request));
            message.setTraceId(trackId);
            message.setCode(errorCode.getCode()+"");
            message.setError(e.getMessage());
            message.setMessage(errorCode.getName());
            message.setUrl(request.getRequestURI());
            alertNotice.sendAsync(message);
        }
    }
}
