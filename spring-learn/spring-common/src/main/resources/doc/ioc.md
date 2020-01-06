
spring容器的refresh(): 初始化容器
1. prepareRefresh: 刷新前的预处理器
     1) initPropertySources(); 初始化一些属性设置,子类自定义化的属性和方法
     2) getEnvironment().validateRequiredProperties(); 校验属性的合法性
     3) this.ear
     
2. beanFactory = obtainFreshBeanFactory();
    1) 


