package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AdminDao;
import com.entity.Admin;
import com.util.SafeUtil;

/**
 * 管理员相关服务
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	/**
	 * 验证用户密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password){
		return Objects.nonNull(adminDao.selectByUsernameAndPassword(username, SafeUtil.encode(password)));
	}
	
	/**
	 * 是否存在
	 * @param username
	 * @return
	 */
	public boolean isExist(String username) {
		return Objects.nonNull(adminDao.selectByUsername(username));
	}

	/**
	 * 列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Admin> getList(int page, int size) {
		return adminDao.selectList((page-1)*size, size);
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal() {
		return adminDao.selectTotal();
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Admin get(int id) {
		return adminDao.select(id);
	}
	
	/**
	 * 添加
	 * @param admin
	 */
	public Integer add(Admin admin) {
		admin.setPassword(SafeUtil.encode(admin.getPassword()));
		adminDao.insert(admin);
		return admin.getId();
	}
	
	/**
	 * 更新
	 * @param user
	 */
	public boolean update(Admin admin) {
		return adminDao.update(admin);
	}

	/**
	 * 删除
	 * @param user
	 */
	public boolean delete(int id) {
		return adminDao.delete(id);
	}

	
}
