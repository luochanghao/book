package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Book;

/**
 * 图书相关数据库处理
 */
public interface BookDao {	
	
	/**
	 * 增加
	 * @param book
	 * @return
	 */
	@Insert("insert into book (name, cover, price, intro, auther, press, pubdate, special, news, sale, category_id) "
			+ "values (#{name}, #{cover}, #{price}, #{intro}, #{auther}, #{press}, #{pubdate}, #{special}, #{news}, #{sale}, #{categoryId})")
	@SelectKey(before=false, keyProperty="id", resultType=Integer.class, statement="SELECT LAST_INSERT_ID()")
	public boolean insert(Book book);
    
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @Delete("delete from book where id=#{id}")
	public boolean delete(int id);
	
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update book set name=#{name}, cover=#{cover}, price=#{price}, intro=#{intro}, auther=#{auther}, auther=#{auther}, "
    		+ "press=#{press}, pubdate=#{pubdate}, special=#{special}, news=#{news}, sale=#{sale}, category_id=#{categoryId} where id=#{id}")
	public boolean update(Book user);
	
    /**
     * 查询
     * @param id
     * @return
     */
    @Select("select * from book where id=#{id}")
    public Book select(int id);
    

    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book")
	public long selectTotal();
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book where name like concat('%',#{name},'%')")
    public long selectTotalLikeName(@Param("name")String name);
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book where category_id=#{categoryId}")
    public long selectTotalByCategoryId(@Param("categoryId")int categoryId);
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book where special=1")
    public long selectTotalIsSpecial();
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book where news=1")
    public long selectTotalIsNews();
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from book where sale=1")
    public long selectTotalIsSale();
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book order by id desc limit #{begin}, #{size}")
	public List<Book> selectList(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book where name like concat('%',#{name},'%') order by id desc limit #{begin}, #{size}")
    public List<Book> selectListLikeName(@Param("name")String name, @Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book where category_id=#{categoryId} order by id desc limit #{begin}, #{size}")
    public List<Book> selectListByCategoryId(@Param("categoryId")int categoryId, @Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book where special=1 order by id desc limit #{begin}, #{size}")
    public List<Book> selectListIsSpecial(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book where news=1 order by id desc limit #{begin}, #{size}")
    public List<Book> selectListIsNews(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from book where sale=1 order by id desc limit #{begin}, #{size}")
    public List<Book> selectListIsSale(@Param("begin")int begin, @Param("size")int size);
    

}
