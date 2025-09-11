# Redis篇

### Redis通用命令

通用指令是部分数据类型的，都可以使用的指令，常见的有：

- KEYS：查看符合模板的所有key
- DEL：删除一个指定的key
- EXISTS：判断key是否存在
- EXPIRE：给一个key设置有效期，有效期到期时该key会被自动删除
- TTL：查看一个KEY的剩余有效期

通过help [command] 可以查看一个命令的具体用法，例如：

```sh
# 查看keys命令的帮助信息：
127.0.0.1:6379> help keys

KEYS pattern
summary: Find all keys matching the given pattern
since: 1.0.0
group: generic
```

### 5种数据类型

#### 1. String（字符串）

这是最基本、最常用的类型。它是**二进制安全**的，意味着可以存储任何类型的数据，比如文本、JSON、数字、甚至图片的二进制数据。一个键最大能存储 512MB。

- **内部实现**：基于 **SDS (Simple Dynamic String)**，这是一种修改过的字符串，获取长度复杂度为 O(1)，并且可以高效地执行追加操作。
- **常用命令**:
  - `SET key value`： 设置值。
  - `GET key`： 获取值。
  - `INCR key` / `DECR key`： 将键存储的整数值增加/减少 1。（原子操作，非常适合计数器场景）
  - `INCRBY key increment`： 增加指定的整数值。
  - `APPEND key value`： 追加字符串。
  - `MSET` / `MGET`： 批量设置/获取多个键值对。
- **应用场景**：
  - 缓存 HTML 片段、用户信息（JSON序列化后）。
  - 计数器（文章阅读量、用户点赞数）。
  - 分布式锁（利用 `SET key value NX PX milliseconds` 命令）。

#### 2. Hash（哈希）

类似于 Java 中的 `Map`。它是一个键值对集合，非常适合用来存储**对象**。

- **内部实现**：底层有两种编码方式：`ziplist`（元素较少时，节省内存）和 `hashtable`（元素多时，查询效率高）。
- **常用命令**:
  - `HSET key field value`： 设置哈希表中字段的值。
  - `HGET key field`： 获取哈希表中字段的值。
  - `HGETALL key`： 获取哈希表中所有的字段和值。
  - `HDEL key field`： 删除哈希表中的一个或多个字段。
  - `HMSET key field1 value1 field2 value2 ...`： 批量设置多个字段值（新版本中 `HSET` 也支持）。
  - `HINCRBY key field increment`： 为哈希表中的整数字段值增加指定数值。
- **应用场景**：
  - 存储用户信息（`user:1000` 作为 key，`name`, `age`, `email` 作为 field）。
  - 存储商品信息、配置信息等。

#### 3. List（列表）

一个简单的**字符串列表**，按插入顺序排序。你可以从列表的头部（左边）或尾部（右边）添加元素。一个列表最多可以包含 2³² - 1 个元素。

- **内部实现**：底层是 **quicklist**，它是多个 **ziplist** 通过双向链表连接起来的结构，在内存效率和操作性能之间取得了很好的平衡。
- **常用命令**:
  - `LPUSH key value1 value2 ...`： 将一个或多个值插入到列表头部（左边）。
  - `RPUSH key value1 value2 ...`： 将一个或多个值插入到列表尾部（右边）。
  - `LPOP key`： 移除并获取列表的第一个元素（左边）。
  - `RPOP key`： 移除并获取列表的最后一个元素（右边）。
  - `LRANGE key start stop`： 获取列表指定范围内的元素（例如 `LRANGE mylist 0 -1` 获取所有元素）。
  - `BLPOP key timeout`： 阻塞式地弹出列表的第一个元素，如果列表为空则等待。
- **应用场景**：
  - 消息队列（`LPUSH` + `BRPOP`）。
  - 最新消息排行（比如朋友圈时间线，`LPUSH` 加入新内容，`LTRIM` 截取最近 N 条）。
  - 记录用户的操作日志。

#### 4. Set（集合）

Redis 的 Set 是**无序的、元素不重复**的字符串集合。它实现了并集、交集、差集等操作，效率极高。

- **内部实现**：底层有两种编码：`intset`（当集合中所有元素都是整数时）和 `hashtable`（value 为 NULL 的哈希表）。
- **常用命令**:
  - `SADD key member1 member2 ...`： 向集合添加一个或多个成员。
  - `SREM key member`： 移除集合中一个或多个成员。
  - `SMEMBERS key`： 返回集合中的所有成员（小心使用，数据量大时可能阻塞服务）。
  - `SISMEMBER key member`： 判断 member 元素是否是集合的成员。
  - `SINTER key1 key2`： 返回多个集合的**交集**。
  - `SUNION key1 key2`： 返回多个集合的**并集**。
  - `SDIFF key1 key2`： 返回第一个集合与其他集合之间的**差集**。
- **应用场景**：
  - 共同关注、共同好友（利用 `SINTER`）。
  - 标签系统（给用户/文章打标签，一个用户的所有标签就是一个集合）。
  - 随机抽奖（`SRANDMEMBER` 或 `SPOP`）。

#### 5. ZSet / Sorted Set（有序集合）

这是 Redis 中最有特色的数据结构。它和 Set 一样，也是字符串且元素不重复，但**每个元素都会关联一个 `double` 类型的分数（score）**。Redis 正是通过这个分数来对集合中的成员进行**从小到大排序**的。

- **内部实现**：非常精妙，使用了 **跳跃表（skiplist）** 和 **哈希表（hashtable）** 的组合。跳跃表支持高效的范围查询，哈希表支持高效的单点查询。
- **常用命令**:
  - `ZADD key score1 member1 score2 member2 ...`： 向有序集合添加一个或多个成员，或者更新已存在成员的分数。
  - `ZRANGE key start stop [WITHSCORES]`： 通过索引区间返回有序集合指定区间内的成员（低到高）。
  - `ZREVRANGE key start stop [WITHSCORES]`： 返回有序集合指定区间内的成员，通过索引，分数从高到低。
  - `ZRANGEBYSCORE key min max`： 通过分数返回有序集合指定区间内的成员。
  - `ZRANK key member`： 返回有序集合中指定成员的排名（从 0 开始，低到高）。
  - `ZREVRANK key member`： 返回有序集合中指定成员的排名（从 0 开始，高到低）。
- **应用场景**：
  - **排行榜**：这是最经典的场景，如游戏积分排行榜、热搜榜。
  - 带权重的消息队列（score 代表优先级）。
  - 范围查找（例如处理过期事件，将时间戳作为 score）。

#### 总结

| 数据结构              | 特性                         | 核心命令示例                         | 典型应用场景           |
| :-------------------- | :--------------------------- | :----------------------------------- | :--------------------- |
| **String**      | 二进制安全，可存任何数据     | `SET`, `GET`, `INCR`           | 缓存、计数器、分布式锁 |
| **Hash**        | 字段-值映射，适合存储对象    | `HSET`, `HGET`, `HGETALL`      | 用户信息、商品信息     |
| **List**        | 有序、可重复，双向链表       | `LPUSH`, `RPOP`, `LRANGE`      | 消息队列、最新列表     |
| **Set**         | 无序、唯一，支持集合运算     | `SADD`, `SINTER`, `SMEMBERS`   | 标签、共同好友、抽奖   |
| **ZSet**        | 唯一、有序，按分数排序       | `ZADD`, `ZRANGE`, `ZREVRANK`   | 排行榜、带权重队列     |
| **HyperLogLog** | 高效基数估算，不存储元素本身 | `PFADD`, `PFCOUNT`               | 独立访客(UV)统计       |
| **Geo**         | 地理位置信息（基于ZSet）     | `GEOADD`, `GEORADIUS`            | 附近的人、地理位置计算 |
| **Bitmaps**     | 位操作（基于String）         | `SETBIT`, `GETBIT`, `BITCOUNT` | 用户签到、活跃度统计   |

选择合适的数据结构可以极大地提升程序的效率和简化代码逻辑，这也是深入使用 Redis 的关键。

### Redis在JAVA中的应用

#### SpringDataRedis客户端

SpringData是Spring中数据操作的模块，包含对各种数据库的集成，其中对Redis的集成模块就叫做SpringDataRedis，官网地址：https://spring.io/projects/spring-data-redis

- 提供了对不同Redis客户端的整合（Lettuce和Jedis）
- 提供了RedisTemplate统一API来操作Redis
- 支持Redis的发布订阅模型
- 支持Redis哨兵和Redis集群
- 支持基于Lettuce的响应式编程
- 支持基于JDK、JSON、字符串、Spring对象的数据序列化及反序列化
- 支持基于Redis的JDKCollection实现

SpringDataRedis中提供了RedisTemplate工具类，其中封装了各种对Redis的操作。并且将不同数据类型的操作API封装到了不同的类型中：

![](assets/UFlNIV0.png)

```xml
    <!--redis依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```

#### 配置Redis

```yaml
spring:
  redis:
    host: 192.168.150.101
    port: 6379
    password: 123321
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
        max-wait: 100ms
```

#### 注入RedisTemplate

因为有了SpringBoot的自动装配，我们可以拿来就用：

```java
@SpringBootTest
class RedisStringTests {
    @Autowired
    private RedisTemplate redisTemplate;
}
```

### RedisTemplate 常用操作方法总结

| API                             | 返回值类型          | 说明                                                          |
| :------------------------------ | :------------------ | :------------------------------------------------------------ |
| `redisTemplate.opsForValue()` | `ValueOperations` | 用于操作**String** 类型的数据                           |
| `redisTemplate.opsForHash()`  | `HashOperations`  | 用于操作**Hash** 类型的数据                             |
| `redisTemplate.opsForList()`  | `ListOperations`  | 用于操作**List** 类型的数据                             |
| `redisTemplate.opsForSet()`   | `SetOperations`   | 用于操作**Set** 类型的数据                              |
| `redisTemplate.opsForZSet()`  | `ZSetOperations`  | 用于操作**Sorted Set**（有序集合）类型的数据            |
| `redisTemplate`               | —                  | 提供通用的 Redis 命令，如 `delete`、`expire`、`keys` 等 |

---

补充说明：

- 每种 `opsForXXX()` 方法返回一个对应的操作接口，通过该接口可以执行对应数据结构的增删改查等操作。
- `redisTemplate` 本身也提供了一些通用的方法，如删除键、设置过期时间、判断键是否存在等。

#### 1. String 类型操作 (ValueOperations)

```java
// 设置键值对
redisTemplate.opsForValue().set("key", "value");

// 设置带过期时间的键值对（30秒后过期）
redisTemplate.opsForValue().set("key", "value", 30, TimeUnit.SECONDS);

// 获取值
String value = (String) redisTemplate.opsForValue().get("key");

// 如果键不存在则设置
Boolean result = redisTemplate.opsForValue().setIfAbsent("key", "value");

// 递增
Long incremented = redisTemplate.opsForValue().increment("counter", 1);

// 递减
Long decremented = redisTemplate.opsForValue().decrement("counter", 1);
```

#### 2. Hash 类型操作 (HashOperations)

```java
// 设置哈希字段值
redisTemplate.opsForHash().put("user:1001", "name", "张三");
redisTemplate.opsForHash().put("user:1001", "age", "25");

// 获取哈希字段值
String name = (String) redisTemplate.opsForHash().get("user:1001", "name");

// 获取所有字段值
Map<Object, Object> userData = redisTemplate.opsForHash().entries("user:1001");

// 删除一个或多个哈希字段
redisTemplate.opsForHash().delete("user:1001", "age");

// 检查字段是否存在
Boolean hasField = redisTemplate.opsForHash().hasKey("user:1001", "name");

// 获取所有字段
Set<Object> fields = redisTemplate.opsForHash().keys("user:1001");

// 获取所有值
List<Object> values = redisTemplate.opsForHash().values("user:1001");
```

#### 3. List 类型操作 (ListOperations)

```java
// 从左端插入元素
redisTemplate.opsForList().leftPush("list", "item1");
redisTemplate.opsForList().leftPushAll("list", "item2", "item3");

// 从右端插入元素
redisTemplate.opsForList().rightPush("list", "item4");

// 获取列表范围
List<Object> items = redisTemplate.opsForList().range("list", 0, -1);

// 获取列表长度
Long size = redisTemplate.opsForList().size("list");

// 从左端弹出元素
Object leftItem = redisTemplate.opsForList().leftPop("list");

// 从右端弹出元素
Object rightItem = redisTemplate.opsForList().rightPop("list");

// 根据索引获取元素
Object item = redisTemplate.opsForList().index("list", 1);

// 根据索引设置元素
redisTemplate.opsForList().set("list", 1, "newItem");
```

#### 4. Set 类型操作 (SetOperations)

```java
// 添加元素
redisTemplate.opsForSet().add("set", "member1", "member2", "member3");

// 获取所有成员
Set<Object> members = redisTemplate.opsForSet().members("set");

// 判断是否为成员
Boolean isMember = redisTemplate.opsForSet().isMember("set", "member1");

// 移除成员
Long removed = redisTemplate.opsForSet().remove("set", "member1");

// 获取集合大小
Long size = redisTemplate.opsForSet().size("set");

// 集合运算：交集
Set<Object> intersect = redisTemplate.opsForSet().intersect("set1", "set2");

// 集合运算：并集
Set<Object> union = redisTemplate.opsForSet().union("set1", "set2");

// 集合运算：差集
Set<Object> difference = redisTemplate.opsForSet().difference("set1", "set2");
```

#### 5. Sorted Set 类型操作 (ZSetOperations)

```java
// 添加元素（带分数）
redisTemplate.opsForZSet().add("zset", "member1", 10.0);
redisTemplate.opsForZSet().add("zset", "member2", 5.0);

// 获取元素分数
Double score = redisTemplate.opsForZSet().score("zset", "member1");

// 增加元素分数
Double newScore = redisTemplate.opsForZSet().incrementScore("zset", "member1", 2.0);

// 获取排名（从小到大）
Long rank = redisTemplate.opsForZSet().rank("zset", "member1");

// 获取排名（从大到小）
Long reverseRank = redisTemplate.opsForZSet().reverseRank("zset", "member1");

// 获取范围内的元素（按分数从小到大）
Set<Object> range = redisTemplate.opsForZSet().range("zset", 0, -1);

// 获取范围内的元素（按分数从大到小）
Set<Object> reverseRange = redisTemplate.opsForZSet().reverseRange("zset", 0, -1);

// 获取范围内的元素（带分数）
Set<ZSetOperations.TypedTuple<Object>> rangeWithScores = redisTemplate.opsForZSet().rangeWithScores("zset", 0, -1);

// 根据分数范围获取元素
Set<Object> rangeByScore = redisTemplate.opsForZSet().rangeByScore("zset", 5.0, 10.0);

// 移除元素
Long removed = redisTemplate.opsForZSet().remove("zset", "member1");
```

#### 6. 通用操作 (RedisTemplate)

```java
// 删除键
redisTemplate.delete("key");

// 判断键是否存在
Boolean exists = redisTemplate.hasKey("key");

// 设置过期时间
redisTemplate.expire("key", 60, TimeUnit.SECONDS);

// 获取过期时间
Long expire = redisTemplate.getExpire("key");

// 移除过期时间，使键永久有效
redisTemplate.persist("key");

// 获取匹配的键
Set<String> keys = redisTemplate.keys("user:*");

// 获取Redis连接工厂
RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
```