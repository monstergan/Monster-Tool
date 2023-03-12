package com.monster.core.tool.beans;

import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.ReflectUtils;

import java.security.ProtectionDomain;

/**
 * 重写 cglib BeanMap，支持链式bean
 * <p>
 * Create on 2023/3/12 22:26
 *
 * @author monster gan
 */
public abstract class MonsterBeanMap extends BeanMap {

    protected MonsterBeanMap() {
    }

    protected MonsterBeanMap(Object bean) {
        super(bean);
    }

    public static MonsterBeanMap create(Object bean) {
        KteGenerator gen = new KteGenerator();
        gen.setBean(bean);
        return gen.create();
    }

    /**
     * newInstance
     *
     * @param o Object
     * @return KteBeanMap
     */
    @Override
    public abstract MonsterBeanMap newInstance(Object o);

    public static class KteGenerator extends AbstractClassGenerator {
        private static final Source SOURCE = new Source(MonsterBeanMap.class.getName());

        private Object bean;
        private Class beanClass;
        private int require;

        public KteGenerator() {
            super(SOURCE);
        }

        /**
         * Set the bean that the generated map should reflect. The bean may be swapped
         * out for another bean of the same type using {@link #setBean}.
         * Calling this method overrides any value previously set using {@link #setBeanClass}.
         * You must call either this method or {@link #setBeanClass} before {@link #create}.
         *
         * @param bean the initial bean
         */
        public void setBean(Object bean) {
            this.bean = bean;
            if (bean != null) {
                beanClass = bean.getClass();
            }
        }

        /**
         * Set the class of the bean that the generated map should support.
         * You must call either this method or {@link #setBeanClass} before {@link #create}.
         *
         * @param beanClass the class of the bean
         */
        public void setBeanClass(Class beanClass) {
            this.beanClass = beanClass;
        }

        /**
         * Limit the properties reflected by the generated map.
         *
         * @param require any combination of {@link #REQUIRE_GETTER} and
         *                {@link #REQUIRE_SETTER}; default is zero (any property allowed)
         */
        public void setRequire(int require) {
            this.require = require;
        }

        @Override
        protected ClassLoader getDefaultClassLoader() {
            return beanClass.getClassLoader();
        }

        @Override
        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(beanClass);
        }

        /**
         * Create a new instance of the <code>BeanMap</code>. An existing
         * generated class will be reused if possible.
         *
         * @return {KteBeanMap}
         */
        public MonsterBeanMap create() {
            if (beanClass == null) {
                throw new IllegalArgumentException("Class of bean unknown");
            }
            setNamePrefix(beanClass.getName());
            MonsterBeanMapKey key = new MonsterBeanMapKey(beanClass, require);
            return (MonsterBeanMap) super.create(key);
        }

        @Override
        public void generateClass(ClassVisitor v) throws Exception {
            new MonsterBeanMapEmitter(v, getClassName(), beanClass, require);
        }

        @Override
        protected Object firstInstance(Class type) {
            return ((BeanMap) ReflectUtils.newInstance(type)).newInstance(bean);
        }

        @Override
        protected Object nextInstance(Object instance) {
            return ((BeanMap) instance).newInstance(bean);
        }
    }

}
