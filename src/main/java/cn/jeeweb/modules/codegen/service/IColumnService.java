package cn.jeeweb.modules.codegen.service;

import java.util.List;
import cn.jeeweb.core.common.service.ICommonService;
import cn.jeeweb.modules.codegen.entity.Column;

public interface IColumnService extends ICommonService<Column> {
	List<Column> selectListByTableId(String tableId);
}
