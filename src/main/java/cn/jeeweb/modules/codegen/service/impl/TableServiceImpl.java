package cn.jeeweb.modules.codegen.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import cn.jeeweb.core.common.service.impl.CommonServiceImpl;
import cn.jeeweb.core.utils.ServletUtils;
import cn.jeeweb.core.utils.StringUtils;
import cn.jeeweb.modules.codegen.codegenerator.GeneratorManagor;
import cn.jeeweb.modules.codegen.codegenerator.data.AttributeInfo;
import cn.jeeweb.modules.codegen.codegenerator.data.DbColumnInfo;
import cn.jeeweb.modules.codegen.codegenerator.data.DbTableInfo;
import cn.jeeweb.modules.codegen.codegenerator.data.GeneratorInfo;
import cn.jeeweb.modules.codegen.codegenerator.exception.GenerationException;
import cn.jeeweb.modules.codegen.codegenerator.utils.CodeGenUtils;
import cn.jeeweb.modules.codegen.dao.IGeneratorDao;
import cn.jeeweb.modules.codegen.entity.Column;
import cn.jeeweb.modules.codegen.entity.Table;
import cn.jeeweb.modules.codegen.mapper.TableMapper;
import cn.jeeweb.modules.codegen.service.IColumnService;
import cn.jeeweb.modules.codegen.service.ITableService;
import cn.jeeweb.modules.sys.entity.Menu;
import cn.jeeweb.modules.sys.service.IMenuService;
import freemarker.template.TemplateException;

/**
 * 
 * All rights Reserved, Designed By www.jeeweb.cn
 * 
 * @title: TableService.java
 * @package cn.jeeweb.modules.codegen.service.impl
 * @description:
 * @author: 王存见
 * @date: 2017年5月8日 上午11:17:41
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */
@Transactional
@Service("tableService")
public class TableServiceImpl extends CommonServiceImpl<TableMapper, Table> implements ITableService {
	@Autowired
	private IGeneratorDao generatorDao;

	@Autowired
	private IColumnService columnService;

	private GeneratorManagor generatorManagor;

	private IMenuService menuService;

	@PostConstruct
	public void initTable() {
		generatorManagor = new GeneratorManagor();
	}

	@Override
	public List<DbTableInfo> getTableNameList() {
		return generatorDao.getDbTables();
	}

	@Override
	public boolean insert(Table table) {
		table.setSyncDatabase(Boolean.FALSE);
		// 保存主表
		super.insert(table);
		// 字段
		String columnListStr = StringEscapeUtils.unescapeHtml4(ServletUtils.getRequest().getParameter("columnList"));
		List<Column> columnList = JSONObject.parseArray(columnListStr, Column.class);
		for (int i = 0; i < columnList.size(); i++) {
			// 保存字段列表
			Column column = columnList.get(i);
			column.setId("");
			column.setSort(i);
			column.setTable(table);
			columnService.insert(column);
		}
		return true;
	}

	@Override
	public boolean insertOrUpdate(Table table) {
		// 删除已经删除的数据
		List<Column> oldColumnList = columnService.selectListByTableId(table.getId());
		// 字段
		String columnListStr = StringEscapeUtils.unescapeHtml4(ServletUtils.getRequest().getParameter("columnList"));
		List<Column> columnList = JSONObject.parseArray(columnListStr, Column.class);

		// 更新主表
		super.insertOrUpdate(table);
		columnList = JSONObject.parseArray(columnListStr, Column.class);
		List<String> newsIdList = new ArrayList<String>();
		int sort = 1;
		// 保存或更新数据
		for (Column column : columnList) {
			column.setSort(sort);
			// 保存字段列表
			if (StringUtils.isEmpty(column.getId()) || column.getId().contains("templateid")) {
				// 保存字段列表
				column.setId("");
				column.setTable(table);
				columnService.insert(column);
			} else {
				// 设置不变更的字段
				columnService.insertOrUpdate(column);
			}
			sort++;
			newsIdList.add(column.getId());
		}

		// 删除老数据
		for (Column column : oldColumnList) {
			String columnId = column.getId();
			if (!newsIdList.contains(columnId)) {
				columnService.deleteById(columnId);
			}
		}
		return true;
	}

	@Override
	public boolean deleteBatchIds(List<? extends Serializable> idList) {
		for (Serializable id : idList) {
			deleteById(id);
		}
		return true;
	}

	@Override
	public boolean deleteById(Serializable id) {
		// 删除已经删除的数据
		Table table = selectById(id);
		// 先刪除表
		try {
			generatorDao.dropTable(table.getTableName());
		} catch (Exception e) {
			e.printStackTrace();
			// 部分数据库在没有表而执行删表语句时会报错
			// logger.error(e.getMessage());
		}
		removeById(id);
		return true;
	}

	@Override
	public void removeById(Serializable id) {
		// 删除已经删除的数据
		List<Column> columnList = columnService.selectListByTableId((String) id);
		// 保存或更新数据
		for (Column column : columnList) {
			columnService.deleteById(column.getId());
		}
		super.deleteById(id);
	}

	@Override
	public void generateCode(Table table, GeneratorInfo generatorInfo) throws IOException, GenerationException {
		generatorInfo.setTableName(table.getTableName());
		table.setClassName(generatorInfo.getEntityName());
		List<Column> columnList = columnService.selectListByTableId(table.getId());
		List<AttributeInfo> attributeInfos = new ArrayList<AttributeInfo>();
		for (Column column : columnList) {
			attributeInfos.add(new AttributeInfo(column));
		}
		generatorInfo.setType(table.getTableType());
		generatorInfo.setColumns(columnList);
		generatorInfo.setAttributeInfos(attributeInfos);
		// 查询附表
		List<Table> schedules = null;
		if (table.getTableType().equals("2")) {
			// 获得附表
			schedules = findSubTable(table.getTableName());
			for (Table tableEntity : schedules) {
				List<Column> subColumnList = columnService.selectListByTableId(tableEntity.getId());
				tableEntity.setColumns(subColumnList);
				for (Column column : subColumnList) {
					if (!StringUtils.isEmpty(column.getForeignTable())
							&& column.getForeignTable().equals(table.getTableName())) {
						tableEntity.setParentField(column.getJavaField());
					}
				}
			}
			generatorInfo.setSchedules(schedules);
			// 生成附表实体
		}
		generatorManagor.process(generatorInfo);
		// 查询附表
		if (table.getTableType().equals("2")) {
			for (Table tableEntity : schedules) {
				generatorInfo.setTableName(tableEntity.getTableName());
				generatorInfo.setFunctionDesc(tableEntity.getRemarks());
				generatorInfo.setEntityName(tableEntity.getClassName());
				generatorInfo.setFunctionName(tableEntity.getRemarks());
				List<Column> subColumnList = tableEntity.getColumns();
				List<AttributeInfo> subAttributeInfos = new ArrayList<AttributeInfo>();
				for (Column column : subColumnList) {
					if (!StringUtils.isEmpty(column.getForeignTable())
							&& column.getForeignTable().equals(table.getTableName())) {
						column.setJavaType(table.getClassName());
						tableEntity.setParentField(column.getJavaField());
						column.setImportedKey(Boolean.TRUE);
					}
					AttributeInfo attributeInfo = new AttributeInfo(column);
					subAttributeInfos.add(attributeInfo);
				}
				generatorInfo.setType(tableEntity.getTableType());
				generatorInfo.setColumns(subColumnList);
				generatorInfo.setAttributeInfos(subAttributeInfos);
				List<String> generatorKeys = new ArrayList<String>();
				generatorKeys.add("Entity");
				generatorKeys.add("IService");
				generatorKeys.add("ServiceImpl");
				generatorKeys.add("Mapper");
				generatorKeys.add("xmlMapper");
				generatorInfo.setGeneratorKeys(generatorKeys);
				generatorManagor.process(generatorInfo);
			}
		}
	}

	@Override
	public void importDatabase(Table table) {
		String tableName = table.getTableName();
		table.setTitle(tableName);
		table.setSyncDatabase(Boolean.TRUE);
		// 保存主表
		super.insert(table);
		List<DbColumnInfo> dbColumnInfos = generatorDao.getDbColumnInfo(tableName);
		for (int j = 0; j < dbColumnInfos.size(); j++) {
			Column column = new Column(dbColumnInfos.get(j));
			column.setSort(j + 1);
			// 保存字段列表
			column.setTable(table);
			columnService.insert(column);
		}

	}

	public void dropTable(String tableid) {
		Table table = selectById(tableid);
		try {
			generatorDao.dropTable(table.getTableName());
		} catch (Exception e) {
			// 部分数据库在没有表而执行删表语句时会报错
			// logger.error(e.getMessage());
		}
	}

	@Override
	public void syncDatabase(String tableid) throws TemplateException, IOException {
		Table table = selectById(tableid);
		List<Column> columns = columnService.selectListByTableId(table.getId());
		table.setColumns(columns);
		String dbType = CodeGenUtils.getDbType();
		Map<String, Object> tableInfo = new HashMap<String, Object>();
		tableInfo.put("table", table);
		tableInfo.put("dbType", dbType);
		generatorDao.createTable(tableInfo);
		table.setSyncDatabase(Boolean.TRUE);
		super.insertOrUpdate(table);
	}

	@Override
	public void createMenu(Table table, Menu menu) {
		String url = "";
		String permission = "";
		menu.setIsshow((short) 1);
		menu.setUrl(url);
		menu.setPermission(permission);
		menu.setRemarks(table.getRemarks());
		menuService.insert(menu);
	}

	@Override
	public List<Table> findSubTable(String tablename) {
		return baseMapper.findSubTables(tablename);
	}

}
