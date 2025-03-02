package ink.labrador.mmsmanager.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;

    // @Override
    // public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //     context = applicationContext;
    // }

    public static <T> T getBean(Class<T> cls) {
        return beanFactory.getBean(cls);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        beanFactory = factory;
    }
}
