# newEagleBackEnd

战鹰系统后端项目，基于 Spring Boot、MyBatis 和 SQLite，提供人员位置、案件热力图、工作量统计、腾讯地图代理和地理编码缓存等接口。

## 技术栈

- Java 8
- Spring Boot 2.7.18
- MyBatis
- SQLite
- HikariCP
- Tencent Map WebService API
- Maven Wrapper

## 环境要求

- JDK 8 或兼容版本
- Maven Wrapper 已包含在项目中，可直接使用 `mvnw.cmd`
- SQLite 数据库文件默认路径：

```text
D:/PICC_SYSTEM/EAGLE_SYSTEM/db.sqlite3
```

数据库路径配置在：

```text
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:sqlite:D:/PICC_SYSTEM/EAGLE_SYSTEM/db.sqlite3
```

## 本地启动

Windows：

```bash
mvnw.cmd spring-boot:run
```

或先编译：

```bash
mvnw.cmd -DskipTests compile
```

默认端口：

```text
http://localhost:8080
```

## 配置说明

主要配置文件：

```text
src/main/resources/application.properties
```

关键配置：

```properties
server.port=8080
tencent.map.key=你的腾讯地图WebServiceKey
tencent.map.js-key=你的腾讯地图JSKey
tencent.map.api-domain=https://apis.map.qq.com/
spring.datasource.url=jdbc:sqlite:D:/PICC_SYSTEM/EAGLE_SYSTEM/db.sqlite3
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
```

生产环境建议把地图 Key 和数据库路径改成环境变量或外部配置，不要直接写死在仓库中。

## 目录说明

```text
src/main/java/com/example/demo/
  controller/       HTTP 接口
  mapper/           MyBatis Mapper 接口
  pojo/             数据对象
  service/          业务接口
  service/impl/     业务实现
  util/             地图解析、限流、异步任务工具
  exception/        全局异常处理

src/main/resources/
  mapper/           MyBatis XML
  application.properties
```

## 主要接口

### 热力图

```text
GET /api/hotmap?date=YYYY-MM-DD
GET /api/hotmap/progress?date=YYYY-MM-DD
GET /api/hotmap/clearCache?date=YYYY-MM-DD
GET /api/statsCardsData?date=YYYY-MM-DD
```

说明：

- `/api/hotmap` 先返回已有经纬度热力点。
- 如果存在只有中文地址、没有经纬度的数据，会启动后台异步解析。
- `/api/hotmap/progress` 返回解析进度、成功数、失败数、缓存数量等信息。
- `/api/hotmap/clearCache` 用于清理指定日期热力图缓存。

### 人员位置

```text
GET /api/locations
GET /api/locations/latest
GET /api/locations/latest/progress
GET /api/locations/user/{usercode}
GET /api/locations/groups
```

### 工作量统计

```text
GET /api/cur_gzl/list
GET /api/cur_gzl_bm/list
GET /api/cur_gzl_group/list
GET /api/cur_gzl_rs/list
```

### 腾讯地图代理

```text
GET /api/map/geocoder
GET /api/map/district/search
GET /api/map/district/getchildren
GET /api/map/jsapi
```

## 热力图异步地理编码逻辑

当前热力图满足以下业务要求：

> 先返回已有经纬度，后台再慢慢补缺失地址，缓存表只辅助异步解析。

实现流程：

1. 前端请求 `/api/hotmap?date=YYYY-MM-DD`。
2. 后端从案件表中查询已有经纬度的数据，立即返回热力点。
3. 后端检查同一天是否还有缺失经纬度但有中文地址的数据。
4. 如果存在缺失数据，并且当前日期没有正在运行的解析任务，则提交后台异步任务。
5. 异步任务调用腾讯地图正向地理编码接口，将中文地址解析成经纬度。
6. 解析成功的数据写入缓存表。
7. 前端通过 `/api/hotmap/progress` 轮询进度。
8. 解析完成后，前端重新请求 `/api/hotmap` 获取补齐后的热力点。

相关文件：

```text
controller/HotmapController.java
service/impl/HotmapServiceImpl.java
service/impl/AsyncGeocodeServiceImpl.java
service/impl/HeatDataCacheServiceImpl.java
util/GeocodeScheduler.java
util/LocationAddressConverter.java
mapper/PrplCheckTaskMapper.java
resources/mapper/PrplCheckTaskMapper.xml
```

## 地理编码调度和限流

`GeocodeScheduler` 负责后台任务调度和请求节流：

- 使用线程池执行异步地理编码任务。
- 同一个任务 key 正在运行时，不重复提交。
- 使用简化令牌桶控制请求速率，避免超过地图 API 每秒限制。
- `acquire()` 会在令牌不足时等待，并在重新获得令牌后继续请求。

任务 key 示例：

```text
hotmap:2026-05-28
location:2026-05-28
```

## 缓存说明

项目里缓存分两类：

1. 内存状态缓存：记录某个日期是否正在解析、解析进度、成功数、失败数。
2. 数据库缓存表：保存已经解析过的中文地址和经纬度，避免重复调用地图 API。

缓存表不是主数据来源，只用于辅助异步解析和复用历史解析结果。

## 验证命令

编译：

```bash
mvnw.cmd -DskipTests compile
```

接口冒烟：

```bash
curl "http://localhost:8080/api/hotmap?date=2026-05-28"
curl "http://localhost:8080/api/hotmap/progress?date=2026-05-28"
```

## 常见问题

### 1. 启动时报 SQLite 文件不存在

确认以下路径存在：

```text
D:/PICC_SYSTEM/EAGLE_SYSTEM/db.sqlite3
```

如果路径不一致，修改 `application.properties` 中的 `spring.datasource.url`。

### 2. 地图解析失败

检查：

- 腾讯地图 Key 是否有效。
- Key 是否开通 WebService API。
- 当前机器网络是否能访问 `https://apis.map.qq.com/`。
- 是否触发腾讯地图接口频率限制。

### 3. 前端请求 404 或 500

确认后端服务已启动在 `8080`，前端 `.env.development` 中代理地址为：

```env
VITE_API_PROXY_URL=http://localhost:8080/
```

### 4. 不要提交的文件

以下文件属于本地运行产物，不应提交：

```text
target/
backend-dev.log
```
