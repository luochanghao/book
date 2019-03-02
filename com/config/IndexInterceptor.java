package com.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.service.BookService;
import com.service.CategoryService;

/**
 * 前台拦截器
 */
public class IndexInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute("categoryList", categoryService.getList());
		request.setAttribute("saleList", bookService.getListIsSale(1, 2));
		return true;
	}

}
