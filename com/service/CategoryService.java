package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CategoryDao;
import com.entity.Category;

/**
 * 类目相关服务
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	
	/**
	 * 获取列表
	 * @return
	 */
	public List<Category> getList(){
		return categoryDao.selectListAll();
	}

	/**
	 * 列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Category> getList(int page, int size) {
		return categoryDao.selectList((page-1)*size, size);
	}

	/**
	 * 总数
	 * @return
	 */
	public long getTotal() {
		return categoryDao.selectTotal();
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public Category get(int id) {
		return categoryDao.select(id);
	}
	
	/**
	 * 添加
	 * @param category
	 * @return
	 */
	public boolean add(Category category) {
		return categoryDao.insert(category);
	}

	/**
	 * 更新
	 * @param category
	 */
	public boolean update(Category category) {
		return categoryDao.update(category);
	}

	/**
	 * 删除
	 * @param id
	 */
	public boolean delete(int id) {
		return categoryDao.delete(id);
	}
	
}
