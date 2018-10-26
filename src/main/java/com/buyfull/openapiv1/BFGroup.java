package com.buyfull.openapiv1;

import com.buyfull.openapiv1.dao.CodeType;
import com.buyfull.openapiv1.dao.DeviceResult;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yechangqing
 * 对应百蝠场景，每一个百蝠场景可以对应一个大范围的地理位置比如一家超市，一个小吃店，其下可以添加多个安装位置
 * 每个百蝠应用可以使用自己帐号下的所有场景，也可以授权某些场景供第三方百蝠应用识别
 */
public interface BFGroup extends BFObjBaseV1 {
	/**
	 * @return 场景名称
	 */
	public String getGroupName();
	/**
	 * @param groupName 场景名称
	 */
	public void setGroupName(String groupName);
	/**
	  * @Description: 获取识别组备注信息
	  * @Param:
	  * @return:
	  * @Author: Kevin
	  * @Date: 2018/9/4  10:32
	  */
	public String getBackup();
	
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
	//item site related methods
	/**
	 * @return 此场景下的所有安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFItem> getItemList(String itemDes ,int pageNum , int limit) throws BFException, ParseException;
	
	/**
	 * 创建一个安装位置
	 * @param itemDescrption 具体的安装位置的说明文字
	 * @param deviceSN 百蝠设备序列号，可为空
	 * @return 安装位置
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFItem createItem(String itemDescrption, String deviceSN) throws BFException, ParseException;

	/**
	 * 创建多个安装位置
	 * @param itemDescrptionList 多个安装位置列表，key为具体的安装位置的说明文字，value为百蝠设备序列号，可为空
	 * @return 安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public List<? extends BFItem> createItemList(HashMap<String, String > itemDescrptionList) throws BFException;

	/**
	 * 删除一个安装位置，所有的百蝠应用在此安装位置上的识别结果都会被删除
	 * @param is 安装位置
	 * @returntrue为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeItem(BFItem is) throws BFException;
	
	/**
	 * 删除多个安装位置，所有的百蝠应用在这些安装位置上的识别结果都会被删除
	 * @param islist
	 * @returntrue为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeItems(List<? extends BFItem> islist) throws BFException;
	
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

	/**
	  * @Description:  更新组信息
	  * @Param:
	  * @return:
	  * @Author: Kevin
	  * @Date: 2018/9/4  10:28
	  */
	public boolean update(  String groupName ,String address , String province , String city , String brand  ) throws BFException, ParseException;
	/**
	  * @Description: 批量通过设备序列号设置设备识别结果,如果设备合法设备已经创建则直接设置识别结果(识别结果为空，则次设备之前数据位也将位空)
	  *               如果设备没有创建此先创建设备,然后将再设置设备识别结果
	  * @Param:
	  * @return:
	  * @Author: Kevin
	  * @Date: 2018/9/14  10:18
      */
	public boolean setResultBySN ( List<DeviceResult>deviceResults , BFApp app ) throws BFException;
	/**
	  * @Description: 第三方设备注册，获取箧书音频文件地址
	  * @Param:  app-平台下注册的 app， deviceSN 设备序列码 ， deviceType 设备型号 ， codeType 1/2
	  * @return: {"downloadurl":"https://cdn.buyfull.cc/A-2-50-MBM-201808027.wav?attname=","outtime":1540456680815}
	  * @Author: Kevin
	  * @Date: 2018/10/25  16:30
	  */
	public String loginDynamicDevice(  BFApp app ,String deviceSN , String deviceType , CodeType codeType    ) throws BFException;

	/**
	  * @Description:  itemDes 获取动态设备列表
	  * @Param:
	  * @return:
	  * @Author: Kevin
	  * @Date: 2018/10/25  17:30
	  */
	public BFPage<? extends BFDynamicDevice> getDynamicList( String itemDes ,int pageNum , int limit) throws BFException, ParseException;

	/**
	  * @Description:  删除动态三方设备
	  * @Param:
	  * @return:  ture/false
	  * @Author: Kevin
	  * @Date: 2018/10/25  17:35
	  */
	public boolean removeDynamicDevices( List<? extends BFDynamicDevice> dynamicDevices    ) throws BFException;

}
