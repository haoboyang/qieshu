package com.buyfull.openapiv1;

import com.buyfull.openapiv1.implement.BFItem_Implement;
import org.json.JSONException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author yechangqing
 * 百蝠OPENAPI的入口功能集合
 */
public interface BFOpenAPI {
	//百蝠场景有关的API
	/**
	 * @return 当前环境中的百蝠帐号accessKey
	 */
	public String accessKey();
	/**
	 * @return 当前环境中的百蝠帐号secretKey
	 */
	public String secretKey();

	/**
	 *
	 * param rootUrl OPENAPI服务器地址
	 * @return
	 */
	public String rootUrl() ;
	/**
	 * 返回当前帐号下自已创建的所有场景列表，不包含第三方授权的场景
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return 场景列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFGroup> getGroupList(int pageNum, int limit) throws BFException, ParseException;
	
	/**
	 * 通过关键子模糊查找相关的场景列表，不包含第三方授权的场景。关键字可多选，可为空字符串
	 * @param groupName 场景名称
	 * @param backup 品牌
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return 场景列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFGroup> findGroupsByKeyword(String groupName, String backup,  int pageNum, int limit) throws BFException, ParseException;

	/**
	 * 在当前帐号下创建一个百蝠场景
	 * @param groupName 场景名称
	 * @param address 地址
	 * @param province 省份
	 * @param city 城市
	 * @param brand 品牌
	 * @return 百蝠场景对象
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFGroup createGroup(String groupName, String address, String province, String city, String brand) throws BFException, ParseException;
	
	/**
	 * @param group 删除一个百蝠场景，它所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeGroup(BFGroup group) throws BFException;
	
	/**
	 * @param groupList 删除多个百蝠场景，它们所包含的安装位置以及安装位置上的识别结果都会被删除
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeGroups(List<? extends BFGroup> groupList) throws BFException;
	
	//百蝠场景下安装位置的API
	/**
	 * 通过百蝠设备序列号来查找绑定了的安装位置，不包含第三方授权的场景下的安装位置
	 * @param deviceSN 百蝠设备序列号，可以输入前8位来模糊匹配
	 * @param pageNum 结果分页返回，从1开始
	 * @param limit 结果分页返回，每页返回limit条，范围 1 - 200
	 * @return 安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFItem> findItemByDeviceInfo(String deviceSN, String itemKeywords, int pageNum, int limit) throws BFException, ParseException;

	/**
	 * 通过安装说明中的关键字来查找相对应的安装位置，不包含第三方授权的场景下的安装位置
	 * @param itemKeywords 安装说明中的关键字
	 * @return 安装位置列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFItem> findItemByitemDescrption(String itemKeywords,int pageNum,int limit) throws BFException, ParseException;

	//百蝠应用有关的API
	/**
	 * 返回此帐号下的所有百蝠应用列表
	 * @return 百蝠应用列表
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFApp> getAppList(int pageNum, int limit ) throws IOException, ParseException;

	/**
	 *
	 * 获取 第三方被授权app 授权列表
	 * @param pageNum
	 * @param limit
	 * @return
	 */
	public BFPage<String>getAuthAppKeys( int pageNum, int limit  ) throws BFException;

    /**
     *
     *获取第三方被授权 app 下面场景列表
     * @param pageNum
     * @param limit
     * @param appKey
     * @return
     */
	public BFPage<String>getAuthAppGroupList( int pageNum , int limit , String appKey    ) throws BFException;

	/**
	 * 创建一个百蝠应用
	 * @param appName 名称
	 * @param descrption 说明
	 * @param bundleID iOS应用
	 * @param packageName Android应用
	 * @param wxAppID 微信小程序
	 * @return 百蝠应用对象
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFApp createApp(String appName, String descrption, String bundleID, String packageName, String wxAppID) throws BFException, JSONException, ParseException;
	
	/**
	 * 删除一个百蝠应用，删除后其下设置的识别结果都会被删除
	 * @param app 百蝠应用
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean removeApp(BFApp app) throws BFException;

	/**
	 *
	 * 移除授权 app,删除所有相关联场景及安装位置信息
	 * @param appKey
	 * @return
	 * @throws BFException
	 */
	public boolean removeAuthorizedApp( String appKey  )throws BFException;

	/**
	 *
	 * 用户获取被app 被授权场景下面安装位置列表
	 * @param groupId
	 * @return
	 */
	BFPage<String> getAuthorizedItemList(int pageNum , int limit , String groupId ) throws BFException, ParseException;

}
