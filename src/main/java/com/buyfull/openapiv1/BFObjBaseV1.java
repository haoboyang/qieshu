package com.buyfull.openapiv1;

import java.text.ParseException;

public interface BFObjBaseV1 {
	public BFOpenAPI getContext();
	/**
	 * 销毁对象，释放内存
	 */
	public void destory();
	/**
	 * BFAPP, BFScene, BFInstallSite等对象的基本属性可以通过此方法获取数据
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean fetch() throws BFException, ParseException;
	
	/**
	 * BFAPP, BFScene, BFInstallSite等对象的基本属性更新后需要调用此方法上传更新
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean update() throws BFException, ParseException;

	/**
	 * @return 每个对象都有唯一的UUID
	 */
	public String uuid();

	public String createTime();

	/**
	 * @return 最后一次更新日期时间
	 */
	public long	lastUpdateTime();
	/**
	 * @return 第三方授权的百蝠场景中，所有的属性和安装位置都是只读的
	 */
	public boolean isReadOnly();
	/**
	 * @return 当前对象是否有效，比如百蝠场景被删除后，下面包括的所有安装位置都无效了
	 */
	public boolean isValid();
}
