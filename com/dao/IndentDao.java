package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.entity.Indent;

/**
 * 订单相关数据库处理
 */
public interface IndentDao {	
	
	/**
	 * 增加
	 * @param indent
	 * @return
	 */
	@Insert("insert into indent (total, amount, status, name, phone, address, systime, user_id) "
			+ "values (#{total}, #{amount}, #{status}, #{name}, #{phone}, #{address}, now(), #{userId})")
	@SelectKey(before=false, keyProperty="id", resultType=Integer.class, statement="SELECT LAST_INSERT_ID()")
	public boolean insert(Indent indent);
    
	/**
	 * 删除
	 * @param id
	 * @return
	 */
    @Delete("delete from indent where id=#{id}")
	public boolean delete(int id);
	
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update indent set total=#{total}, amount=#{amount}, status=#{status}, name=#{name}, "
    		+ "phone=#{phone}, address=#{address}, user_id=#{userId} where id=#{id}")
	public boolean update(Indent user);
    
    /**
     * 修改
     * @param user
     * @return
     */
    @Update("update indent set status=#{status} where id=#{id}")
    public boolean updateStatus(@Param("id")int id, @Param("status")byte status);
	
    /**
     * 查询
     * @param id
     * @return
     */
    @Select("select * from indent where id=#{id}")
    public Indent select(int id);
    

    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from indent")
	public long selectTotal();
    
    /**
     * 查询总数
     * @return
     */
    @Select("select count(*) from indent where status=#{status}")
    public long selectTotalByStatus(@Param("status")byte status);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from indent order by id desc limit #{begin}, #{size}")
	public List<Indent> selectList(@Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from indent where status=#{status} order by id desc limit #{begin}, #{size}")
    public List<Indent> selectListByStatus(@Param("status")byte status, @Param("begin")int begin, @Param("size")int size);
    
    /**
     * 查询列表
     * @return
     */
    @Select("select * from indent where user_id=#{userIid} order by id desc")
    public List<Indent> selectListByUserid(@Param("userIid")int userIid);
    

}
