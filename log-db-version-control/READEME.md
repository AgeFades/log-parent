# Liquibase数据库版本控制

## 官网地址

[官方文档](https://www.liquibase.org/)

## 使用规范

**本人项目实际使用中定义规范，具体使用者可按公司实际情况来定**

1. id作为执行记录表主键,为防止冲突,需严格遵守命名规范:
   1. id = {yyyyMMdd}-{分配的使用数字+当日递增}，举例如下：
   2. 20230105-100(该时间的被分配到100数据段用户的第一个sql变更文件记录)
   3. 20230105-101(该时间的被分配到100数据段用户的第二个sql变更文件记录)
2. author必须如实填写操作用户的真实姓名
3. comments必须填写执行该sql文件的注释以表明其意义
4. 已经被数据库执行过的changeSet严禁修改,每一次执行记录都会用MD5对文件加密存值,用于下次应用启动判断该文件是否被执行过
   1. 所有对已执行过的sql想要回滚或修改,需另外写changeSet进行变更
5. 实际sql文件存储目录分为 data(对数据的操作)/structure(对库表结构的操作)
6. sql文件命名,为防止绝对路径冲突,在最后加上{该目录下同名文件的数量递增}，举例如下：
   1. db/structure/1.0/alter_user.sql
   2. db/structure/1.0/alter_user_1.sql
7. 对表或字段的操作,禁止带上生成的编码及排序规则,统一使用db默认的编码及排序规则
   1. 错误示例：（不同开发人员用不同平台生成出来的SQL，可能导致各个表的编码和排序规则不一致，造成项目开发中各种奇怪的bug）
   2. ![](https://agefades-note.oss-cn-beijing.aliyuncs.com/1672890901135.png)

**代码实例示范**

```yaml
# 1.0版本初始SQL执行记录文件
databaseChangeLog:
   - changeSet:
        id: 20230105-100
        author: AgeFades
        comments: 初始化xx项目库表结构及数据
        changes:
           # sql多的可用文件指定
           - sqlFile:
                encoding: utf8
                path: classpath:db/sql/structure/1.0/db-structure-init.sql
           - sqlFile:
                encoding: utf8
                path: classpath:db/sql/data/1.0/db-data-init.sql
           # sql少的可直接执行sql
           - sql: alter table test add column agefades varchar(255) comment "测试changeSet直接执行sql";
```

