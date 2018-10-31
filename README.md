# qieshu 识别内容后台管理系统 sdk
### 简介;
此 SDK 适用于 Java 8版本。使用此 SDK 构建您自己识别内容后台管理系统能让您以非常便捷地方式管理您的箧书识别设备及识别内容。

Java SDK 属于箧书SDK之一，主要有如下功能：

1. 提供识别组操作管理,识别组授权<br>
2. 提供识别 app 管理;<br>
3. 提供专业超声波设备管理;<br>
4. 提供设备内容识别设备管理;<br>
5. 提供第三方设备动态音频获取;<br>

### 安装

```java
          <dependency>
			<groupId>net.qieshu</groupId>
			<artifactId>qieshu-sdk</artifactId>
			<version>2.0</version>
	  </dependency>
```

## 注册调用`app`信息，获取识别 `(ak,sk)` 用于获取识别`token`
### 注册调用`app`

```java
          String secretId     = "secret id";
	  String secretKey    = "secret key";
	  String appName = "声连码小程序";
          String comment = "声连码小程序用途描述";
          String bundleID = "com.qieshu.IOS.packageName"; //ios 包名
          String packageName = "com.qieshu.IOS.packageName"; //ios 包名
          String wxAppID = ""; //wxs1d3f1s3df13sdf 微信小程序 WXID , 如果是 WXID ， 其它两个包名不需要填写
	  
	  BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey ) ;
          try {
                BFApp app  = authManager.createApp( appName ,comment ,bundleID ,packageName ,  wxAppID);  
       	  } catch (BFException e) {
               System.err.println(  e );
          } catch (ParseException e) {
            System.err.println(  e );
          }		

```

### 获取`app`注册列表 

```java
	  String secretId     = "secret id";
	  String secretKey    = "secret key";
          int    pageNum      = 1;
          int    limit        = 200 ; 	
	  BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey ) ;
          try {
            BFPage<? extends BFApp> appList = authManager.getAppList( pageNum , limit ) ; //带分页功能
          } catch (ParseException e) {
            e.printStackTrace();
            System.err.println(  e );
          } catch (IOException e) {
            e.printStackTrace();
         }

```

### 删除 `app` `(删除 app 会清空该 app 下面所有识别组里面识别内容)`

```java
     String secretId     = "secret id";
     String secretKey    = "secret key";
     String appKey       = "your appKey";
     BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey ) ;
     try {
            BFApp    bfApp = authManager.getApp( appKey);
            boolean deleteStatus = authManager.removeApp(bfApp);
     } catch (ParseException e) {
            e.printStackTrace();
     } catch (BFException e) {
            System.out.println(  e.getErrMsg() );
     }

```

### 获取app详情

```java
	String secretId     = "secret id";
	String secretKey    = "secret key";
        String appKey       = "your appKey";
        BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey );
	try {
            BFApp    bfApp  =   authManager.getApp( appKey) ;
            System.out.println( "this is uuid          = "   + bfApp.uuid()   );
            System.out.println( "this is appName       = "   + bfApp.getAppName()   );
            System.out.println( "this is appKey        = "   + bfApp.getAppKey()   );
            System.out.println( "this is secKey        = "   + bfApp.getSecKey()   );
            System.out.println( "this is WXAppID       = "   + bfApp.getWXAppID()   );
            System.out.println( "this is iOSBundleID   = "   + bfApp.getiOSBundleID()   );
            System.out.println( "this is PackageName   = "   + bfApp.getAndroidPackageName()   );
            System.out.println( "this is comment       = "   + bfApp.getAppDescrption()   );
            System.out.println( "this is LastUpTime    = "   + bfApp.getLastUpdateTime()   );
        } catch (ParseException e) {
            System.out.println(  e );
        } catch (BFException e) {
            e.printStackTrace();
            System.out.println( e.getErrMsg() );
        }	 
```

### 更新 `app`密钥

     ```java
        String secretId     = "secret id";
	String secretKey    = "secret key";
        String appKey       = "your appKey";
        BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey );
	try {
            BFApp    bfApp        = authManager.getApp( appKey);
            boolean  updateStatus = bfApp.generateSecKey() ;
            System.out.println(  updateStatus );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (BFException e) {
            System.out.println(  e.getErrMsg() );
        }
     ```
          
### `app` 添加识别组内安装位置设置识别内容

    ```java
    
        String secretId     = "secret id";
	String secretKey    = "secret key";
        String appKey       = "your appKey";
        String group_uuid   = "your group key";
        String itemName     = "";//模糊匹配
        int    pageNum      = 1 ;
        int    limit        = 200;
	
        BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey );
	try {
            BFApp    bfApp        = authManager.getApp( appKey);
            BFGroup  bfGroup      = authManager.getGroup(group_uuid);
            BFPage<? extends BFItem> itemPage = bfGroup.getItemList( itemName , pageNum , limit ) ;
            List<? extends BFItem> itemList =itemPage.getResultList();
            List<String> tags =  null ;
            HashMap<BFItem, List<String>> resultTags = new HashMap<>();
            for (BFItem bfItem : itemList) {
                tags = new ArrayList<>() ;
                tags.add("hello");
		tags.add("word");
                resultTags.put(  bfItem , tags  ); //总 tag字符长度不能超过4094
            }
            boolean setStatus = bfApp.setRecgonizeResults( bfGroup ,  resultTags    ) ;
            System.out.println(  updateStatus );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (BFException e) {
            System.out.println(  e.getErrMsg() );
        }        
    
    ```

### 获取 `app` 下某个识别组中安装位置里面的识别内容

```java

        String secretId     = "secret id";
	String secretKey    = "secret key";
        String appKey       = "your appKey";
        String group_uuid   = "your group key";
        String itemName     = "";//模糊匹配
        int    pageNum      = 1 ;
        int    limit        = 200;
	
	BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey );
	try {
      
	BFApp    bfApp   = authManager.getApp( appKey);
        BFGroup  bfGroup = authManager.getGroup(group_uuid);
        HashMap<BFItem, List<String>> resultTags = bfApp.getRecgonizeResults( bfGroup  ) ;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (BFException e) {
            System.out.println(  e.getErrMsg() );
        }

```


## 创建识别组

```java
          String secretId     = "secret id";
	  String secretKey    = "secret key";
	  BFOpenAPI  authManager = createBFOpenAPInstance( secretId ,secretKey ) ;
	 //创建识别组描述参数
	  String groupName = "中兴路店" ;
          String address   = "中兴路373号" ;
          String province  = "上海市" ;
          String city      = "静安区" ;
          String brand     = "百蝠" ;
	  
          BFGroup group = authManager.createGroup( groupName , address , province , city , brand );

```

## 管理识别组

###  




