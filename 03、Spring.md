# Spring篇

## Spring注解

### 1. 注解的本质

注解本质上是一个接口，它继承自 `java.lang.annotation.Annotation`。使用 `@interface` 关键字来声明它，编译器会将其编译为一个接口。

**基本语法**

```java
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)  // 保留策略：运行时
@Target({ElementType.METHOD, ElementType.TYPE})  // 应用于方法和类
@Documented  // 是否生成 Javadoc 文档
public @interface MyAnnotation {
    String value() default "default";  // 注解属性
    int count() default 0;
}
```

### 2. 元注解 (Meta-Annotations)

在定义自己的注解之前，必须先了解 **元注解** 。元注解是用于修饰其他注解的注解，它们来自 `java.lang.annotation` 包，用来定义自定义注解的行为。

| 元注解                   | 作用                                                                                  |
| ------------------------ | ------------------------------------------------------------------------------------- |
| **`@Target`**    | **指定注解可以应用的地方** （如类、方法、字段等）。这是**必须**要设置的。 |
| **`@Retention`** | **指定注解的保留策略** （源码、编译期、运行时）。这是**必须**要设置的。   |
| `@Documented`          | 表明这个注解应该被 javadoc 工具记录，生成在 API 文档中。                              |
| `@Inherited`           | 表明该注解类型可以被自动继承。如果一个类用上了该注解，其子类会自动继承此注解。        |
| `@Repeatable`(Java 8+) | 表明注解可以在同一个声明上重复使用。                                                  |

 **`@Target` 的参数 (ElementType)** ：

* `ElementType.TYPE`：类、接口、枚举
* `ElementType.FIELD`：字段（包括枚举常量）
* `ElementType.METHOD`：方法
* `ElementType.PARAMETER`：形参
* `ElementType.CONSTRUCTOR`：构造器
* `ElementType.LOCAL_VARIABLE`：局部变量
* `ElementType.ANNOTATION_TYPE`：注解类型
* `ElementType.PACKAGE`：包
* `ElementType.TYPE_PARAMETER`(Java 8+)：类型参数
* `ElementType.TYPE_USE`(Java 8+)：类型使用

 **`@Retention` 的参数 (RetentionPolicy)** ：

* `RetentionPolicy.SOURCE`：仅存在于源码中，编译后就被丢弃（如 `@Override`, `@SuppressWarnings`）。
* `RetentionPolicy.CLASS`：存在于编译后的 `.class` 文件中，但不会被 JVM 运行时加载。 **这是默认策略，但不常用** 。
* `RetentionPolicy.RUNTIME`：存在于 `.class` 文件中，且会被 JVM 加载到运行时内存。 **可以通过反射读取** 。这是我们自定义注解最常用的策略。

### 3. 注解的元素 (Annotation Elements)

注解内部可以定义一些“属性”，看起来像接口的方法。

* **语法** ：`数据类型 属性名() [default 默认值];`
* **支持的数据类型** ：所有基本类型、`String`、`Class`、`enum`、注解，以及这些类型的数组。
* **如果注解只有一个属性** ，通常将其命名为 `value`。这样在使用时可以直接写 `@MyAnnotation("something")`，而不用写 `@MyAnnotation(value = "something")`。
* **使用 `default` 关键字可以为属性指定默认值** 。


## Spring mvc

![1757599146948](assets/1757599146948.png)

## Spring Bean 的生命周期

![1757742753489](assets/1757742753489.png)

Spring bean 一个完整的生命周期包含下面 10个阶段：

1. **实例化（Instantiation）**

   容器通过反射机制创建一个 Bean 的实例。
2. **属性赋值（Populate Properties）** ：

   容器将 Bean 的属性值（包括依赖注入的其他 Bean）设置到新创建的实例中。
3. **BeanNameAware 接口回调** ：

   如果 Bean 实现了 `BeanNameAware` 接口，容器会调用 `setBeanName(String name)` 方法，传递当前 Bean 的名称。
4. **BeanFactoryAware 接口回调** ：

   如果 Bean 实现了 `BeanFactoryAware` 接口，容器会调用 `setBeanFactory(BeanFactory beanFactory)` 方法，传递当前的 BeanFactory 实例。
5. **ApplicationContextAware 接口回调** ：

   如果 Bean 实现了 `ApplicationContextAware` 接口，容器会调用 `setApplicationContext(ApplicationContext applicationContext)` 方法，传递当前的 ApplicationContext 实例。
6. **BeanPostProcessor 前置处理** ：

   容器调用所有注册的 `BeanPostProcessor` 的 `postProcessBeforeInitialization(Object bean, String beanName)` 方法。
7. **初始化（Initialization）** ：

   如果 Bean 实现了 `InitializingBean` 接口，容器会调用 `afterPropertiesSet()` 方法。

   如果 Bean 在配置文件中通过 `init-method` 属性指定了初始化方法，容器会调用该方法。
8. **BeanPostProcessor 后置处理** ：

   容器调用所有注册的 `BeanPostProcessor` 的 `postProcessAfterInitialization(Object bean, String beanName)` 方法。
9. **使用（Usage）** ：

   此时 Bean 已经完成初始化，可以被应用程序使用。
10. **销毁（Destruction）** ：

    如果 Bean 实现了 `DisposableBean` 接口，容器会调用 `destroy()` 方法。

    如果 Bean 在配置文件中通过 `destroy-method` 属性指定了销毁方法，容器会调用该方法。

### 原理

Spring Bean 生命周期的管理主要依赖于 Spring 的 IoC（Inversion of Control，控制反转）容器。这个容器负责创建、初始化、配置和销毁 Bean。通过配置文件或注解，开发者可以定义 Bean 的依赖关系和生命周期回调方法。

Spring 通过 `BeanFactory` 和 `ApplicationContext` 接口提供了对 Bean 生命周期的控制。`ApplicationContext` 是 `BeanFactory` 的一个子接口，提供了更多高级特性，如事件发布、国际化等。
