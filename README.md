## 项目介绍

1. JDK版本使用 `JDK11`
2. 需安装 `Lombok` 插件
3. 项目采用父子多模块的Maven工程结构
4. .md文件为Markdown格式文档，建议使用 `Typora` 阅读编辑

## 安装组件

- Nacos：常规部署一个单机节点Nacos即可
- MySQL：基础SQL参见SQL目录下single.sql文件
- Redis：常规部署一个单机节点Redis即可
- ELK：参见技术分享文档
  - [直接体验地址](http://kibana.agefades.com/app/home#/)
  - 账号：elastic
  - 密码：elastic
- Sentry：参见技术分享文档
  - [直接体验地址](http://sentry.agefades.com/)
  - 账号密码需通知我邮件地址，待发送邀请邮件后自行注册登陆体验

## 接口文档地址

- 本地 http://localhost:8000/swagger-ui/index.html

![](https://agefades-note.oss-cn-beijing.aliyuncs.com/1638933747606.png)

## 微服务模块定义

- 本项目微服务模块项目名称定义标准：
  - pom聚合工程的命名
    - 前缀均为  **log-**
    - 后缀为该服务名称
  - jar子工程的命名
    - 每个业务微服务pom聚合工程，均对应三个jar子工程
      - 后缀为 **common**，定义该服务 `entity`、`req`、`resp` 等公用代码
      - 后缀为 **client**，表示为该服务提供给系统其它服务调用的Feign客户端，依赖 **common**
      - 后缀为 **service**，表示为该服务向前端暴露接口，依赖 **common**

### 示例

- 系统微服务
  - pom命名为: **log-activity**
  - 三个jar子工程分别为 **log-activity-common、log-activity-client、log-activity-service**

## 目录结构

```shell
├── log-common										# 公共模块pom聚合工程
│   ├── log-common-cache					# 公共缓存模块
│   ├── log-common-core						# 公共核心模块
│   ├── log-common-datasource			# 公共数据源模块
│   └── log-common-log						# 公共日志模块
├── log-gateway										# Gateway网关
│   ├── src
│   └── target
├── log-order											# 订单微服务
│   ├── log-order-client					# 订单对内暴露Feign接口模块
│   ├── log-order-common					# 订单公共模块
│   └── log-order-service					# 订单对外暴露Controller模块
├── log-system										# 系统微服务
│   ├── log-system-client					# ...以下如上
│   ├── log-system-common					
│   └── log-system-service
└── sql														# 项目基础SQL文件
```

## 版本升级

- 当项目开发版本迭代时，需更新项目版本

### 方式

- 全局统一修改项目parent工程版本号

### 示例

![](https://agefades-note.oss-cn-beijing.aliyuncs.com/1638933471333.png)

