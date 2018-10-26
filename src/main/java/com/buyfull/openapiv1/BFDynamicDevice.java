package com.buyfull.openapiv1;

public interface BFDynamicDevice extends  BFObjBaseV1{

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
     * @return 返回绑定的百蝠设备序列号，如果是第三方授权的返回空
     */
    public String getDeviceSN();


}
