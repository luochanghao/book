package com.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BookDao;
import com.entity.Book;
import com.util.UploadUtil;

/**
 * 图书相关服务
 */
@Service
public class BookService {

	@Autowired
	private BookDao bookDao;
	
	
	/**
	 * 通过名称搜索
	 * @return 无记录返回空集合
	 */
	public long getTotal(){
		return bookDao.selectTotal();
	}
	
	/**
	 * 通过名称搜索
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getList(int page, int size) {
		return bookDao.selectList((page-1)*size, size);
	}
	
	/**
	 * 通过名称搜索
	 * @return 无记录返回空集合
	 */
	public long getTotal(String name){
		return bookDao.selectTotalLikeName(name);
	}
	
	/**
	 * 通过名称搜索
	 * @param category
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getList(String name, int page, int size) {
		return bookDao.selectListLikeName(name, (page-1)*size, size);
	}
	
	/**
	 * 通过分类搜索
	 * @return 无记录返回空集合
	 */
	public long getTotalByCategoryId(int categoryid){
		return bookDao.selectTotalByCategoryId(categoryid);
	}
	
	/**
	 * 通过分类搜索
	 * @param categoryid
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Book> getListByCategoryId(int categoryId, int page, int size) {
		return bookDao.selectListByCategoryId(categoryId, (page-1)*size, size);
	}
	
	/**
	 * 获取特卖总数
	 * @return 无记录返回空集合
	 */
	public long getTotalIsSpecial(){
		return bookDao.selectTotalIsSpecial();
	}
	
	/**
	 * 获取特卖列表
	 * @return 无记录返回空集合
	 */
	public List<Book> getListIsSpecial(int page, int size){
		return bookDao.selectListIsSpecial((page-1)*size, size);
	}
	
	/**
	 * 获取新品总数
	 * @return 无记录返回空集合
	 */
	public long getTotalIsNews(){
		return bookDao.selectTotalIsNews();
	}
	
	/**
	 * 获取新品列表
	 * @return 无记录返回空集合
	 */
	public List<Book> getListIsNews(int page, int size){
		return bookDao.selectListIsNews((page-1)*size, size);
	}
	
	/**
	 * 获取热卖总数
	 * @return 无记录返回空集合
	 */
	public long getTotalIsSale(){
		return bookDao.selectTotalIsSale();
	}
	
	/**
	 * 获取热卖列表
	 * @return 无记录返回空集合
	 */
	public List<Book> getListIsSale(int page, int size){
		return bookDao.selectListIsSale((page-1)*size, size);
	}

	
	/**
	 * 通过id获取
	 * @param bookid
	 * @return
	 */
	public Book get(int id) {
		Book book = bookDao.select(id);
		// 介绍只显示前70个字符
		if(Objects.nonNull(book) && Objects.nonNull(book.getIntro()) && book.getIntro().length() > 70) {
			book.setIntro(book.getIntro().substring(0, 70) + "...");
		}
		return book;
	}

	/**
	 * 添加
	 * @param book
	 */
	public boolean add(Book book) {
		book.setCover(UploadUtil.upload(book.getFile()));
		return bookDao.insert(book);
	}

	/**
	 * 修改
	 * @param book
	 * @return 
	 */
	public boolean update(Book book) {
		if (Objects.nonNull(book.getFile()) && !book.getFile().isEmpty()) {
			book.setCover(UploadUtil.upload(book.getFile()));
		}
		return bookDao.update(book);
	}

	/**
	 * 删除
	 * @param book
	 */
	public boolean delete(int id) {
		return bookDao.delete(id);
	}
	
}
