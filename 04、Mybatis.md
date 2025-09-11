# Mybatis篇

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
	虽然有些开发者认为它"不优雅"，但在 MyBatis 动态 SQL 中是广泛接受的做法。
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
```

**提示：**<bind>元素非常有用，特别是在需要多次使用同一个表达式，或者需要处理数据库兼容性问题时。

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