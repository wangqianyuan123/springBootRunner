package com.victor.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.victor.model.ErrorInfo;
import com.victor.model.MyDataException;
import com.victor.model.MyPageException;

/**
 * @ControllerAdvice统一定义不同Exception映射到不同错误处理页面。
 * 而当我们要实现RESTful API时，返回的错误是JSON格式的数据，而不是HTML页面，
 * 这时候我们也能轻松支持。本质上，只需在@ExceptionHandler之后加入@ResponseBody
 * 就能让处理函数return的内容转换为JSON格式。
 * @author victor
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 返回json格式  不需要返回界面
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
    @ExceptionHandler(value = MyDataException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MyDataException e) throws Exception {
        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
    
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 直接与前端交互 返回错误的提示界面
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyPageException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, MyPageException e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageException", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

}
