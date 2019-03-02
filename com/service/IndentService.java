package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.IndentDao;
import com.dao.ItemDao;
import com.entity.Book;
import com.entity.Indent;
import com.entity.Item;

/**
 * 订单相关服务
 */
@Service
public class IndentService {

	@Autowired
	private IndentDao indentDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private BookService bookService;
	

	/**
	 * 保存订单
	 * @param indent
	 */
	public void add(Indent indent) {
		indent.setStatus(Indent.STATUS_WAIT);
		indent.setSystime(new Date());
		indentDao.insert(indent);
		int indentId = indent.getId();
		for(Item item : indent.getItemList()){
			item.setIndentId(indentId);
			itemDao.insert(item);
		}
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public long getTotal(byte status) {
		return indentDao.selectTotalByStatus(status);
	}
	
	/**
	 * 获取订单列表
	 * @param page
	 * @param row
	 * @return
	 */
	public List<Indent> getList(byte status, int page, int size) {
		return pack(indentDao.selectListByStatus(status, (page-1)*size, size));
	}

	/**
	 * 处理订单
	 * @param id
	 * @return 
	 */
	public boolean dispose(int id) {
		Indent indent = indentDao.select(id);
		indent.setStatus(2);
		return indentDao.update(indent);
	}

	/**
	 * 删除订单
	 * @param id
	 */
	public boolean delete(int id) {
		return indentDao.updateStatus(id, Indent.STATUS_DOWN);
	}

	/**
	 * 订单项列表
	 * @param indentid
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Item> getItemList(int indentid) {
		List<Item> itemList = itemDao.selectListByIndentId(indentid);
		if (itemList!=null && !itemList.isEmpty()) {
			for (Item item : itemList) {
				item.setTotal(item.getPrice() * item.getAmount());
			}
		}
		return itemList;
	}

	/**
	 * 获取某人全部订单
	 * @param userid
	 * @return
	 */
	public List<Indent> getListByUserid(int userid) {
		return pack(indentDao.selectListByUserid(userid));
	}
	
	
	/**
	 * 创建订单
	 * @param bookid
	 * @return
	 */
	public Indent create(Book book) {
		List<Item> itemList = new ArrayList<Item>();
		itemList.add(createItems(book));
		Indent indent = new Indent();
		indent.setItemList(itemList);
		indent.setTotal(book.getPrice());
		indent.setAmount(1);
		return indent;
	}
	
	/**
	 * 创建订单项
	 * @param book
	 * @return
	 */
	private Item createItems(Book book) {
		Item item = new Item();
		item.setBook(book);
		item.setBookId(book.getId());
		item.setPrice(book.getPrice());
		item.setAmount(1);
		item.setTotal(item.getPrice() * item.getAmount());
		return item;
	}

	/**
	 * 向订单添加项目
	 * @param indentList
	 * @param bookid
	 * @return
	 */
	public Indent addItem(Indent indent, Book book) {
		List<Item> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Item>() : itemList;
		// 如果购物车已有此书, 数量+1
		boolean noThisBook = true;
		for (Item item : itemList) {
			if (item.getBook().getId() == book.getId()) {
				item.setPrice(book.getPrice());
				item.setAmount(item.getAmount() + 1);
				item.setTotal(item.getPrice() * item.getAmount());
				noThisBook = false;
			}
		}
		// 如果当前购物车没有此书, 创建新条目
		if (noThisBook) {
			itemList.add(createItems(book));
		}
		indent.setTotal(indent.getTotal() + book.getPrice());
		indent.setAmount(indent.getAmount() + 1);
		return indent;
	}
	
	/**
	 * 从订单中减少项目
	 * @param indent
	 * @param product
	 * @return
	 */
	public Indent lessenItem(Indent indent, Book book) {
		List<Item> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Item>() : itemList;
		// 如果购物车已有此项目, 数量-1
		boolean noneThis = true;
		for (Item item : itemList) {
			if (item.getBook().getId() == book.getId()) {
				if (item.getAmount() - 1 <= 0) { // 减少到0后删除
					return deleteItem(indent, book);
				}
				item.setPrice(book.getPrice());
				item.setAmount(item.getAmount() - 1);
				item.setTotal(item.getPrice() * item.getAmount());
				noneThis = false;
			}
		}
		// 如果当前购物车没有项目, 直接返回
		if (noneThis) {
			return indent;
		}
		indent.setTotal(indent.getTotal() - book.getPrice());
		indent.setAmount(indent.getAmount() - 1);
		return indent;
	}
	
	/**
	 * 从订单中删除项目
	 * @param indent
	 * @param product
	 * @return
	 */
	public Indent deleteItem(Indent indent, Book book) {
		List<Item> itemList = indent.getItemList();
		itemList = itemList==null ? new ArrayList<Item>() : itemList;
		// 如果购物车已有此项目, 数量清零
		boolean noneThis = true;
		int itemAmount = 0;
		List<Item> resultList = new ArrayList<Item>();
		for (Item item : itemList) {
			if (item.getBook().getId() == book.getId()) {
				itemAmount = item.getAmount();
				noneThis = false;
				continue;
			}
			resultList.add(item);
		}
		// 如果已经没有项目, 返回null
		if (resultList.isEmpty()) {
			return null;
		}
		indent.setItemList(resultList);
		// 如果当前购物车没有项目, 直接返回
		if (noneThis) {
			return indent;
		}
		indent.setTotal(indent.getTotal() - book.getPrice() * itemAmount);
		indent.setAmount(indent.getAmount() - itemAmount);
		return indent;
	}
	
	
	/**
	 * 封装订单信息
	 * @param indentList
	 * @return
	 */
	private List<Indent> pack(List<Indent> indentList) {
		if(Objects.nonNull(indentList)) {
			for(Indent indent : indentList) {
				indent = pack(indent);
			}
		}
		return indentList;
	}
	
	/**
	 * 封装订单信息
	 * @param indentList
	 * @return
	 */
	private Indent pack(Indent indent) {
		if(Objects.nonNull(indent)) {
			List<Item> itemList = itemDao.selectListByIndentId(indent.getId());
			for(Item item : itemList) {
				item.setBook(bookService.get(item.getBookId()));
				item.setTotal(item.getPrice() * item.getAmount());
			}
			indent.setItemList(itemList);
		}
		return indent;
	}
	
}
