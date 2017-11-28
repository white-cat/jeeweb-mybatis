package cn.jeeweb.modules.codegen.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import cn.jeeweb.core.common.entity.DataEntity;
import cn.jeeweb.core.utils.StringUtils;
import cn.jeeweb.modules.codegen.codegenerator.data.DbColumnInfo;
import cn.jeeweb.modules.codegen.codegenerator.utils.DefinitionUtils;
import cn.jeeweb.modules.codegen.codegenerator.utils.type.DbTypeConvert;
import cn.jeeweb.modules.codegen.codegenerator.xml.definition.Type;

import java.lang.String;

@TableName("codegen_column")
@SuppressWarnings("serial")
public class Column extends DataEntity<String> implements java.io.Serializable {

	@TableId(value = "id", type = IdType.UUID)
	private String id;
	// 表的ID
	@TableField(value = "table_id", el = "table.id")
	private Table table;
	// 获得字段名称
	@TableField("column_name")
	private String columnName = "";
	// 获得字段类型名称
	@TableField("type_name")
	private String typeName = "";
	// 获得字段大小
	@TableField("column_size")
	private String columnSize = "255";
	// 是否为主键
	@TableField("parmary_key")
	private Boolean parmaryKey = false;
	// 是否为外键
	@TableField("imported_key")
	private Boolean importedKey = false;
	// 是否允许为空
	@TableField("is_nullable")
	private Boolean nullable = false;
	// 默认值
	@TableField("column_def")
	private String columnDef = "";
	// 小数部分的位数
	@TableField("decimal_digits")
	private String decimalDigits = "0";
	/** 页面属性 */
	// JAVA类型
	@TableField("java_type")
	private String javaType;
	// JAVA字段名
	@TableField("java_field")
	private String javaField;
	// 是否列表显示
	@TableField("is_list")
	private Boolean isList;
	// 是否查询字段
	@TableField("is_query")
	private Boolean isQuery;
	// 查询方式
	@TableField("query_type")
	private String queryType;
	// 查询模式
	@TableField("query_model")
	private String queryModel;

	// 是否表单显示
	@TableField("is_form")
	private Boolean isForm;
	// 显示表单类型
	@TableField("form_type")
	private String formType;
	// 字段生成方案
	@TableField("input_type")
	private String inputType;
	// 字典分组
	@TableField("dict_group")
	private String dictGroup;
	@TableField("sort")
	private Integer sort;

	/* 验证 */
	// 校验类型
	@TableField("valid_type")
	private String validType;
	// 验证规则
	@TableField("regex_valid")
	private String regexValid;
	// 最大长度
	@TableField("max_size")
	private Integer maxSize;
	// 最小长度
	@TableField("min_size")
	private Integer minSize;
	// 最大值
	@TableField("max_value")
	private String maxValue;
	// 最小值
	@TableField("min_value")
	private String minValue;
	// 为空提示
	@TableField("nullmsg")
	private String nullmsg;
	// 关联表
	@TableField("foreign_table")
	private String foreignTable;

	@TableField(exist = false)
	private Boolean isFloat = Boolean.FALSE;
	@TableField(exist = false)
	private Boolean isString = Boolean.FALSE;
	@TableField(exist = false)
	private Boolean isAlone = Boolean.FALSE;
	@TableField(exist = false)
	private Boolean isBlob = Boolean.FALSE;
	@TableField(exist = false)
	private Boolean isBaseType = Boolean.FALSE;
	@TableField(exist = false)
	private String[] baseTypes = { "String", "Double", "Text", "Date", "Blob", "Short", "Integer", "Boolean" };
	@TableField(exist = false)
	private String simpleJavaType;

	public Column() {

	}

	public Column(DbColumnInfo dbColumnInfo) {
		this.columnName = dbColumnInfo.getColumnName().toLowerCase();
		this.remarks = dbColumnInfo.getRemarks();
		this.typeName = dbColumnInfo.getTypeName().toLowerCase();
		if (StringUtils.isEmpty(dbColumnInfo.getColumnSize())) {
			this.columnSize = "1";
		} else {
			this.columnSize = dbColumnInfo.getColumnSize();
		}
		this.nullable = dbColumnInfo.isNullable();
		this.parmaryKey = dbColumnInfo.isParmaryKey();
		this.importedKey = dbColumnInfo.isImportedKey();
		this.columnDef = dbColumnInfo.getColumnDef();
		this.decimalDigits = StringUtils.isEmpty(dbColumnInfo.getDecimalDigits()) ? "0"
				: dbColumnInfo.getDecimalDigits();

		Type type = DbTypeConvert.getTypeConvert(DbTypeConvert.TYPE_DB_TO_JAVA).getType(dbColumnInfo.getTypeName());
		if (type != null) {
			this.javaType = type.getJavaType();
		} else {
			this.javaType = "String";
		}
		this.javaField = StringUtils.underlineToCamel(this.columnName);
		this.isList = Boolean.TRUE;
		this.isQuery = Boolean.FALSE;
		this.queryType = "eq";
		this.isForm = Boolean.TRUE;
		this.formType = "input";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName.toUpperCase();
	}

	public String getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public Boolean getParmaryKey() {
		return parmaryKey;
	}

	public void setParmaryKey(Boolean parmaryKey) {
		this.parmaryKey = parmaryKey;
	}

	public Boolean getImportedKey() {
		return importedKey;
	}

	public void setImportedKey(Boolean importedKey) {
		this.importedKey = importedKey;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public String getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaField() {
		return javaField;
	}

	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	public Boolean getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(Boolean isQuery) {
		this.isQuery = isQuery;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Boolean getIsForm() {
		return isForm;
	}

	public void setIsForm(Boolean isForm) {
		this.isForm = isForm;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getDictGroup() {
		return dictGroup;
	}

	public void setDictGroup(String dictGroup) {
		this.dictGroup = dictGroup;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(String queryModel) {
		this.queryModel = queryModel;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getMinSize() {
		return minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getNullmsg() {
		return nullmsg;
	}

	public void setNullmsg(String nullmsg) {
		this.nullmsg = nullmsg;
	}

	public String getRegexValid() {
		return regexValid;
	}

	public void setRegexValid(String regexValid) {
		this.regexValid = regexValid;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getHibernateType() {
		/*
		 * Type type =
		 * DbTypeConvert.getTypeConvert(DbTypeConvert.TYPE_JAVA_TO_HIBERNATE).
		 * getType(this.javaType); if (type != null) { hibernateType =
		 * type.getDbType(); } else { hibernateType = "java.lang.String"; }
		 */
		return "";
	}

	public String getForeignTable() {
		return foreignTable;
	}

	public void setForeignTable(String foreignTable) {
		this.foreignTable = foreignTable;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Boolean getIsFloat() {
		String isFloats = DefinitionUtils.getDefinitionUtils().getDefinition().getDb().getFloatTypes().trim();
		String[] floatTypes = isFloats.split(",");
		return this.inTarget(typeName, floatTypes);
	}

	public Boolean getIsString() {
		String isFloats = DefinitionUtils.getDefinitionUtils().getDefinition().getDb().getCharTypes().trim();
		String[] floatTypes = isFloats.split(",");
		return this.inTarget(typeName, floatTypes);
	}

	public Boolean getIsAlone() {
		String isAlones = DefinitionUtils.getDefinitionUtils().getDefinition().getDb().getAloneTypes().trim();
		String[] isAloneTypes = isAlones.split(",");
		return this.inTarget(typeName, isAloneTypes);
	}

	public Boolean getIsBlob() {
		String isBlobType = DefinitionUtils.getDefinitionUtils().getDefinition().getDb().getBlobTypes().trim();
		String[] isBlobTypes = isBlobType.split(",");
		return this.inTarget(typeName, isBlobTypes);
	}

	public Boolean getIsBaseType() {
		return this.inTarget(this.javaType, baseTypes);
	}

	public String getSimpleJavaType() {
		String javaType = this.javaType;
		if (javaType.contains("|")) {
			String[] innerJavaTypes = javaType.split("\\|");
			return innerJavaTypes[1];
		}
		return this.javaType;
	}

	private Boolean inTarget(String source, String[] targets) {
		Boolean inTarget = Boolean.FALSE;
		for (String type : targets) {
			if (type.trim().equals(source.trim())) {
				inTarget = Boolean.TRUE;
				break;
			}
		}
		return inTarget;
	}

}
