CREATE TABLE `${table.tableName}` (  
   `id` varchar(32) NOT NULL COMMENT '主键',
   <#if table.columns?exists>
   <#list table.columns as attr>
   <#if attr.columnName != "id">
   `${attr.columnName}` ${attr.typeName}(255) 
   <#if !attr.nullable>NOT NULL</#if> 
   <#if attr.columnDef!=null>DEFAULT ${attr.columnDef}</#if>
   COMMENT  <#if !attr.isString>'${attr.remarks}'<#else>${attr.remarks}</#if>,
   </#if>
   </#list>
   </#if>
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.remarks}';