package jclipper.springboot.exception;

import jclipper.common.base.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/6/8 17:20.
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public class GlobalErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public R handle(HttpServletResponse response) {
        R baseResponse = null;
        if (log.isErrorEnabled()) {
            log.error("GlobalExceptionHandler中捕获除了500之外的所有状态码: {}", response.getStatus());
        }
        baseResponse = R.error(response.getStatus());
        //其他的的各种问题均转化为状态置为200
        response.setStatus(HttpStatus.OK.value());
        return baseResponse;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
