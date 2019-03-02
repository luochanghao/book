package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Item;

/**
 * 订单项相关数据库处理
 */
public interface ItemDao {	
	
	/**
	 * 增加
	 * @param item
	 * @return
	 */
	@Insert("insert into items (price, amount, book_id, indent_id) values (#{price}, #{amount}, #{bookId}, #{indentId})")
	@SelectKey(before=false, keyProperty="id", resultType=Integer.class, statement="SELECT LAST_INSERT_ID()")
	public boolean insert(Item item);
    
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @Delete("delete from items where id=#{id}")
	public boolean delete(int id);
	
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update items set price=#{price}, amount=#{amount}, book_id=#{bookId}, indent_id=#{indentId} where id=#{id}")
	public boolean update(Item item);
	
    /**
     * 查询
     * @param id
     * @return
     */
    @Select("select * from items where id=#{id}")
    public Item select(int id);
    

    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from items")
	public long selectTotal();
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from items order by id desc limit #{begin}, #{size}")
	public List<Item> selectList(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from items where indent_id=#{indentId}")
    public List<Item> selectListByIndentId(@Param("indentId")int indentId);
    

}
