package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Indent;
import com.entity.User;
import com.service.BookService;
import com.service.IndentService;
import com.service.UserService;

/**
 * 用户相关接口
 */
@Controller
@RequestMapping("/index")
public class UserAction {
	
	private static final String indentKey = "indent";
	
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private IndentService indentService;

	
	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg(){
		return "/index/register.jsp";
	}
	
	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping("/register")
	public String register(HttpServletRequest request, User user){
		request.setAttribute("flag", 7);
		if (user.getUsername().isEmpty()) {
			request.setAttribute("msg", "用户名不能为空!");
			return "/index/register.jsp";
		}else if (userService.isExist(user.getUsername())) {
			request.setAttribute("msg", "用户名已存在!");
			return "/index/register.jsp";
		}else {
			userService.add(user);
			request.setAttribute("msg", "注册成功, 请登录!");
			return "/index/login.jsp";
		}
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/log")
	public String log() {
		return "/index/login.jsp";
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, User user) {
		request.setAttribute("flag", 6);
		if(userService.checkUser(user.getUsername(), user.getPassword())){
			request.getSession().setAttribute("username", user.getUsername());
			return "index";
		} else {
			request.setAttribute("msg", "用户名或密码错误!");
			return "/index/login.jsp";
		}
	}

	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("indent");
		return "/index/login.jsp";
	}
	
	
	/**
	 * 查看购物车
	 * @return
	 */
	@RequestMapping("/cart")
	public String cart(HttpServletRequest request) {
		Object username = request.getSession().getAttribute("username");
		if (username!=null && !username.toString().isEmpty()) {
			List<Indent> indentList = indentService.getListByUserid(userService.get(username.toString()).getId());
			if (indentList!=null && !indentList.isEmpty()) {
				request.setAttribute("indent", indentList.get(0)); // 最后一次订单信息
			}
		}
		return "/index/cart.jsp";
	}
	
	/**
	 * 购买
	 * @return
	 */
	@RequestMapping("/buy")
	public @ResponseBody String buy(HttpServletRequest request, int bookId){
		Indent indent = (Indent) request.getSession().getAttribute(indentKey);
		if (indent==null) {
			request.getSession().setAttribute(indentKey, indentService.create(bookService.get(bookId)));
		}else {
			request.getSession().setAttribute(indentKey, indentService.addItem(indent, bookService.get(bookId)));
		}
		return "ok";
	}
	
	/**
	 * 减少
	 */
	@RequestMapping("/lessen")
	public @ResponseBody String lessen(HttpServletRequest request, int bookId){
		Indent indent = (Indent) request.getSession().getAttribute(indentKey);
		if (indent != null) {
			request.getSession().setAttribute(indentKey, indentService.lessenItem(indent, bookService.get(bookId)));
		}
		return "ok";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public @ResponseBody String delete(HttpServletRequest request, int bookId){
		Indent indent = (Indent) request.getSession().getAttribute(indentKey);
		if (indent != null) {
			request.getSession().setAttribute(indentKey, indentService.deleteItem(indent, bookService.get(bookId)));
		}
		return "ok";
	}
	
	/**
	 * 提交订单
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Indent indent){
		Object username = request.getSession().getAttribute("username");
		if (username==null || username.toString().isEmpty()) {
			request.setAttribute("msg", "请登录后提交订单!");
			return "/index/login.jsp";
		}
		Indent indentSession = (Indent) request.getSession().getAttribute(indentKey);
		User user = userService.get(username.toString());
		indentSession.setUserId(user.getId());
		indentSession.setName(indent.getName());
		indentSession.setPhone(indent.getPhone());
		indentSession.setAddress(indent.getAddress());
		indentService.add(indentSession);	// 保存订单
		request.getSession().removeAttribute(indentKey);	// 清除购物车
		request.setAttribute("msg", "提交订单成功!");
		return "/index/cart.jsp";
	}
	
	/**
	 * 查看订单
	 * @return
	 */
	@RequestMapping("/order")
	public String order(HttpServletRequest request){
		Object username = request.getSession().getAttribute("username");
		if (username==null || username.toString().isEmpty()) {
			request.setAttribute("msg", "请登录后提交订单!");
			return "login";
		}
		request.setAttribute("indentList", indentService.getListByUserid(userService.get(username.toString()).getId()));
		return "/index/order.jsp";
	}
	
}
