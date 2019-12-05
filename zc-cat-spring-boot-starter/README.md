# CAT

## 部署点评Cat监控项目

使用maven编译CAT找不到org.unidal.maven.plugins:codegen-maven-plugin:2.3.2
> 
    1. 下载codegen-2.3.2.jar放在本地maven仓库中或者私服,需要在这里面下载https://github.com/dianping/cat/tree/mvn-repo
    2. 删除本地仓库的报错位置的 _remote.repositories 文件
    3. 执行mvn命令； mvn clean install -Dmaven.test.skip=true  -U
    
[windows 下 war 包部署开发环境](https://www.cnblogs.com/harrychinese/p/dianping-cat-server-setup.html)

