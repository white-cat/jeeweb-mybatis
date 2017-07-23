package ${packageName}<#if moduleName?exists><#if moduleName!=''>.${moduleName}</#if></#if>.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.jeeweb.core.common.controller.BaseCRUDController;
import cn.jeeweb.core.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.core.query.wrapper.EntityWrapper;
import java.util.List;

import ${packageName}<#if moduleName?exists><#if moduleName!=''>.${moduleName}</#if></#if>.entity.${entityName?cap_first};
<#list schedules as schedule>
import ${packageName}<#if moduleName?exists><#if moduleName!=''>.${moduleName}</#if></#if>.entity.${schedule.className?cap_first};
import ${packageName}<#if moduleName?exists><#if moduleName!=''>.${moduleName}</#if></#if>.service.I${schedule.className?cap_first}Service;
</#list>

/**   
 * @Title: ${functionName}
 * @Description: ${functionDesc}
 * @author ${functionAuthor}
 * @date ${time}
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("${r'${admin.url.prefix}'}/${moduleName}/${entityName?lower_case}")
@RequiresPathPermission("${moduleName}:${entityName?lower_case}")
public class ${entityName?cap_first}Controller extends BaseCRUDController<${entityName?cap_first}, String> {
	<#list schedules as schedule>
	@Autowired
	private I${schedule.className?cap_first}Service ${schedule.className?uncap_first}Service;
	</#list>
	
	@Override
	public void preEdit(${entityName?cap_first} ${entityName?uncap_first}, Model model, HttpServletRequest request, HttpServletResponse response) {
	    <#list schedules as schedule>
		// 获得${schedule.remarks!}数据
		List<${schedule.className?cap_first}> ${schedule.className?uncap_first}List = ${schedule.className?uncap_first}Service.selectList(new EntityWrapper<${schedule.className?cap_first}>(${schedule.className?cap_first}.class).eq("${schedule.parentField?uncap_first}.id",${entityName?uncap_first}.getId()));
		model.addAttribute("${schedule.className?uncap_first}List", ${schedule.className?uncap_first}List);
		</#list>
	}
}
