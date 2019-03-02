package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Category;

/**
 * 类目相关数据库处理
 */
public interface CategoryDao {	
	
	/**
	 * 增加
	 * @param category
	 * @return
	 */
	@Insert("insert into category (name) values (#{name})")
	@SelectKey(before=false, keyProperty="id", resultType=Integer.class, statement="SELECT LAST_INSERT_ID()")
	public boolean insert(Category category);
    
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @Delete("delete from category where id=#{id}")
	public boolean delete(int id);
	
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update category set name=#{name} where id=#{id}")
	public boolean update(Category user);
	
    /**
     * 查询
     * @param id
     * @return
     */
    @Select("select * from category where id=#{id}")
    public Category select(int id);
    

    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from category")
	public long selectTotal();
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from category order by id desc limit #{begin}, #{size}")
	public List<Category> selectList(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from category order by id desc")
    public List<Category> selectListAll();
    
}
