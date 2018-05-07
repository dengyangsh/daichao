package com.dbdai.daichao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.hebao.bixia.common.util.Table;


/**
 * 
 * 
 * @author bixia-generator
 * @email bixia@hebao.im
 * @date 2018-05-07 20:29:04
 */


@Table(name = "loan_info", id = "id")
public class LoanInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    public static String ID = "id";
    public static String SOCRE = "socre";
    public static String AGE = "age";
    public static String AMOUNT = "amount";
    public static String CREATE_TIME = "createTime";
    public static String UPDATE_TIME = "updateTime";
    public static String PHONE = "phone";

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 
	 */
	private Integer socre;
	/**
	 * 
	 */
	private Integer age;
	/**
	 * 
	 */
	private BigDecimal amount;
	/**
	 * 
	 */
	private LocalDateTime createTime;
	/**
	 * 
	 */
	private LocalDateTime updateTime;
	/**
	 * 
	 */
	private String phone;

	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getSocre() {
		return socre;
	}
	
	/**
	 * 设置：
	 */
	public void setSocre(Integer socre) {
		this.socre = socre;
	}
	/**
	 * 获取：
	 */
	public Integer getAge() {
		return age;
	}
	
	/**
	 * 设置：
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	
	/**
	 * 设置：
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：
	 */
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置：
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * 设置：
	 */
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 设置：
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
