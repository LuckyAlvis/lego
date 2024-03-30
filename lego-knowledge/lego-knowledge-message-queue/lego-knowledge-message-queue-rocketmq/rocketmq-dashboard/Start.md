# RocketMq DashBoard启动流程

1. 下载地址

``` bash
https://rocketmq.apache.org/zh/docs/deploymentOperations/04Dashboard/
https://github.com/apache/rocketmq-dashboard
https://github.com/apache/rocketmq-dashboard/releases/tag/rocketmq-dashboard-1.0.0
```

2. 打包

``` bash
cd rocketmq-dashboard-rocketmq-dashboard-1.0.0 && mvn clean package -Dmaven.test.skip=true
```

3. 启动

``` bash
cd rocketmq-dashboard-rocketmq-dashboard-1.0.0 && nohup java -jar -DServer.port=9988 -Drocketmq.namesrv.addr=127.0.0.1:9876 target/rocketmq-dashboard-1.0.0.jar &
```
4. 删除target
``` bash
cd rocketmq-dashboard-rocketmq-dashboard-1.0.0 && mvn clean
```
