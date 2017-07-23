CREATE TABLE `${table.tableName}` (  
   `id` varchar(32) NOT NULL COMMENT '主键',
   <#if table.columns?exists>
   <#list table.columns as attr>
   <#if attr.columnName != "id">
   `${attr.columnName}` <#if attr.isAlone> ${attr.typeName}<#else> ${attr.typeName}(${attr.columnSize}<#if attr.isFloat>,${attr.decimalDigits}</#if>)</#if><#if !attr.nullable> NOT NULL</#if><#if !attr.isBlob&&attr.columnDef?exists&&attr.columnDef!=''> DEFAULT <#if attr.isString>'${attr.columnDef}'<#else>${attr.columnDef}</#if></#if> <#if attr.remarks?exists&&attr.remarks!=''>COMMENT  '${attr.remarks}'</#if>,
   </#if>
   </#list>
   </#if>
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.remarks}';