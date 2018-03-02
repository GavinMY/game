package com.chinasofti.sys;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.orm.hibernate4.HibernateJdbcException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.chinasofti.exception.UserExistException;
import com.chinasofti.exception.ValidateException;
import com.chinasofti.util.PrintWrite;



/**
 * Spring 全局异常处理类
 * 
 * @author Season
 * @date 2014年2月25日20:25:39
 */
@Component
public class SpringExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		// 请求是否是页面
		boolean isPage = false;
		// 是否打印StackTrace
		boolean pringStackTrace = true;
		boolean flag=false;
		String resultStr = "系统错误，请与管理员联系！";
		
		if(exception instanceof ValidateException){
			resultStr = exception.getMessage();
			//resultStr = PropertyUtil.getProperty(resultStr);
			pringStackTrace = false;
		}else if(exception instanceof UserExistException){
			resultStr = exception.getMessage();
			//resultStr = PropertyUtil.getProperty(resultStr);
			pringStackTrace = false;
			flag = true;
		}else if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 根据@ResponseBody注解来判断返回是否是页面
			if(handlerMethod.getMethod().getAnnotation(ResponseBody.class) == null){
				isPage = true;
			}
		}
		if(exception instanceof HibernateJdbcException){		// Hibernate数据库异常
			resultStr = "数据库执行错误，错误代码：ORA-" + ((HibernateJdbcException) exception).getSQLException().getErrorCode();
		}else if(exception instanceof ConstraintViolationException){		// 验证异常处理
			resultStr = "";
			pringStackTrace = false;
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				resultStr += constraintViolation.getMessage();
			}
		}else if(exception instanceof CannotCreateTransactionException){
			resultStr = "连接数据库错误，请与管理员联系。";
		}
		if(pringStackTrace){
			exception.printStackTrace();
    	}
		if(!isPage){
			if(flag){
				PrintWrite.toJSON_exist(response, resultStr);
			}else{
				PrintWrite.toJSON(response, resultStr);
			}
			
		}else{
			return new ModelAndView("error", "exception" , resultStr);
		}
		return new ModelAndView();
	}

	
//	遍历错误集合
//	Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception).getConstraintViolations();
//	for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//	}

}
