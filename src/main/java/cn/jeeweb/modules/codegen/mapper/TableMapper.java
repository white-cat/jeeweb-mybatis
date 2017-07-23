package cn.jeeweb.modules.codegen.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.modules.codegen.entity.Table;

public interface TableMapper extends BaseMapper<Table> {
	
	/**
	 * 
	 * @title: findSubTables   
	 * @description:通过表名获得子表信息
	 * @param userId
	 * @return      
	 * @return: List<Role>
	 */
	List<Table> findSubTables(String tablename);
}