package com.buyfull.openapiv1;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;



/**
 * @author yechangqing
 * 对应百蝠官网中的应用管理相关功能，每个APP有一组appkey和seckey，可以在百蝠SDK中初始化
 * 每个百蝠应用可以对自身创建的场景或是第三方授权的场景中的每个安装位置设定一组识别结果
 * 当百蝠SDK识别到用户在安装位置附近，这些结果会返回
 */
public interface BFApp extends  BFObjBaseV1{
	
	/**
	 * @return 应用名称
	 */
	public String getAppName();
	
	/**
	 * @return 应用的appkey，用在百蝠SDK中
	 */
	public String getAppKey();
	
	/**
	 * @return 应用的描述信息
	 */
	public String getAppDescrption();
	
	/**
	 * @return iOS程序的bundleID
	 */
	public String getiOSBundleID();

	/**
	 * @return Android程序的packageName
	 */
	public String getAndroidPackageName();

	
	/**
	 * @return 微信小程序的appID
	 */
	public String getWXAppID();

	
	/**
	 * @return 应用的seckey，用在百蝠SDK中
	 */
	public String getSecKey();

	public long  getLastUpdateTime();

	/**
	 * * 生成／重新生成此应用的seckey，成功后原seckey失效
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean generateSecKey() throws BFException, ParseException;
	
////////////////////////////////////////////////	
	/**
	 * @return 第三方授权给此应用的场景，不包括自我创建的场景
	 * @throws BFException 服务器返回具体错误信息
	 */
	public BFPage<? extends BFGroup> getAuthorizedgroupList( int pageNum ,int limit , String groupName , String backup  ) throws BFException, ParseException;
////////////////////////////////////////////////
	/**
	 * @param  GroupName 自已创建的场景或被授权使用的场景
	 * @return 此应用下针对一个场景的每个安装位置设定的识别结果的集合，会在百蝠SDK中返回json字符串数组，例如["结果1","result 2"]
	 * @throws BFException
	 */
	public HashMap<BFItem, List<String> > getRecgonizeResults(BFGroup GroupName) throws BFException, ParseException;
	/**
	 * 完全覆盖设置此场景下的所有安装位置的识别结果
	 * @param  GroupName 自已创建的场景或被授权使用的场景
	 * @param  results 此应用下针对一个场景每个安装位置设定的识别结果的集合，会在百蝠SDK中返回json字符串数组，例如["结果1","result 2"]
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean setRecgonizeResults(BFGroup GroupName, HashMap<BFItem, List<String> > results) throws BFException;
}
