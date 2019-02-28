
maven 安装阿里支付的SDK
本地仓库
```
//安装jar文件
mvn install:install-file -Dfile=alipay-sdk-java-3.3.2.jar -DgroupId=com.alipay.sdk -DartifactId=alipay-sdk-java -Dversion=3.3.2 -Dpackaging=jar
//安装源文件
mvn install:install-file -Dfile=alipay-sdk-java-3.3.2-source.jar -DgroupId=com.alipay.sdk -DartifactId=alipay-sdk-java -Dversion=3.3.2 -Dpackaging=jar -Dclassifier=sources

#mvn install:install-file -Dfile=a.jar -DgroupId=gid -DartifactId=aid -Dversion=0.0.1 -Dpackaging=jar -Dclassifier=javadoc
```

发布到私服
```
mvn deploy:deploy-file -Dmaven.test.skip=true -Dfile=alipay-sdk-java-3.3.2.jar -Dsources=alipay-sdk-java-3.3.2-source.jar -DgroupId=com.alipay.sdk -DartifactId=alipay-sdk-java -Dversion=3.3.2 -Dpackaging=jar -DrepositoryId=snapshots -Durl=http://10.0.2.xx:8080/nexus/content/repositories/snapshots/

```

>参考文章
https://blog.csdn.net/zwx19921215/article/details/83828783

>命令参数
http://maven.apache.org/plugins/maven-deploy-plugin/deploy-file-mojo.html