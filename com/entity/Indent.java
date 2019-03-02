package com.entity;

/**
 * 订单实体
 */
import java.util.Date;
import java.util.List;

import com.util.DateUtil;

public class Indent {
	
	// 状态 - 未处理
	public static final byte STATUS_WAIT = 1;
	// 状态 - 已处理
	public static final byte STATUS_DOWN = 2;
	
	
	private int id;
	private float total;
	private int amount;
	private int status;
	private String name;
	private String phone;
	private String address;
	private Date systime;
	private String systimes;
	private int userId;
	private User user;
	private List<Item> itemList;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = (float)Math.round(total*100)/100;
	}
	public Date getSystime() {
		return systime;
	}
	public void setSystime(Date systime) {
		this.systime = systime;
		this.systimes = DateUtil.formatDateTime(systime);
	}
	public String getSystimes() {
		return systimes;
	}
	public void setSystimes(String systimes) {
		this.systimes = systimes;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
