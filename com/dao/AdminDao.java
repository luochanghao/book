package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Admin;

/**
 * 管理员相关数据库处理
 */
public interface AdminDao {
	
	/**
	 * 增加
	 * @param admin
	 * @return
	 */
	@Insert("insert into admin (username, password) values (#{username}, #{password})")
	@SelectKey(before=false, keyProperty="id", resultType=Integer.class, statement="SELECT LAST_INSERT_ID()")
	public boolean insert(Admin admin);
    
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @Delete("delete from admin where id=#{id}")
	public boolean delete(int id);
	
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update admin set username=#{username}, password=#{password} where id=#{id}")
	public boolean update(Admin user);
	
    /**
     * 查询
     * @param id
     * @return
     */
    @Select("select * from admin where id=#{id}")
    public Admin select(int id);
    

    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from admin")
	public long selectTotal();
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from admin order by id desc limit #{begin}, #{size}")
	public List<Admin> selectList(@Param("begin")int begin, @Param("size")int size);
    
    
    /**
     * 通过名称搜索
     * 由于此处如果查询出多条记录会抛异常, 所以加上limit防止数据引起的错误
     * @param username
     * @return
     */
    @Select("select * from admin where username=#{username} limit 1")
    public Admin selectByUsername(@Param("username")String username);
    
    /**
     * 通过名称搜索
     * 由于此处如果查询出多条记录会抛异常, 所以加上limit防止数据引起的错误
     * @param username
     * @return
     */
    @Select("select * from admin where username=#{username} and password=#{password} limit 1")
    public Admin selectByUsernameAndPassword(@Param("username")String username, @Param("password")String password);
    
}