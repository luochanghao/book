package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.service.BookService;
import com.util.PageUtil;

/**
 * 前台相关接口
 */
@Controller
@RequestMapping("/index")
public class IndexAction {

	@Autowired
	private BookService bookService;
	

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		request.setAttribute("specialList", bookService.getListIsSpecial(1, 2));
		request.setAttribute("newList", bookService.getListIsNews(1, 2));
		request.setAttribute("flag", 1);
		return "/index/index.jsp";
	}
	
	/**
	 * 简介
	 * @return
	 */
	@RequestMapping("/about")
	public String about(HttpServletRequest request){
		request.setAttribute("flag", 2);
		return "/index/about.jsp";
	}
	
	/**
	 * 精品推荐
	 * @return
	 */
	@RequestMapping("/special")
	public String special(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page){
		request.setAttribute("specialList", bookService.getListIsSpecial(page, 3));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, bookService.getTotalIsSpecial(), page, 3));
		request.setAttribute("flag", 3);
		return "/index/special.jsp";
	}
	
	/**
	 * 最新出版
	 * @return
	 */
	@RequestMapping("/new")
	public String news(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page){
		request.setAttribute("newsList", bookService.getListIsNews(page, 3));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, bookService.getTotalIsNews(), page, 3));
		request.setAttribute("flag", 4);
		return "/index/new.jsp";
	}
	
	/**
	 * 优惠促销
	 * @return
	 */
	@RequestMapping("/sale")
	public String sale(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page){
		request.setAttribute("saleList", bookService.getListIsSale(page, 3));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, bookService.getTotalIsSale(), page, 3));
		request.setAttribute("flag", 5);
		return "/index/sale.jsp";
	}
	
	/**
	 * 类目搜索
	 * @return
	 */
	@RequestMapping("/category")
	public String category(HttpServletRequest request, int categoryId,
			@RequestParam(required=false, defaultValue="1")int page){
		request.setAttribute("bookList", bookService.getListByCategoryId(categoryId, page, 12));
		request.setAttribute("pageTool", PageUtil.getPageTool(request, bookService.getTotalByCategoryId(categoryId), page, 12));
		return "/index/category.jsp";
	}
	
	/**
	 * 名称搜索
	 * @return
	 */
	@RequestMapping("/search")
	public String search(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="")String searchName,
			@RequestParam(required=false, defaultValue="1")int page){
		if (searchName!=null && !searchName.trim().isEmpty()) {
			request.setAttribute("bookList", bookService.getList(searchName, page, 12));
			request.setAttribute("pageTool", PageUtil.getPageTool(request, bookService.getTotal(searchName), page, 12));
		}
		return "/index/search.jsp";
	}
	
	/**
	 * 详情
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, int bookId){
		request.setAttribute("book", bookService.get(bookId));
		return "/index/detail.jsp";
	}

}
