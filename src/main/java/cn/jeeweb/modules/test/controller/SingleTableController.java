package cn.jeeweb.modules.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.jeeweb.core.common.controller.BaseCRUDController;
import cn.jeeweb.core.security.shiro.authz.annotation.RequiresPathPermission;
import cn.jeeweb.modules.test.entity.SingleTable;

/**   
 * @Title: 单表测试
 * @Description: 单表测试
 * @author jeeweb
 * @date 2017-07-22 23:05:36
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("${admin.url.prefix}/test/singletable")
@RequiresPathPermission("test:singletable")
public class SingleTableController extends BaseCRUDController<SingleTable, String> {

}
