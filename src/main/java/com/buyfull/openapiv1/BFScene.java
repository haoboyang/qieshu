package com.buyfull.openapiv1;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yechangqing
 * 对应百蝠场景，每一个百蝠场景可以对应一个大范围的地理位置比如一家超市，一个小吃店，其下可以添加多个安装位置
 * 每个百蝠应用可以使用自己帐号下的所有场景，也可以授权某些场景供第三方百蝠应用识别
 */
public interface BFScene extends BFObjBaseV1 {
	/**
	 * @return 场景名称
	 */
	public String getSenceName();
	/**
	 * @param senceName 场景名称
	 */
	public void setSenceName(String senceName);
	
	/**
	 * @return 具体地址
	 */
	public String getAddress();
	/**
	 * @param address 具体地址
	 */
	public void setAddress(String address);
	
	/**
	 * @return 省份
	 */
	public String getProvince();
	/**
	 * @param province 省份
	 */
	public void setProvince(String province);

	/**
	 * @return 城市
	 */
	public String getCity();
	/**
	 * @param city 城市
	 */
	public void setCity(String city);

	/**
	 * @return 品牌
	 */
	public String getBrand();
	/**
	 * @param brand 品牌
	 */
	public void setBrand(String brand);
	
	/**
	 * 如果是第三方授权的返回0
	 * @return 百蝠绑定码，在线下门店布署时可以在百蝠专用小程序中输入此码来将一个百蝠设备绑定到此场景中的某一个安装位置上，需结合绑定子码
	 */
	public Long getBoundCode();

	/**
	 * 生成／重新生成此场景的绑定码，原绑定码立即失效
	 * 如果是第三方授权的返回false
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean generateBoundCode() throws BFException, ParseException;
	
	////////////////////////////////////////
	//install site related methods
	/**
	 * @return 此场景下的所有安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFInstallSite> getInstallSiteList(int pageNum , int limit) throws BFException, ParseException;
	
	/**
	 * 创建一个安装位置
	 * @param installDescrption 具体的安装位置的说明文字
	 * @param deviceSN 百蝠设备序列号，可为空
	 * @return 安装位置
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFInstallSite createInstallSite(String installDescrption, String deviceSN) throws BFException, ParseException;

	/**
	 * 创建多个安装位置
	 * @param installDescrptionList 多个安装位置列表，key为具体的安装位置的说明文字，value为百蝠设备序列号，可为空
	 * @return 安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public List<? extends BFInstallSite> createInstallSiteList(HashMap<String, String > installDescrptionList) throws BFException;

	/**
	 * 删除一个安装位置，所有的百蝠应用在此安装位置上的识别结果都会被删除
	 * @param is 安装位置
	 * @returntrue为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeInstallSite(BFInstallSite is) throws BFException;
	
	/**
	 * 删除多个安装位置，所有的百蝠应用在这些安装位置上的识别结果都会被删除
	 * @param islist
	 * @returntrue为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeInstallSites(List<? extends BFInstallSite> islist) throws BFException;
	
	///////////////////////////////////////////
	//authorize related methods
	/**
	 * 返回此场景已经授权了的第三方百蝠应用的APPKEY的列表
	 * @return APPKEY列表
	 * @throws BFException
	 */
	public BFPage<String> getAuthorizedAppKeys( int pageNum , int limit    ) throws BFException;
	
	/**
	 * 	 * 将此场景授权给第三方百蝠应用
	 * @param appKey 第三方百蝠应用的APPKEY
	 * @param startDate 授权起始日期时间
	 * @param endDate 授权终止日期时间
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean addAuthorizedApp(String appKey, String backupName ,Date startDate, Date endDate) throws BFException;

	/**
	 * 取消此场景授权给第三方百蝠应用，第三方应用针对此场景添加的识别结果会被删除
	 * @param appKey 第三方百蝠应用的APPKEY
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeAuthorizedApp(String appKey) throws BFException;
}
