# Spring篇

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

    如果 Bean 实现了`BeanNameAware` 接口，容器会调用 `setBeanName(String name)` 方法，传递当前 Bean 的名称。

4. **BeanFactoryAware 接口回调** ：

    如果 Bean 实现了`BeanFactoryAware` 接口，容器会调用 `setBeanFactory(BeanFactory beanFactory)` 方法，传递当前的 BeanFactory 实例。

5. **ApplicationContextAware 接口回调** ：

    如果 Bean 实现了`ApplicationContextAware` 接口，容器会调用 `setApplicationContext(ApplicationContext applicationContext)` 方法，传递当前的 ApplicationContext 实例。

6. **BeanPostProcessor 前置处理** ：

    容器调用所有注册的`BeanPostProcessor` 的 `postProcessBeforeInitialization(Object bean, String beanName)` 方法。

7. **初始化（Initialization）** ：

    如果 Bean 实现了`InitializingBean` 接口，容器会调用 `afterPropertiesSet()` 方法。

    如果 Bean 在配置文件中通过`init-method` 属性指定了初始化方法，容器会调用该方法。

8. **BeanPostProcessor 后置处理** ：

    容器调用所有注册的`BeanPostProcessor` 的 `postProcessAfterInitialization(Object bean, String beanName)` 方法。

9. **使用（Usage）** ：

    此时 Bean 已经完成初始化，可以被应用程序使用。

10. **销毁（Destruction）** ：

    如果 Bean 实现了`DisposableBean` 接口，容器会调用 `destroy()` 方法。

    如果 Bean 在配置文件中通过`destroy-method` 属性指定了销毁方法，容器会调用该方法。

### 原理

Spring Bean 生命周期的管理主要依赖于 Spring 的 IoC（Inversion of Control，控制反转）容器。这个容器负责创建、初始化、配置和销毁 Bean。通过配置文件或注解，开发者可以定义 Bean 的依赖关系和生命周期回调方法。

Spring 通过 `BeanFactory` 和 `ApplicationContext` 接口提供了对 Bean 生命周期的控制。`ApplicationContext` 是 `BeanFactory` 的一个子接口，提供了更多高级特性，如事件发布、国际化等。
