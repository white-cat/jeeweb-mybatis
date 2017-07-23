package cn.jeeweb.modules.test.service.impl;

import cn.jeeweb.core.common.service.impl.CommonServiceImpl;
import cn.jeeweb.core.query.wrapper.EntityWrapper;
import cn.jeeweb.modules.test.mapper.TestOrderMainMapper;
import cn.jeeweb.modules.test.entity.TestOrderMain;
import cn.jeeweb.modules.test.service.ITestOrderMainService;
import cn.jeeweb.modules.test.entity.TestOrderTicket;
import cn.jeeweb.modules.test.service.ITestOrderTicketService;
import cn.jeeweb.modules.test.entity.TestOrderCustomer;
import cn.jeeweb.modules.test.service.ITestOrderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.jeeweb.core.utils.ServletUtils;
import cn.jeeweb.core.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

/**   
 * @Title: 订单主表
 * @Description: 订单主表
 * @author jeeweb
 * @date 2017-07-22 20:05:35
 * @version V1.0   
 *
 */
@Transactional
@Service("testOrderMainService")
public class TestOrderMainServiceImpl  extends CommonServiceImpl<TestOrderMainMapper,TestOrderMain> implements  ITestOrderMainService {
	@Autowired
	private ITestOrderTicketService testOrderTicketService;
	@Autowired
	private ITestOrderCustomerService testOrderCustomerService;
	
	@Override
	public boolean insert(TestOrderMain testOrderMain) {
		// 保存主表
		super.insert(testOrderMain);
		// 保存机票信息
		String testOrderTicketListJson = StringEscapeUtils
				.unescapeHtml4(ServletUtils.getRequest().getParameter("testOrderTicketListJson"));
		List<TestOrderTicket> testOrderTicketList = JSONObject.parseArray(testOrderTicketListJson, TestOrderTicket.class);
		for (TestOrderTicket testOrderTicket : testOrderTicketList) {
			// 保存字段列表
			testOrderTicket.setOrder(testOrderMain);
			testOrderTicketService.insert(testOrderTicket);
		}
		// 保存客户信息
		String testOrderCustomerListJson = StringEscapeUtils
				.unescapeHtml4(ServletUtils.getRequest().getParameter("testOrderCustomerListJson"));
		List<TestOrderCustomer> testOrderCustomerList = JSONObject.parseArray(testOrderCustomerListJson, TestOrderCustomer.class);
		for (TestOrderCustomer testOrderCustomer : testOrderCustomerList) {
			// 保存字段列表
			testOrderCustomer.setOrder(testOrderMain);
			testOrderCustomerService.insert(testOrderCustomer);
		}
		return true;
	}
	
	@Override
	public boolean insertOrUpdate(TestOrderMain testOrderMain) {
		try {
			// 获得以前的数据
			List<TestOrderTicket> oldTestOrderTicketList = testOrderTicketService.selectList(new EntityWrapper<TestOrderTicket>(TestOrderTicket.class).eq("order.id",testOrderMain.getId()));
			// 字段
			String testOrderTicketListJson = StringEscapeUtils
					.unescapeHtml4(ServletUtils.getRequest().getParameter("testOrderTicketListJson"));
			List<TestOrderTicket> testOrderTicketList = JSONObject.parseArray(testOrderTicketListJson,
					TestOrderTicket.class);
			// 获得以前的数据
			List<TestOrderCustomer> oldTestOrderCustomerList = testOrderCustomerService.selectList(new EntityWrapper<TestOrderCustomer>(TestOrderCustomer.class).eq("order.id",testOrderMain.getId()));
			// 字段
			String testOrderCustomerListJson = StringEscapeUtils
					.unescapeHtml4(ServletUtils.getRequest().getParameter("testOrderCustomerListJson"));
			List<TestOrderCustomer> testOrderCustomerList = JSONObject.parseArray(testOrderCustomerListJson,
					TestOrderCustomer.class);
			// 更新主表
			super.insertOrUpdate(testOrderMain);
			testOrderTicketList = JSONObject.parseArray(testOrderTicketListJson, TestOrderTicket.class);
			List<String> newsTestOrderTicketIdList = new ArrayList<String>();
			// 保存或更新数据
			for (TestOrderTicket testOrderTicket : testOrderTicketList) {
				// 设置不变更的字段
				if (StringUtils.isEmpty(testOrderTicket.getId())) {
					// 保存字段列表
					testOrderTicket.setOrder(testOrderMain);
					testOrderTicketService.insert(testOrderTicket);
				} else {
					testOrderTicketService.insertOrUpdate(testOrderTicket);
				}
				newsTestOrderTicketIdList.add(testOrderTicket.getId());
			}

			// 删除老数据
			for (TestOrderTicket testOrderTicket : oldTestOrderTicketList) {
				String testOrderTicketId = testOrderTicket.getId();
				if (!newsTestOrderTicketIdList.contains(testOrderTicketId)) {
					testOrderTicketService.deleteById(testOrderTicketId);
				}
			}
			testOrderCustomerList = JSONObject.parseArray(testOrderCustomerListJson, TestOrderCustomer.class);
			List<String> newsTestOrderCustomerIdList = new ArrayList<String>();
			// 保存或更新数据
			for (TestOrderCustomer testOrderCustomer : testOrderCustomerList) {
				// 设置不变更的字段
				if (StringUtils.isEmpty(testOrderCustomer.getId())) {
					// 保存字段列表
					testOrderCustomer.setOrder(testOrderMain);
					testOrderCustomerService.insert(testOrderCustomer);
				} else {
					testOrderCustomerService.insertOrUpdate(testOrderCustomer);
				}
				newsTestOrderCustomerIdList.add(testOrderCustomer.getId());
			}

			// 删除老数据
			for (TestOrderCustomer testOrderCustomer : oldTestOrderCustomerList) {
				String testOrderCustomerId = testOrderCustomer.getId();
				if (!newsTestOrderCustomerIdList.contains(testOrderCustomerId)) {
					testOrderCustomerService.deleteById(testOrderCustomerId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}
	
	
	
}
