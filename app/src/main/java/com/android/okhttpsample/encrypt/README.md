##### EncryptManager用法

EncryptManager主要用于对核心数据加密 ，目前我们要求保存在文件里(包括 sp,db 文件)的核心数据必须加密而不能明文展示。
具体用法如下：				
比如之前是：			
				
```java
String getToken(){
	UserDO userDO = mDAO.query();
	return userDO.getToken;
}

void setToken(String token,UserDO userDO){
	userDO.setToken(token);
	mDAO.update(userDO);
}
```					

使用EncryptManager :
				
```java
String getToken(){
	UserDO userDO= mDAO.query();
	String token = userDO.getToken();
	
	//判断从数据库查询的token是否已经加密了，考虑到线上数据升级，该步骤必写									
	if(EncryptManager.getInstance().useEncrypt(userDO.getToken)){
		token = EncryptManager.getInstance().decryptKey(userDO.getToken);	 
	}
	return token;
}
	
/**
*
*这里的入参 是明文token ，如果不确定，可以使用 *EncryptManager.getInstance.useEncrypt()
*判断是否是已经加密的token
*/
void setToken(String token,UserDO userDO){
	String tmpEncrypt=EncryptManager.getInstance(). encryptKey(token);
	userDO.setToken(tmpEncrypt);
	mDAO.update(userDO);
}
```					
review by zxb:

java原生不支持 AES/CBC/PKCS7Padding，要第三方支持; 这样的加解密会导致 java 服务器不能使用；