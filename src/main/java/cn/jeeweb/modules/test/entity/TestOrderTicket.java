package cn.jeeweb.modules.test.entity;

import cn.jeeweb.core.common.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import java.util.Date;
import cn.jeeweb.modules.sys.entity.User;

/**   
 * @Title: 机票信息
 * @Description: 机票信息
 * @author jeeweb
 * @date 2017-07-22 20:05:35
 * @version V1.0   
 *
 */
@TableName("test_order_ticket")
@SuppressWarnings("serial")
public class TestOrderTicket extends AbstractEntity<String> {

    /**id*/
    @TableId(value = "id", type = IdType.UUID)
	private String id;
    /**航班号*/
    @TableField(value = "fltno")
	private String fltno;
    /**航班时间*/
    @TableField(value = "flytime")
	private Date flytime;
    /**备注信息*/
    @TableField(value = "remarks")
	private String remarks;
    /**order_id*/
    @TableField(value = "order_id",el="order.id")
	private TestOrderMain order;
    /**创建者*/
    @TableField(value = "create_by",el="createBy.id")
	private User createBy;
    /**创建时间*/
    @TableField(value = "create_date")
	private Date createDate;
    /**更新者*/
    @TableField(value = "update_by",el="updateBy.id")
	private User updateBy;
    /**更新时间*/
    @TableField(value = "update_date")
	private Date updateDate;
    /**删除标记（0：正常；1：删除）*/
    @TableField(value = "del_flag")
	private String delFlag;
	
	/**
	 * 获取  id
	 *@return: String  id
	 */
	public String getId(){
		return this.id;
	}

	/**
	 * 设置  id
	 *@param: id  id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取  fltno
	 *@return: String  航班号
	 */
	public String getFltno(){
		return this.fltno;
	}

	/**
	 * 设置  fltno
	 *@param: fltno  航班号
	 */
	public void setFltno(String fltno){
		this.fltno = fltno;
	}
	/**
	 * 获取  flytime
	 *@return: Date  航班时间
	 */
	public Date getFlytime(){
		return this.flytime;
	}

	/**
	 * 设置  flytime
	 *@param: flytime  航班时间
	 */
	public void setFlytime(Date flytime){
		this.flytime = flytime;
	}
	/**
	 * 获取  remarks
	 *@return: String  备注信息
	 */
	public String getRemarks(){
		return this.remarks;
	}

	/**
	 * 设置  remarks
	 *@param: remarks  备注信息
	 */
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	/**
	 * 获取  order
	 *@return: TestOrderMain  order_id
	 */
	public TestOrderMain getOrder(){
		return this.order;
	}

	/**
	 * 设置  order
	 *@param: order  order_id
	 */
	public void setOrder(TestOrderMain order){
		this.order = order;
	}
	/**
	 * 获取  createBy
	 *@return: User  创建者
	 */
	public User getCreateBy(){
		return this.createBy;
	}

	/**
	 * 设置  createBy
	 *@param: createBy  创建者
	 */
	public void setCreateBy(User createBy){
		this.createBy = createBy;
	}
	/**
	 * 获取  createDate
	 *@return: Date  创建时间
	 */
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 * 设置  createDate
	 *@param: createDate  创建时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 * 获取  updateBy
	 *@return: User  更新者
	 */
	public User getUpdateBy(){
		return this.updateBy;
	}

	/**
	 * 设置  updateBy
	 *@param: updateBy  更新者
	 */
	public void setUpdateBy(User updateBy){
		this.updateBy = updateBy;
	}
	/**
	 * 获取  updateDate
	 *@return: Date  更新时间
	 */
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 * 设置  updateDate
	 *@param: updateDate  更新时间
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 * 获取  delFlag
	 *@return: String  删除标记（0：正常；1：删除）
	 */
	public String getDelFlag(){
		return this.delFlag;
	}

	/**
	 * 设置  delFlag
	 *@param: delFlag  删除标记（0：正常；1：删除）
	 */
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	
}
