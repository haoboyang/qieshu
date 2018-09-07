package com.buyfull.openapiv1;


import org.json.JSONException;
import java.text.ParseException;

/**
 * @author yechangqing
 * 对应百蝠场景中的安装位置，每一个安装位置可以绑定一个百蝠声标设备，是一个最小可识别单位
 * 每个百蝠应用可以针对一个安装位置设置自定义的识别结果，百蝠SDK识别后会返回识别结果
 */
public interface BFItem extends BFObjBaseV1{
	/**
	 * @return 所属于的场景
	 */
	public BFGroup getGroup() throws BFException, ParseException;

	/**
	 *
	 * @return bound of groupId
	 */
	public String getGroupId() ;
	/**
	 * 如果是第三方授权的返回0
	 * @return 百蝠绑定子码，在线下门店布署时可以在百蝠专用小程序中输入此码来将一个百蝠设备绑定到此安装位置上
	 */
	public Long getBoundSubCode();
	
	/**
	 * @return 返回安装位置的具体说明，此说明文字会在百蝠专用小程序中显示给线下安装人员，请输入详细的可读信息
	 * 比如：XXX广场2层A区中庭电梯厅门口的天花板，方向垂直向下
	 */
	public String getItemDescrption();
	/**
	 * @param itemDescrption 返回安装位置的具体说明，此说明文字会在百蝠专用小程序中显示给线下安装人员，请输入详细的可读信息
	 * 比如：XXX广场2层A区中庭电梯厅门口的天花板，方向垂直向下
	 */
	public void setItemDescrption(String itemDescrption);
	
	/**
	 * @return 返回绑定的百蝠设备序列号，如果是第三方授权的返回空
	 */
	public String getDeviceSN();
	/**
	 * 绑定百蝠设备序列号，第三方授权的不可绑定
	 * @param deviceSN 绑定百蝠设备序列号，在二维码贴纸下的16位数字。一个设备只能被绑定在一个安装位置上，更换绑定前必须解绑
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean bindDeviceSN(String deviceSN) throws BFException, JSONException, ParseException;
	/**
	 * 解绑百蝠设备，第三方授权的不可解绑
	 * @return true为成功 false为失败
	 * @throws BFException 服务器返回具体错误信息
	 */
	public boolean unbindDeviceSN() throws BFException, ParseException;
}
