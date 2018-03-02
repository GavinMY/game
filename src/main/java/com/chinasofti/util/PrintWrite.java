package com.chinasofti.util;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.chinasofti.model.Result;

import net.sf.json.JSONObject;

/**
 * 页面输出
 * 
 * @author Season
 * @date 2014年2月27日11:41:22
 */
public class PrintWrite {
	
	/**
	 * 向页面输出JSON格式字符
	 * 
	 * @param response
	 * @param str
	 */
	public static void toJSON(HttpServletResponse response, String str){
		try {
			response.setContentType("application/json; charset=UTF-8");
			OutputStream out = response.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
			//String result = JSONUtil.getResult(SysConstant.STATE_FAILURE, str, "null");
			Result result = new Result(-1, str, null);
			JSONObject json=JSONObject.fromObject(result);
			writer.write(json.toString());
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向页面输出JSON格式字符
	 * 
	 * @param response
	 * @param str
	 */
	public static void toJSON_exist(HttpServletResponse response, String str){
		try {
			response.setContentType("application/json; charset=UTF-8");
			OutputStream out = response.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
			Result result = new Result(-1, str, null);
//			String result = JSONUtil.getResult(SysConstant.STATE_FAILURE1, str, "null");
			JSONObject json=JSONObject.fromObject(result);
			writer.write(json.toString());
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 向页面输出String格式字符
	 * 
	 * @param response
	 * @param str
	 */
	public static void toString(HttpServletResponse response, String str){
		try {
			response.setContentType("text/html;charset=utf-8");
			OutputStream out = response.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"utf-8"));
			writer.write(str);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
