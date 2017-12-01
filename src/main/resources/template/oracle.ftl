CREATE TABLE "${table.tableName?upper_case}" (
"ID" VARCHAR2(32) NOT NULL,
<#if table.columns?exists>
    <#list table.columns as attr>
        <#if attr.columnName?upper_case != "ID">
        "${attr.columnName?upper_case}" <#if attr.isAlone> ${attr.typeName}<#else> ${attr.typeName}(${attr.columnSize}<#if attr.isFloat>,${attr.decimalDigits}</#if>)</#if><#if !attr.isBlob&&attr.columnDef?exists&&attr.columnDef!=''> DEFAULT <#if attr.isString>'${attr.columnDef}'<#else>${attr.columnDef}</#if></#if><#if !attr.nullable> NOT NULL</#if> <#if attr_has_next>,</#if>
        </#if>
    </#list>
</#if>
)
LOGGING
NOCOMPRESS
NOCACHE
;
comment on table ${table.tableName?upper_case} is '${table.remarks}';
<#list table.columns as attr>
    <#if attr.remarks?exists&&attr.remarks!=''>
    comment on column ${table.tableName?upper_case}.${attr.columnName?upper_case} is '${attr.remarks}';
    </#if>
</#list>
ALTER TABLE "${table.tableName?upper_case}" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "${table.tableName?upper_case}" ADD PRIMARY KEY ("ID");