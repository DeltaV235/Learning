package com.wuyue.crowd.mvc.config;

import com.google.gson.Gson;
import com.wuyue.util.CrowdUtil;
import com.wuyue.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wuyue.util.CrowdConstant.EXCEPTION;

/**
 * @author DeltaV235
 * @version 1.0
 * @className CrowdExceptionResolver
 * @description
 * @date 2020/5/6 16:47
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(Exception.class)
    public ModelAndView resolveNullPointerException(Exception exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, exception, request, response);
    }

    private ModelAndView commonResolve(String viewName,
                                       Exception exception,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        boolean isAjax = CrowdUtil.judgeRequestType(request);
        if (isAjax) {
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());
            String json = new Gson().toJson(failed);
            response.getWriter().write(json);
            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION.getStrConstant(), exception);
        modelAndView.setViewName(viewName);
        return modelAndView;

    }
}
