package cn.jeeweb.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import cn.jeeweb.modules.sys.entity.UserOnline;

public interface UserOnlineMapper extends BaseMapper<UserOnline> {
	
   List<UserOnline> selectUserOnlinePage(Page<UserOnline> page, @Param("ew") Wrapper<UserOnline> wrapper);
}