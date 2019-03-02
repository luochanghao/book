package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.entity.User;
import com.util.SafeUtil;

/**
 * 用户相关服务
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	
	/**
	 * 验证用户密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password){
		return userDao.selectByUsernameAndPassword(username, SafeUtil.encode(password)) != null;
	}

	/**
	 * 用户是否存在
	 * @param username
	 * @return
	 */
	public boolean isExist(String username) {
		return userDao.selectByUsername(username) != null;
	}

	/**
	 * 添加
	 * @param user
	 * @return
	 */
	public boolean add(User user) {
		user.setPassword(SafeUtil.encode(user.getPassword()));
		return userDao.insert(user);
	}
	
	/**
	 * 通过id获取
	 * @param userid
	 * @return
	 */
	public User get(int userid){
		return userDao.select(userid);
	}
	
	/**
	 * 通过username获取
	 * @param username
	 * @return
	 */
	public User get(String username){
		return userDao.selectByUsername(username);
	}
	
	/**
	 * 列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<User> getList(int page, int size) {
		return userDao.selectList((page-1)*size, size);
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal() {
		return userDao.selectTotal();
	}

	/**
	 * 更新
	 * @param user
	 */
	public boolean update(User user) {
		return userDao.update(user);
	}

	/**
	 * 删除
	 * @param user
	 */
	public boolean delete(int id) {
		return userDao.delete(id);
	}
	
}
