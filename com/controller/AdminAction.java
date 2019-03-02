package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Admin;
import com.entity.Book;
import com.entity.Category;
import com.entity.User;
import com.service.AdminService;
import com.service.BookService;
import com.service.CategoryService;
import com.service.IndentService;
import com.service.UserService;
import com.util.PageUtil;
import com.util.SafeUtil;

/**
 * 后台管理相关接口
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {

	private static final int size = 10;
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private IndentService indentService;
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	
    
	/**
	 * 管理员登录
	 * @return
	 */
    @RequestMapping("/login")
	public String login(Admin admin, HttpServletRequest request) {
		if (adminService.checkUser(admin.getUsername(), admin.getPassword())) {
			request.getSession().setAttribute("admin", admin.getUsername());
			return "/admin/main.jsp";
		}
		request.setAttribute("msg", "用户名或密码错误!");
		return "/admin/login.jsp";
	}
    
    /**
     * 管理员注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
    	request.getSession().removeAttribute("admin");
    	return "/admin/login.jsp";
    }
	
	
	/**
	 * 订单列表
	 * @return
	 */
    @RequestMapping("/indentList")
	public String indentList(byte status, HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page){
    	request.setAttribute("indentList", indentService.getList(status, page, size));
    	request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, indentService.getTotal(status), page, size));
		return "/admin/pages/indent-list.jsp";
	}
	
	/**
	 * 订单处理
	 * @return
	 */
    @RequestMapping("/indentDispose")
	public String indentDispose(int id){
		indentService.dispose(id);
		return "redirect:indentList";
	}
	
	/**
	 * 订单删除
	 * @return
	 */
    @RequestMapping("/indentDelete")
	public String indentDelete(int id){
		indentService.delete(id);
		return "redirect:indentList";
	}
	
	/**
	 * 订单项列表
	 * @return
	 */
    @RequestMapping("/itemList")
	public String itemList(int id, HttpServletRequest request){
    	request.setAttribute("itemList", indentService.getItemList(id));
		return "/admin/pages/item-list.jsp";
	}
	
	
	/**
	 * 顾客管理
	 * @return
	 */
    @RequestMapping("/userList")
	public String userList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1")int page){
    	request.setAttribute("userList", userService.getList(page, size));
    	request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, userService.getTotal(), page, size));
		return "/admin/pages/user-list.jsp";
	}
	
	/**
	 * 顾客添加
	 * @return
	 */
    @RequestMapping("/userAd")
	public String userAd(){
		 return "/admin/pages/user-add.jsp";
	}
    
    /**
     * 顾客添加
     * @return
     */
    @RequestMapping("/userAdd")
    public String userAdd(User user, HttpServletRequest request){
    	if (userService.isExist(user.getUsername())) {
    		request.setAttribute("msg", "用户名已存在!");
    		return "/admin/pages/user-add.jsp";
    	}
    	userService.add(user);
    	return "redirect:userList";
    }
	
	/**
	 * 顾客密码重置页面
	 * @return
	 */
    @RequestMapping("/userRe")
	public String userRe(int id, HttpServletRequest request){
    	request.setAttribute("user", userService.get(id));
		return "/admin/pages/user-reset.jsp";
	}
	
	/**
	 * 顾客密码重置
	 * @return
	 */
    @RequestMapping("/userReset")
	public String userReset(User user){
		String password = SafeUtil.encode(user.getPassword());
		user = userService.get(user.getId());
		user.setPassword(password);
		userService.update(user);
		return "redirect:userList";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
    @RequestMapping("/userUp")
	public String userUp(int id, HttpServletRequest request){
    	request.setAttribute("user", userService.get(id));
		return "/admin/pages/user-update.jsp";
	}
	
	/**
	 * 顾客更新
	 * @return
	 */
    @RequestMapping("/userUpdate")
	public String userUpdate(User user){
		userService.update(user);
		return "redirect:userList";
	}
	
	/**
	 * 顾客删除
	 * @return
	 */
    @RequestMapping("/userDelete")
	public String userDelete(int id){
		userService.delete(id);
		return "redirect:userList";
	}
	
	
	/**
	 * 图书列表
	 * @return
	 */
    @RequestMapping("/bookList")
	public String bookList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="0")int status, 
			@RequestParam(required=false, defaultValue="1")int page){
    	if(status == 0) {
    		request.setAttribute("bookList", bookService.getList(page, size));
    		request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, bookService.getTotal(), page, size));
    	}else if(status == 1) {
    		request.setAttribute("bookList", bookService.getListIsSpecial(page, size));
    		request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, bookService.getTotalIsSpecial(), page, size));
    	}else if (status == 2) {
    		request.setAttribute("bookList", bookService.getListIsNews(page, size));
    		request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, bookService.getTotalIsNews(), page, size));
		}else if (status == 3) {
    		request.setAttribute("bookList", bookService.getListIsSale(page, size));
    		request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, bookService.getTotalIsSale(), page, size));
		}
		return "/admin/pages/book-list.jsp";
	}
	
	/**
	 * 图书添加
	 * @return
	 */
    @RequestMapping("/bookAd")
	public String bookAd(HttpServletRequest request){
    	request.setAttribute("categoryList", categoryService.getList());
		return "/admin/pages/book-add.jsp";
	}
	
	/**
	 * 图书添加
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping("/bookAdd")
	public String bookAdd(Book book) {
		bookService.add(book);
		return "redirect:bookList";
	}
	
	/**
	 * 图书更新
	 * @return
	 */
    @RequestMapping("/bookUp")
	public String bookUp(HttpServletRequest request, int id){
    	request.setAttribute("categoryList", categoryService.getList());
    	request.setAttribute("book", bookService.get(id));
		return "/admin/pages/book-update.jsp";
	}
	
	/**
	 * 图书更新
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping("/bookUpdate")
	public String bookUpdate(Book book) {
		bookService.update(book);
		return "redirect:bookList";
	}
	
	/**
	 * 图书删除
	 * @return
	 */
    @RequestMapping("/bookDelete")
	public String bookDelete(int id){
		bookService.delete(id);
		return "redirect:bookList";
	}
	
	/**
	 * 图书设置
	 * @return
	 */
    @RequestMapping("/bookSet")
	public String bookSet(int id, byte flag){
		Book book = bookService.get(id);
		switch (flag) {
		case 10:
			book.setSpecial(false);
			break;
		case 11:
			book.setSpecial(true);
			break;
		case 20:
			book.setNews(false);
			break;
		case 21:
			book.setNews(true);
			break;
		case 30:
			book.setSale(false);
			break;
		case 31:
			book.setSale(true);
			break;
		}
		bookService.update(book);
		return "redirect:bookList";
	}
	
	
	/**
	 * 类目列表
	 * @return
	 */
    @RequestMapping("/categoryList")
	public String categoryList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1")int page){
    	request.setAttribute("categoryList", categoryService.getList(page, size));
    	request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, categoryService.getTotal(), page, size));
		return "/admin/pages/category-list.jsp";
	}
	
	/**
	 * 类目添加
	 * @return
	 */
    @RequestMapping("/categoryAd")
	public String categoryAd(){
		return "/admin/pages/category-add.jsp";
	}
    
    /**
     * 类目添加
     * @return
     */
    @RequestMapping("/categoryAdd")
    public String categoryAdd(Category category){
    	categoryService.add(category);
    	return "redirect:categoryList";
    }
	
	/**
	 * 类目更新
	 * @return
	 */
    @RequestMapping("/categoryUp")
	public String categoryUp(HttpServletRequest request, int id){
    	request.setAttribute("category", categoryService.get(id));
		return "/admin/pages/category-update.jsp";
	}
	
	/**
	 * 类目更新
	 * @return
	 */
    @RequestMapping("/categoryUpdate")
	public String categoryUpdate(Category category){
		categoryService.update(category);
		return "redirect:categoryList";
	}
	
	/**
	 * 类目删除
	 * @return
	 */
    @RequestMapping("/categoryDelete")
	public String categoryDelete(int id){
		categoryService.delete(id);
		return "redirect:categoryList";
	}
	
	
	/**
	 * 管理员列表
	 * @return
	 */
    @RequestMapping("/adminList")
	public String adminList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1")int page){
    	request.setAttribute("adminList", adminService.getList(page, size));
    	request.setAttribute("pageTool", PageUtil.getPageToolAdmin(request, adminService.getTotal(), page, size));
		return "/admin/pages/admin-list.jsp";
	}
	
	/**
	 * 管理员添加
	 * @return
	 */
    @RequestMapping("/adminAd")
	public String adminAd(){
		return "/admin/pages/admin-add.jsp";
	}
    
    /**
     * 管理员添加
     * @return
     */
    @RequestMapping("/adminAdd")
    public String adminAdd(HttpServletRequest request, Admin admin){
    	if (adminService.isExist(admin.getUsername())) {
    		request.setAttribute("msg", "用户名已存在!");
    		return "adminadd";
    	}
    	adminService.add(admin);
    	return "redirect:adminList";
    }
	
	/**
	 * 重置密码页面
	 * @return
	 */
    @RequestMapping("/adminRe")
	public String adminRe(HttpServletRequest request, int id){
    	request.setAttribute("admin", adminService.get(id));
		return "/admin/pages/admin-reset.jsp";
	}
	
	/**
	 * 重置密码
	 * @return
	 */
    @RequestMapping("/adminReset")
	public String adminReset(Admin admin){
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminService.update(admin);
		return "redirect:adminList";
	}
	
	/**
	 * 管理员删除
	 * @return
	 */
    @RequestMapping("/adminDelete")
	public String adminDelete(int id){
		adminService.delete(id);
		return "redirect:adminList";
	}

}
