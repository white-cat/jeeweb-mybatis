package cn.jeeweb.modules.sys.mapper;

import cn.jeeweb.modules.sys.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 
	 * @title: selectUserList
	 * @description: 查找用户列表
	 * @param wrapper
	 * @return
	 * @return: List<User>
	 */
	List<User> selectUserList(Pagination page, @Param("ew") Wrapper<User> wrapper);
}