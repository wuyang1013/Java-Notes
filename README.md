# JAVA随笔

## 基础篇

正在完善中~~~

### 内存

- #### ‌堆（Heap）：new关键字

  所有线程共享的内存区域，主要用于存放对象实例和数组。堆是动态分配的，大小不固定，只受物理内存大小限制。
- #### ‌栈（Stack）‌：执行方法

  线程私有的内存区域，每个方法执行都会创建一个新的栈帧。栈帧用于存储局部变量、操作数栈、动态链接、方法出口等信息。栈的大小在虚拟机启动时就已经确定。

#### 内存图

成员变量使用过程

![内存图1](assets/内存图1.png)

成员方法调用过程

![内存图2](assets/内存图2.png)

#### 多个对象内存图

成员变量使用过程

![多对象内存图1](assets/多对象内存图1.png)

成员方法调用过程

![多对象内存图2](assets/多对象内存图2.png)

> *以上4张图片来源于黑马*

### String类

#### 字符串长度与空值检查

| 方法                  | 描述                           | 示例                      |
| :-------------------- | :----------------------------- | :------------------------ |
| `int length()`      | 返回字符串长度                 | `str.length()` → 5     |
| `boolean isEmpty()` | 检查字符串是否为空             | `str.isEmpty()` → true |
| `boolean isBlank()` | 检查字符串是否为空白(Java 11+) | `str.isBlank()` → true |

#### 字符串查找

| 方法                                  | 描述                               | 示例                                 |
| :------------------------------------ | :--------------------------------- | :----------------------------------- |
| `char charAt(int index)`            | 返回指定索引处的字符               | `"hello".charAt(1)` → 'e'         |
| `int indexOf(String str)`           | 返回指定子字符串第一次出现的索引   | `"hello".indexOf("l")` → 2        |
| `int lastIndexOf(String str)`       | 返回指定子字符串最后一次出现的索引 | `"hello".lastIndexOf("l")` → 3    |
| `boolean contains(CharSequence s)`  | 检查字符串是否包含指定字符序列     | `"hello".contains("ell")` → true  |
| `boolean startsWith(String prefix)` | 检查字符串是否以指定前缀开头       | `"hello".startsWith("he")` → true |
| `boolean endsWith(String suffix)`   | 检查字符串是否以指定后缀结尾       | `"hello".endsWith("lo")` → true   |

#### 字符串截取

| 方法                                               | 描述                                 | 示例                                 |
| :------------------------------------------------- | :----------------------------------- | :----------------------------------- |
| `String substring(int beginIndex)`               | 返回从指定索引开始到末尾的子字符串   | `"hello".substring(2)` → "llo"    |
| `String substring(int beginIndex, int endIndex)` | 返回指定开始和结束索引之间的子字符串 | `"hello".substring(1, 4)` → "ell" |

#### 字符串比较

| 方法                                               | 描述                     | 示例                                          |
| :------------------------------------------------- | :----------------------- | :-------------------------------------------- |
| `boolean equals(Object anObject)`                | 比较字符串内容是否相等   | `"hello".equals("hello")` → true           |
| `boolean equalsIgnoreCase(String anotherString)` | 忽略大小写比较字符串内容 | `"Hello".equalsIgnoreCase("hello")` → true |
| `int compareTo(String anotherString)`            | 按字典顺序比较两个字符串 | `"abc".compareTo("abd")` → -1              |
| `int compareToIgnoreCase(String str)`            | 忽略大小写的字典顺序比较 | `"A".compareToIgnoreCase("a")` → 0         |

#### 字符串转换

| 方法                             | 描述                             | 示例                                      |
| :------------------------------- | :------------------------------- | :---------------------------------------- |
| `String toLowerCase()`         | 将字符串转换为小写               | `"HELLO".toLowerCase()` → "hello"      |
| `String toUpperCase()`         | 将字符串转换为大写               | `"hello".toUpperCase()` → "HELLO"      |
| `String trim()`                | 去除字符串首尾空白字符           | `" hello ".trim()` → "hello"           |
| `String strip()`               | 去除字符串首尾空白字符(Java 11+) | `" hello ".strip()` → "hello"          |
| `String[] split(String regex)` | 根据正则表达式分割字符串         | `"a,b,c".split(",")` → ["a", "b", "c"] |

#### 字符串替换

| 方法                                                              | 描述                           | 示例                                          |
| :---------------------------------------------------------------- | :----------------------------- | :-------------------------------------------- |
| `String replace(char oldChar, char newChar)`                    | 替换所有出现的指定字符         | `"hello".replace('l', 'L')` → "heLLo"      |
| `String replace(CharSequence target, CharSequence replacement)` | 替换所有出现的指定字符序列     | `"hello".replace("ll", "LL")` → "heLLo"    |
| `String replaceAll(String regex, String replacement)`           | 使用正则表达式替换所有匹配项   | `"a1b2".replaceAll("\\d", "-")` → "a-b-"   |
| `String replaceFirst(String regex, String replacement)`         | 使用正则表达式替换第一个匹配项 | `"a1b2".replaceFirst("\\d", "-")` → "a-b2" |

#### 正则表达式

| 方法                              | 描述                               | 示例                              |
| :-------------------------------- | :--------------------------------- | :-------------------------------- |
| `boolean matches(String regex)` | 检查字符串是否匹配给定的正则表达式 | `"123".matches("\\d+")` → true |

#### 其他实用方法

| 方法                                                                     | 描述                         | 示例                                                                        |
| :----------------------------------------------------------------------- | :--------------------------- | :-------------------------------------------------------------------------- |
| `static String join(CharSequence delimiter, CharSequence... elements)` | 使用指定分隔符连接多个字符串 | `String.join("-", "a", "b", "c")` → "a-b-c"                              |
| `String repeat(int count)`                                             | 重复字符串指定次数(Java 11+) | `"ab".repeat(3)` → "ababab"                                              |
| `String format(String format, Object... args)`                         | 格式化字符串                 | `String.format("Name: %s, Age: %d", "John", 25)` → "Name: John, Age: 25" |
| `static String valueOf(Object obj)`                                    | 将对象转换为字符串表示形式   | `String.valueOf(123)` → "123"                                            |

### String、StringBuffer 和 StringBuilder 的区别

| 特性               | String               | StringBuilder            | StringBuffer                      |
| :----------------- | :------------------- | :----------------------- | :-------------------------------- |
| **可变性**   | 不可变               | 可变                     | 可变                              |
| **线程安全** | 是（由于不可变性）   | 否                       | 是（通过 `synchronized`关键字） |
| **性能**     | 较低（频繁操作时）   | 高                       | 中等                              |
| **使用场景** | 字符串常量或少量操作 | 单线程环境下的字符串操作 | 多线程环境下的字符串操作          |
| **存储位置** | 字符串常量池         | 堆内存                   | 堆内存                            |

### ArrayList

##### 构造方法

| 方法名             | 说明                 |
| ------------------ | -------------------- |
| public ArrayList() | 创建一个空的集合对象 |

##### 成员方法

| 方法名                              | 说明                                   |
| ----------------------------------- | -------------------------------------- |
| public boolean add(要添加的元素)    | 将指定的元素追加到此集合的末尾         |
| public boolean remove(要删除的元素) | 删除指定元素,返回值表示是否删除成功    |
| public E remove(int index)          | 删除指定索引处的元素，返回被删除的元素 |
| public E set(int index,E element)   | 修改指定索引处的元素，返回被修改的元素 |
| public E get(int index)             | 返回指定索引处的元素                   |
| public int size()                   | 返回集合中的元素的个数                 |

### Map集合

#### Map集合概述和特点

```Java
interface Map<K,V>  K：键的类型；V：值的类型
```

- Map集合的特点

  - 双列集合,一个键对应一个值
  - 键不可以重复,值可以重复
- 方法介绍

| 方法名                              | 说明                                 |
| ----------------------------------- | ------------------------------------ |
| V put(K key,V value)                | 添加元素                             |
| V remove(Object key)                | 根据键删除键值对元素                 |
| void clear()                        | 移除所有的键值对元素                 |
| boolean containsKey(Object key)     | 判断集合是否包含指定的键             |
| boolean containsValue(Object value) | 判断集合是否包含指定的值             |
| boolean isEmpty()                   | 判断集合是否为空                     |
| int size()                          | 集合的长度，也就是集合中键值对的个数 |

#### Map集合的获取功能【应用】

- 方法介绍

| 方法名                         | 说明                     |
| ------------------------------ | ------------------------ |
| V get(Object key)              | 根据键获取值             |
| Set `<K>` keySet()           | 获取所有键的集合         |
| Collection `<V>` values()    | 获取所有值的集合         |
| Set<Map.Entry<K,V>> entrySet() | 获取所有键值对对象的集合 |

- 示例代码

```Java
        //V get(Object key):根据键获取值
        System.out.println(map.get("张无忌"));
        System.out.println(map.get("张三丰"));

        //Set<K> keySet():获取所有键的集合
        Set<String> keySet = map.keySet();
        for(String key : keySet) {
            System.out.println(key);
        }

        //Collection<V> values():获取所有值的集合
        Collection<String values = map.values();
        for(String value : values) {
            System.out.println(value);
        }
```

#### Map集合的遍历

方式一：

```java
        //获取所有键的集合。用keySet()方法实现
        Set<String keySet = map.keySet();
        //遍历键的集合，获取到每一个键。用增强for实现
        for (String key : keySet) {
            //根据键去找值。用get(Object key)方法实现
            String value = map.get(key);
            System.out.println(key + "," + value);
        }
```

方式二：

```java
        //获取所有键值对对象的集合
        Set<Map.Entry<String, String entrySet = map.entrySet();
        //遍历键值对对象的集合，得到每一个键值对对象
        for (Map.Entry<String, String me : entrySet) {
            //根据键值对对象获取键和值
            String key = me.getKey();
            String value = me.getValue();
            System.out.println(key + "," + value);
        }
```

### Collections类

#### Collections常用功能

- `java.utils.Collections`是集合工具类，用来对集合进行操作。

常用方法如下：

- `public static void shuffle(List<?> list)`:打乱集合顺序。
- `public static <T> void sort(List<T> list)`:将集合中元素按照默认规则排序（从小到大）。
- `public static <T> void sort(List<T> list，Comparator<? super T> )`:将集合中元素按照指定规则排序。

Stream流

### 重载

- 概念：方法重载指同一个类中定义的多个方法之间的关系，满足下列条件的多个方法相互构成重载
  - 多个方法在同一个类中
  - 多个方法具有相同的方法名
  - 多个方法的参数不相同，类型不同或者数量不同

    ```java
    public class A {
        public static void fn(int a) {
            // 方法体
        }
        public static int fn(double a) {
            // 方法体
        }
        public static int fn(int a, int b){
            // 方法体
        }
    }
    ```

### 数据类型

##### 基本数据类型

- 整型：byte, short, int, long
- 浮点型：float, double
- 字符型：char
- 布尔型：boolean

##### 引用数据类型

引用数据类型是指那些不直接存储数据值，而是存储对象内存地址的数据类型。引用数据类型变量实际上存储的是对内存中对象的引用（指针）。

引用数据类型包括：

- 类（Class）类型（如String、自定义类）
- 接口（Interface）类型
- 数组类型

##### 主要区别

| 特性     | 基本数据类型      | 引用数据类型                                   |
| :------- | :---------------- | :--------------------------------------------- |
| 存储内容 | 实际值            | 对象引用（内存地址）                           |
| 内存位置 | 栈内存            | 堆内存（对象），栈内存（引用）                 |
| 默认值   | 有固定默认值      | null                                           |
| 比较方式 | 使用 `==`比较值 | 使用 `==`比较引用，使用 `equals()`比较内容 |
| 性能     | 访问速度快        | 需要间接访问，稍慢                             |

### 关键字

#### this和super

```java
this.成员变量            --    本类
super.成员变量           --    父类
```

#### static

1. 当 `static` 修饰成员变量或者成员方法时，该变量称为**静态变量**，该方法称为**静态方法**。该类的每个对象都**共享**同一个类的静态变量和静态方法。任何对象都可以更改该静态变量的值或者访问静态方法。但是不推荐这种方式去访问。因为静态变量或者静态方法直接通过类名访问即可，完全没有必要用对象去访问。
2. 无static修饰的成员变量或者成员方法，称为**实例变量，实例方法**，实例变量和实例方法必须创建类的对象，然后通过对象来访问。
3. static修饰的成员属于类，会存储在静态区，是随着类的加载而加载的，且只加载一次，所以只有一份，节省内存。存储于一块固定的内存区域（静态区），所以，可以直接被类名调用。它优先于对象存在，所以，可以被所有对象共享。
4. 无static修饰的成员，是属于对象，对象有多少个，他们就会出现多少份。所以必须由对象调用。

#### final

Java提供了 `final` 关键字，表示修饰的内容不可变。

- **final**：  不可改变，最终的含义。可以用于修饰类、方法和变量。
  - 类：被修饰的类，不能被继承。
  - 方法：被修饰的方法，不能被重写。
  - 变量：被修饰的变量，有且仅能被赋值一次。

#### instanceof

转型的过程中，一不小心就会遇到这样的问题，请看如下代码：

```Java
public class Test {
    public static void main(String[] args) {
        // 向上转型    
        Animal a = new Cat();    
        a.eat();               // 调用的是 Cat 的 eat

        // 向下转型    
        Dog d = (Dog)a;    
        d.watchHouse();        // 调用的是 Dog 的 watchHouse 【运行报错】
    }  
}
```

这段代码可以通过编译，但是运行时，却报出了 `ClassCastException` ，类型转换异常！这是因为，明明创建了Cat类型对象，运行时，当然不能转换成Dog对象的。

为了避免ClassCastException的发生，Java提供了 `instanceof` 关键字，给引用变量做类型的校验，格式如下：

```Java
变量名 instanceof 数据类型 
如果变量属于该数据类型或者其子类类型，返回true。
如果变量不属于该数据类型或者其子类类型，返回false。
```

所以，转换前，我们最好先做一个判断，代码如下：

```Java
public class Test {
    public static void main(String[] args) {
        // 向上转型    
        Animal a = new Cat();    
        a.eat();               // 调用的是 Cat 的 eat

        // 向下转型    
        if (a instanceof Cat){
            Cat c = (Cat)a;        
            c.catchMouse();        // 调用的是 Cat 的 catchMouse
        } else if (a instanceof Dog){
            Dog d = (Dog)a;        
            d.watchHouse();       // 调用的是 Dog 的 watchHouse
        }
    }  
}
```

##### instanceof新特性

JDK14的时候提出了新特性，把判断和强转合并成了一行

```Java
//新特性
//先判断a是否为Dog类型，如果是，则强转成Dog类型，转换之后变量名为d
//如果不是，则不强转，结果直接是false
if(a instanceof Dog d){
    d.lookHome();
}else if(a instanceof Cat c){
    c.catchMouse();
}else{
    System.out.println("没有这个类型，无法转换");
}
```

#### extends

通过 `extends` 关键字，可以声明一个子类继承另外一个父类，定义格式如下：

```Java
class 父类 {
        ...
}

class 子类 extends 父类 {
        ...
}
```

多线程

## MySql篇

### 基本语法

#### 查询

- 查询多个字段

```SQL
select 字段1, 字段2, 字段3 from  表名;
```

- 查询所有字段（通配符）（不推荐使用，会导致索引失效）

```SQL
select *  from  表名;
```

- 设置别名

```SQL
select 字段1 [ as 别名1 ] , 字段2 [ as 别名2 ]  from  表名;
```

- 去除重复记录

```SQL
select distinct 字段列表 from  表名;
```

- 条件查询

```SQL
select  字段列表  from   表名   where   条件列表 ; -- 条件列表：意味着可以有多个条件
```

学习条件查询就是学习条件的构建方式，而在SQL语句当中构造条件的运算符分为两类：

- 比较运算符
- 逻辑运算符

常用的比较运算符如下:

| 比较运算符          | 功能                                     |
| ------------------- | ---------------------------------------- |
| >                   | 大于                                     |
| >=                  | 大于等于                                 |
| <                   | 小于                                     |
| <=                  | 小于等于                                 |
| =                   | 等于                                     |
| <> 或 !=            | 不等于                                   |
| between ... and ... | 在某个范围之内(含最小、最大值)           |
| in(...)             | 在in之后的列表中的值，多选一             |
| like 占位符         | 模糊匹配(_匹配单个字符, %匹配任意个字符) |
| is null             | 是null                                   |

常用的逻辑运算符如下:

| 逻辑运算符 | 功能                        |
| ---------- | --------------------------- |
| and 或 &&  | 并且 (多个条件同时成立)     |
| or 或\|\|  | 或者 (多个条件任意一个成立) |
| not 或 !   | 非 , 不是                   |

常用聚合函数：

| 函数  | 功能     |
| ----- | -------- |
| count | 统计数量 |
| max   | 最大值   |
| min   | 最小值   |
| avg   | 平均值   |
| sum   | 求和     |

> count ：按照列去统计有多少行数据。
>
> - 在根据指定的列统计的时候，如果这一列中有null的行，该行不会被统计在其中。
>
> sum ：计算指定列的数值和，如果不是数值类型，那么计算结果为0
>
> max ：计算指定列的最大值
>
> min ：计算指定列的最小值
>
> avg ：计算指定列的平均值

- 分组查询

分组： 按照某一列或者某几列，把相同的数据进行合并输出。

> 分组其实就是按列进行分类(指定列下相同的数据归为一类)，然后可以对分类完的数据进行合并计算。
>
> 分组查询通常会使用聚合函数进行计算。

- 排序查询

语法：

```SQL
select  字段列表  from  表名  [where 条件]  group by 分组字段名  [having 分组后过滤条件];
```

排序在日常开发中是非常常见的一个操作，有升序排序，也有降序排序。

语法：

```SQL
select  字段列表  
from   表名   
[where  条件列表] 
[group by  分组字段 ] 
order  by  字段1  排序方式1 , 字段2  排序方式2 … ;
```

- 排序方式：

  - ASC ：升序（默认值）
  - DESC：降序
- 分页查询语法：

```SQL
select  字段列表  from   表名  limit  起始索引, 查询记录数 ;    /* 索引起始为0 */
```

### 多表查询

多表查询可以分为：

1. 连接查询
   - 内连接：相当于查询A、B交集部分数据

![img](assets/1756617724314-1.png)

1. 外连接
   - 左外连接：查询左表所有数据(包括两张表交集部分数据)
   - 右外连接：查询右表所有数据(包括两张表交集部分数据)

```sql
SELECT
    [表别名1.]列名1, [表别名2.]列名2, ... -- 选择需要查询的列
FROM
    主表 [AS] 表别名1 -- 首先指定一个主表（或称为驱动表）
[连接类型] JOIN 连接表 [AS] 表别名2 -- 指定要连接的表和连接类型
    ON 连接条件 -- 这是最重要的部分，定义了两个表如何关联
[WHERE 过滤条件] -- 对连接后的结果集进行过滤
[GROUP BY 分组列] -- 分组
[HAVING 分组后的过滤条件] -- 对分组后的结果进行过滤
[ORDER BY 排序列]; -- 排序
```

## Spring篇

## Mybatis篇

### xml映射文件

```SQL
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="">
 
</mapper>
```

### 数据库与实体类不同命的解决方案

实体类属性名和数据库表查询返回的字段名一致，mybatis会自动封装。

如果实体类属性名和数据库表查询返回的字段名不一致，不能自动封装。

 解决方案：

1. 起别名
2. 结果映射
3. 开启驼峰命名

解决方案：

#### 方法一：使用 ResultMap

在 Mapper XML 文件中定义 **resultMap** 进行显式映射：

```xml
<!-- 定义 resultMap -->
<resultMap id="userResultMap" type="User">
  <id property="userId" column="user_id" />
  <result property="userName" column="user_name" />
  <result property="createTime" column="create_time" />
</resultMap>

<!-- 使用 resultMap -->
<select id="selectUser" resultMap="userResultMap">
  SELECT * FROM user
</select>
```

#### 方法二：使用 SQL 别名

在 SQL 查询中为列名设置别名，使其与实体类属性名匹配：

```xml
<select id="selectUser" resultType="User">
  SELECT
    user_id AS userId,
    user_name AS userName,
    create_time AS createTime
  FROM user
</select>
```

#### 方法三：配置驼峰命名转换

在 MyBatis 配置文件中启用驼峰命名自动映射：

```java
在application.properties中添加：
mybatis.configuration.map-underscore-to-camel-case=true
```

```xml
<!-- mybatis-config.xml -->
<configuration>
  <settings>
    <!-- 启用驼峰命名自动映射 -->
    <setting name="mapUnderscoreToCamelCase" value="true" />
  </settings>
</configuration>
```

启用后，MyBatis 会自动将下划线命名的数据库列名（如 user_name）映射到驼峰命名的属性名（如 userName）。

### 动态SQL

#### < if > 元素

用于条件判断，根据表达式的值决定是否包含其中的SQL片段。

基本语法

```xml
<if test="条件表达式">
  <!-- SQL片段 -->
</if>
```

示例

```xml
<!-- 根据条件动态添加WHERE子句 -->
<select id="findUsers" resultType="User">
  SELECT * FROM user
  WHERE 1=1  
<!--  
	1=1
	这是一个技巧，目的是为了简化后续 AND 条件的拼接。
	因为所有 AND 子句都附加在 WHERE 1=1 后面，避免了判断是否是第一个条件来决定是否加 AND。
	虽然有些开发者认为它“不优雅”，但在 MyBatis 动态 SQL 中是广泛接受的做法。
-->
  <if test="username != null">
    AND username = #{username}
  </if>
  <if test="email != null">
    AND email = #{email}
  </if>
</select>
```

**提示：**test属性中使用的是OGNL表达式，可以判断null值、空字符串、布尔条件等。

#### < choose >, < when >, < otherwise > 元素

用于实现多条件选择，类似于Java中的switch-case语句。

基本语法

```xml
<choose>
  <when test="条件1">
    <!-- SQL片段1 -->
  </when>
  <when test="条件2">
    <!-- SQL片段2 -->
  </when>
  <otherwise>
    <!-- 默认SQL片段 -->
  </otherwise>
</choose>
```

示例

```xml
<!-- 根据不同的条件执行不同的查询 -->
<select id="findUsers" resultType="User">
  SELECT * FROM user
  WHERE
  <choose>
    <when test="username != null">
      username = #{username}
    </when>
    <when test="email != null">
      email = #{email}
    </when>
    <otherwise>
      status = 'ACTIVE'
    </otherwise>
  </choose>
</select>
```

**注意：**< choose >元素会按顺序判断每个< when >的条件，一旦有条件为true，就会使用对应的SQL片段，并且不再继续判断后续条件。如果所有< when >的条件都不满足，则使用< otherwise >中的内容。

T

#### < trim > 元素

用于自定义SQL片段的修剪规则，可以添加前缀、后缀，或去除指定的前缀和后缀。

基本语法

```xml
<trim prefix="前缀" prefixOverrides="需要去除的前缀" suffix="后缀" suffixOverrides="需要去除的后缀">
  <!-- SQL片段 -->
</trim>
```

示例

```xml
<!-- 自定义WHERE子句 -->
<select id="findUsers" resultType="User">
  SELECT * FROM user
  <trim prefix="WHERE" prefixOverrides="AND | OR">
    <if test="username != null">
      AND username = #{username}
    </if>
    <if test="email != null">
      AND email = #{email}
    </if>
  </trim>
</select>
```

**提示：**`<trim>`元素非常灵活，可以用来替代 `<where>`和 `<set>`元素，实现更复杂的修剪逻辑。

W

#### < where > 元素

用于生成WHERE子句，能够智能地处理条件前缀（如AND、OR）和空条件。

基本语法

```xml
<where>
  <!-- 条件语句 -->
</where>
```

示例

```xml
<!-- 智能生成WHERE子句 -->
<select id="findUsers" resultType="User">
  SELECT * FROM user
  <where>
    <if test="username != null">
      AND username = #{username}
    </if>
    <if test="email != null">
      AND email = #{email}
    </if>
  </where>
</select>
```

**注意：**< where >元素只有在至少有一个子元素的条件返回内容时，才会插入"WHERE"关键字。而且，如果子元素的开头为"AND"或"OR"，< where >元素会将它们去除。

S

#### < set > 元素

用于生成SET子句，常用于UPDATE语句，能够智能地处理逗号和空条件。

基本语法

```xml
<set>
  <!-- 更新字段 -->
</set>
```

示例

```xml
<!-- 智能生成SET子句 -->
<update id="updateUser">
  UPDATE user
  <set>
    <if test="username != null">username = #{username},</if>
    <if test="email != null">email = #{email},</if>
    <if test="age != null">age = #{age},</if>
  </set>
  WHERE id = #{id}
</update>
```

**提示：**< set >元素会智能地处理末尾的逗号，如果更新语句的最后有逗号，< set >元素会自动将其去除。

#### < foreach > 元素

用于遍历集合，常用于IN条件或批量操作。

基本语法

```xml
<foreach collection="集合名称" item="元素变量名" index="索引变量名" open="开始符号" separator="分隔符" close="结束符号">
  <!-- SQL片段 -->
</foreach>
```

示例

```xml
<!-- 使用IN查询 -->
<select id="findUsersByIds" resultType="User">
  SELECT * FROM user
  WHERE id IN
  <foreach collection="ids" item="id" open="(" separator="," close=")">
    \#{id}
  </foreach>
</select>

<!-- 批量插入 -->
<insert id="batchInsert">
  INSERT INTO user (username, email) VALUES
  <foreach collection="users" item="user" separator=",">
    (#{user.username}, #{user.email})
  </foreach>
</insert>
```

**注意：**collection属性指定要遍历的集合，可以是List、Set、数组或Map。如果是Map，index是键，item是值。

B

#### < bind > 元素

用于创建一个变量并将其绑定到上下文，可以在SQL语句中使用。

基本语法

```xml
<bind name="变量名" value="表达式"/>
```

示例

```xml
<!-- 创建模糊查询条件 -->
<select id="findUsers" resultType="User">
  <bind name="pattern" value="'%' + username + '%'" />
  SELECT * FROM user
  WHERE username LIKE #{pattern}
</select>

**提示：**<bind>元素非常有用，特别是在需要多次使用同一个表达式，或者需要处理数据库兼容性问题时。
```

#### 动态SQL元素比较

| 元素                                | 用途          | 常用场景                     |
| :---------------------------------- | :------------ | :--------------------------- |
| < if >                              | 条件判断      | 根据条件包含SQL片段          |
| < choose >, < when >, < otherwise > | 多条件选择    | 实现if-else或switch-case逻辑 |
| < trim >                            | 自定义修剪    | 处理SQL前缀和后缀            |
| < where >                           | 生成WHERE子句 | 智能处理WHERE条件            |
| < set >                             | 生成SET子句   | UPDATE语句中智能处理更新字段 |
| < foreach >                         | 遍历集合      | IN查询、批量操作             |
| < bind >                            | 创建变量      | 创建可重用的表达式           |

## Redis篇

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

## Git篇

### 1. 配置与初始化

这些命令用于设置 Git 环境和创建新的仓库。

| 命令                                           | 解释                              | 示例                                                  |
| :--------------------------------------------- | :-------------------------------- | :---------------------------------------------------- |
| `git config --global user.name "[name]"`     | 设置全局用户名                    | `git config --global user.name "John Doe"`          |
| `git config --global user.email "[email]"`   | 设置全局邮箱地址                  | `git config --global user.email "john@example.com"` |
| `git config --global core.editor "[editor]"` | 设置默认文本编辑器                | `git config --global core.editor "code --wait"`     |
| `git init [project-name]`                    | 在当前目录初始化一个新的 Git 仓库 | `git init` 或 `git init my-project`               |
| `git clone [url]`                            | 克隆（下载）一个远程仓库到本地    | `git clone https://github.com/user/repo.git`        |

### 2. 基础快照操作

这些是日常开发中最常用的命令，用于跟踪文件的变更。

#### 2.1 查看状态与差异

| 命令                                    | 解释                                               | 示例                         |
| :-------------------------------------- | :------------------------------------------------- | :--------------------------- |
| `git status`                          | 显示工作目录和暂存区的状态（已修改、未跟踪的文件） | `git status`               |
| `git status -s`                       | 以精简模式显示状态                                 | `git status -s`            |
| `git diff`                            | **未暂存**的更改与最后一次提交的比较         | `git diff`                 |
| `git diff --staged` (或 `--cached`) | **已暂存**的更改与最后一次提交的比较         | `git diff --staged`        |
| `git diff [commit1] [commit2]`        | 比较两次提交之间的差异                             | `git diff a1b2c3d e4f5g6h` |

#### 2.2 添加与提交

| 命令                             | 解释                                                     | 示例                                    |
| :------------------------------- | :------------------------------------------------------- | :-------------------------------------- |
| `git add [file]`               | 将**指定文件**的更改添加到暂存区                   | `git add index.html`                  |
| `git add .`                    | 将**所有更改**（包括修改和新文件）添加到暂存区     | `git add .`                           |
| `git add -A`                   | 添加所有更改（包括修改、新文件和已删除的文件）           | `git add -A`                          |
| `git add -p`                   | **交互式**地选择要暂存的更改（块）                 | `git add -p`                          |
| `git commit -m "[message]"`    | 提交已暂存的更改，并附上提交信息                         | `git commit -m "Fix login bug"`       |
| `git commit -a -m "[message]"` | 跳过 `git add`，直接提交所有**已跟踪文件**的更改 | `git commit -a -m "Update styles"`    |
| `git commit --amend`           | **修补**最后一次提交（修改信息或加入漏掉的文件）   | `git commit --amend -m "New message"` |

#### 3. 分支与合并

分支是 Git 的核心功能，用于并行开发和管理不同功能线。

| 命令                              | 解释                                                         | 示例                                          |
| :-------------------------------- | :----------------------------------------------------------- | :-------------------------------------------- |
| `git branch`                    | 列出所有本地分支（当前分支前有 `*` 号）                    | `git branch`                                |
| `git branch -a`                 | 列出所有本地和远程分支                                       | `git branch -a`                             |
| `git branch [branch-name]`      | 创建一个新分支                                               | `git branch feature-x`                      |
| `git checkout [branch-name]`    | 切换到指定分支                                               | `git checkout main`                         |
| `git switch [branch-name]`      | (Git 2.23+) 更清晰的切换分支命令                             | `git switch main`                           |
| `git checkout -b [branch-name]` | **创建并切换**到新分支                                 | `git checkout -b hotfix`                    |
| `git switch -c [branch-name]`   | (Git 2.23+)**创建并切换**到新分支                      | `git switch -c hotfix`                      |
| `git merge [branch]`            | 将指定分支的历史合并到**当前分支**                     | `git switch main` + `git merge feature-x` |
| `git branch -d [branch-name]`   | **删除**指定的分支（已合并的分支）                     | `git branch -d feature-x`                   |
| `git branch -D [branch-name]`   | **强制删除**指定的分支（即使未合并）                   | `git branch -D experimental`                |
| `git rebase [base-branch]`      | 将当前分支的更改在目标分支上**重演**，创造更线性的历史 | `git switch feature` + `git rebase main`  |

#### 4. 查看历史与日志

用于查看项目的提交历史。

| 命令                          | 解释                            | 示例                          |
| :---------------------------- | :------------------------------ | :---------------------------- |
| `git log`                   | 按时间倒序列出提交历史          | `git log`                   |
| `git log --oneline`         | 以精简的单行模式显示历史        | `git log --oneline`         |
| `git log -p`                | 显示每次提交的详细差异（patch） | `git log -p`                |
| `git log -n [limit]`        | 限制显示提交的数量              | `git log -n 5`              |
| `git log --graph --oneline` | 以 ASCII 图形显示分支合并历史   | `git log --graph --oneline` |
| `git show [commit]`         | 显示某次提交的元数据和内容变化  | `git show a1b2c3d`          |
| `git blame [file]`          | 逐行显示文件最后的修改者和提交  | `git blame README.md`       |

#### 5. 撤销与回退

用于撤销更改或回退到之前的某个状态。

| 命令                                           | 解释                                                  | 应用场景                                    |
| :--------------------------------------------- | :---------------------------------------------------- | :------------------------------------------ |
| `git restore [file]`                         | **丢弃工作区**中指定文件的更改（未 `add` 的） | 改乱了文件，想恢复到上次提交的样子          |
| `git restore --staged [file]`                | 将文件从**暂存区**移回工作区（取消 `add`）    | `add` 了不该 add 的文件                   |
| `git reset [--soft/--mixed/--hard] [commit]` | **回退**到指定的提交                            | **慎用 `--hard`，会丢失工作区更改** |
| `git revert [commit]`                        | 创建一个**新的提交**来撤销指定提交的更改        | 安全地撤销已推送到远程的提交                |

**`git reset` 模式详解：**

- `--soft`：回退到某个版本，但**保留工作区和暂存区**的内容。
- `--mixed` (**默认**)：回退到某个版本，**保留工作区**，但重置暂存区。
- `--hard`：**彻底**回退到某个版本，丢弃工作区和暂存区的所有更改。

#### 6. 远程协作

用于与远程仓库（如 GitHub, GitLab）进行交互。

| 命令                                    | 解释                                                           | 示例                                    |
| :-------------------------------------- | :------------------------------------------------------------- | :-------------------------------------- |
| `git remote -v`                       | 列出所有已配置的远程仓库及其 URL                               | `git remote -v`                       |
| `git remote add [name] [url]`         | 添加一个新的远程仓库                                           | `git remote add origin https://...`   |
| `git fetch [remote] [branch]`         | **下载**远程仓库的变更到本地，但**不合并**         | `git fetch origin main`               |
| `git pull [remote] [branch]`          | **下载并合并**远程分支到当前分支 (`fetch` + `merge`) | `git pull origin main`                |
| `git pull --rebase [remote] [branch]` | 下载并以 `rebase` 方式合并                                   | `git pull --rebase origin main`       |
| `git push [remote] [branch]`          | 将本地分支推送到远程仓库                                       | `git push origin feature-x`           |
| `git push -u origin [branch]`         | 推送并建立本地分支与远程分支的**跟踪关系**               | `git push -u origin feature-x`        |
| `git push --force-with-lease`         | **安全强制推送**（比 `--force` 更安全）                | 在 rebase 后推送时使用                  |
| `git push [remote] --delete [branch]` | 删除远程分支                                                   | `git push origin --delete old-branch` |

#### 7. 储藏（Stashing）

临时保存工作目录的修改，以便处理其他事情。

| 命令                            | 解释                                       | 示例                                    |
| :------------------------------ | :----------------------------------------- | :-------------------------------------- |
| `git stash`                   | 将当前工作区的修改储藏起来                 | `git stash`                           |
| `git stash save "[message]"`  | 储藏并添加说明信息                         | `git stash save "WIP: login feature"` |
| `git stash list`              | 列出所有储藏                               | `git stash list`                      |
| `git stash pop`               | 应用最新的储藏并**将其从堆栈中删除** | `git stash pop`                       |
| `git stash apply [stash@{n}]` | 应用指定的储藏，但**不删除**它       | `git stash apply stash@{0}`           |
| `git stash drop [stash@{n}]`  | 删除指定的储藏                             | `git stash drop stash@{0}`            |
| `git stash clear`             | 清空所有储藏                               | `git stash clear`                     |

#### 8. 标签（Tagging）

为特定的提交打上标记，通常用于发布版本。

| 命令                                        | 解释                       | 示例                                             |
| :------------------------------------------ | :------------------------- | :----------------------------------------------- |
| `git tag`                                 | 列出所有标签               | `git tag`                                      |
| `git tag -a [tag-name] -m "[message]"`    | 创建一个带注解的标签       | `git tag -a v1.0.0 -m "Release version 1.0.0"` |
| `git tag [tag-name]`                      | 创建一个轻量标签           | `git tag v1.0.1`                               |
| `git show [tag-name]`                     | 显示标签的详细信息         | `git show v1.0.0`                              |
| `git push [remote] [tag-name]`            | 推送单个标签到远程仓库     | `git push origin v1.0.0`                       |
| `git push [remote] --tags`                | 推送所有本地标签到远程仓库 | `git push origin --tags`                       |
| `git tag -d [tag-name]`                   | 删除本地标签               | `git tag -d v1.0.1`                            |
| `git push [remote] :refs/tags/[tag-name]` | 删除远程标签               | `git push origin :refs/tags/v1.0.1`            |

#### 总结 

一个典型的 Git 工作流如下：

1. **准备**：`git status` -> `git diff` (查看更改)
2. **暂存**：`git add .` 或 `git add -p` (选择要提交的更改)
3. **提交**：`git commit -m "message"` (创建提交)
4. **同步**：`git pull --rebase origin main` (获取远程最新代码并变基)
5. **推送**：`git push origin feature-branch` (将本地提交推送到远程)

## 杂七杂八

### IDEA常用快捷键

`Ctrl + Alt + L` 格式化代码，可以对当前文件和整个包目录使用
`Ctrl+D`复制一行
`Alt + Insert` 生成代码(如GET,SET方法,构造函数等)
`Ctrl + Space`基本代码补全。
`Ctrl + Shift + Space`智能代码补全。
`Ctrl + F`在当前文件中查找文本
`Ctrl + Shift + F`在整个项目或指定窗口中查找文本
`Ctrl + N`在项目中查找类
`Ctrl + Shift + N`查找文件
`Ctrl + R`在当前文件中替换文本
`Ctrl + Shift + R`在指定窗口中替换文本
`Ctrl + Shift + ↑ / ↓`将所选代码上移/下移
