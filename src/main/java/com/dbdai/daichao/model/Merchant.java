package com.dbdai.daichao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.hebao.bixia.common.util.Table;



/**
 * 
 * 
 * @author bixia-generator
 * @email bixia@hebao.im
 * @date 2018-05-07 20:29:04
 */


@Table(name = "merchant", id = "id")
public class Merchant implements Serializable {
	private static final long serialVersionUID = 1L;
    public static String ID = "id";
    public static String MERCHAT = "merchat";
    public static String DEAL_TIME_COUNT = "dealTimeCount";
    public static String DEAL_TIME_COUNT_UNIT = "dealTimeCountUnit";
    public static String ZHI_MA_SCORE = "zhiMaScore";
    public static String MIN_AGE = "minAge";
    public static String MAX_AGE = "maxAge";
    public static String CREATE_TIME = "createTime";
    public static String UPDATE_TIME = "updateTime";

	/**
	 * 
	 */
	private Integer id;
	/**
	 * 商户名称
	 */
	private String merchat;
	/**
	 * 处理需要时间
	 */
	private Integer dealTimeCount;
	/**
	 * 处理需要时间单位（1,分钟。2,小时）
	 */
	private Boolean dealTimeCountUnit;
	/**
	 * 芝麻分
	 */
	private Integer zhiMaScore;
	/**
	 * 最小年龄
	 */
	private Integer minAge;
	/**
	 * 最大年龄
	 */
	private Integer maxAge;
	/**
	 * 
	 */
	private LocalDateTime createTime;
	/**
	 * 
	 */
	private LocalDateTime updateTime;

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
	 * 获取：商户名称
	 */
	public String getMerchat() {
		return merchat;
	}
	
	/**
	 * 设置：商户名称
	 */
	public void setMerchat(String merchat) {
		this.merchat = merchat;
	}
	/**
	 * 获取：处理需要时间
	 */
	public Integer getDealTimeCount() {
		return dealTimeCount;
	}
	
	/**
	 * 设置：处理需要时间
	 */
	public void setDealTimeCount(Integer dealTimeCount) {
		this.dealTimeCount = dealTimeCount;
	}
	/**
	 * 获取：处理需要时间单位（1,分钟。2,小时）
	 */
	public Boolean getDealTimeCountUnit() {
		return dealTimeCountUnit;
	}
	
	/**
	 * 设置：处理需要时间单位（1,分钟。2,小时）
	 */
	public void setDealTimeCountUnit(Boolean dealTimeCountUnit) {
		this.dealTimeCountUnit = dealTimeCountUnit;
	}
	/**
	 * 获取：芝麻分
	 */
	public Integer getZhiMaScore() {
		return zhiMaScore;
	}
	
	/**
	 * 设置：芝麻分
	 */
	public void setZhiMaScore(Integer zhiMaScore) {
		this.zhiMaScore = zhiMaScore;
	}
	/**
	 * 获取：最小年龄
	 */
	public Integer getMinAge() {
		return minAge;
	}
	
	/**
	 * 设置：最小年龄
	 */
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	/**
	 * 获取：最大年龄
	 */
	public Integer getMaxAge() {
		return maxAge;
	}
	
	/**
	 * 设置：最大年龄
	 */
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
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
}
