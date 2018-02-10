package rahul.api.userdetails.dba.configuration;

import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

import javax.persistence.PersistenceException;

public class RetryableLocalEntityManagerFactory extends LocalEntityManagerFactoryBean {
    private LocalEntityManagerFactoryBean delegate;

    public RetryableLocalEntityManagerFactory(LocalEntityManagerFactoryBean bean) {
        this.delegate = bean;
    }

    public LocalEntityManagerFactoryBean getDelegate() {
        return delegate;
    }

    @Override
    //@Retryable( maxAttempts=10, backoff=@Backoff(delay = 5000))
    public void afterPropertiesSet() throws PersistenceException {
        System.out.println("Before Properties Set");
        try {
            delegate.afterPropertiesSet();
            System.out.println("Properties Set");
        } catch (PersistenceException pe) {
            int count = 1;
            retryAfterPropertiesSet(count);
        }
    }

    private void retryAfterPropertiesSet(int count) throws PersistenceException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Retry Before Properties Set attempt " + count);
        try {
            delegate.afterPropertiesSet();
            System.out.println("Properties Set At retry Count " + count);
        } catch (PersistenceException pe) {
            if (count <= 12) {
                retryAfterPropertiesSet(++count);
            }
        }
    }
   /* public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return delegate.translateExceptionIfPossible(ex);
    }

    public EntityManagerFactory getNativeEntityManagerFactory() {
        if (this.nativeEntityManagerFactory != null) {
            return this.nativeEntityManagerFactory;
        } else {
            try {
                return (EntityManagerFactory)this.nativeEntityManagerFactoryFuture.get();
            } catch (InterruptedException var2) {
                throw new IllegalStateException("Interrupted during initialization of native EntityManagerFactory: " + var2.getMessage());
            } catch (ExecutionException var3) {
                throw new IllegalStateException("Failed to asynchronously initialize native EntityManagerFactory: " + var3.getMessage(), var3.getCause());
            }
        }
    }

    public PersistenceUnitInfo getPersistenceUnitInfo() {
        return null;
    }

    public DataSource getDataSource() {
        return null;
    }

    public EntityManagerFactory getObject() {
        return this.entityManagerFactory;
    }

    public Class<? extends EntityManagerFactory> getObjectType() {
        return this.entityManagerFactory != null ? this.entityManagerFactory.getClass() : EntityManagerFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("Closing JPA EntityManagerFactory for persistence unit '" + this.getPersistenceUnitName() + "'");
        }

        this.entityManagerFactory.close();
    }

    Object invokeProxyMethod(Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().isAssignableFrom(EntityManagerFactoryInfo.class)) {
            return method.invoke(this, args);
        } else if (method.getName().equals("createEntityManager") && args != null && args.length > 0 && args[0] != null && args[0].getClass().isEnum() && "SYNCHRONIZED".equals(args[0].toString())) {
            EntityManager rawEntityManager = args.length > 1 ? this.getNativeEntityManagerFactory().createEntityManager((Map)args[1]) : this.getNativeEntityManagerFactory().createEntityManager();
            return ExtendedEntityManagerCreator.createApplicationManagedEntityManager(rawEntityManager, this, true);
        } else {
            if (args != null) {
                for(int i = 0; i < args.length; ++i) {
                    Object arg = args[i];
                    if (arg instanceof Query && Proxy.isProxyClass(arg.getClass())) {
                        try {
                            args[i] = ((Query)arg).unwrap((Class)null);
                        } catch (RuntimeException var6) {
                            ;
                        }
                    }
                }
            }

            Object retVal = method.invoke(this.getNativeEntityManagerFactory(), args);
            if (retVal instanceof EntityManager) {
                EntityManager rawEntityManager = (EntityManager)retVal;
                retVal = ExtendedEntityManagerCreator.createApplicationManagedEntityManager(rawEntityManager, this, false);
            }

            return retVal;
        }
    }


    public void setPersistenceProviderClass(Class<? extends PersistenceProvider> persistenceProviderClass) {
        this.persistenceProvider = (PersistenceProvider)BeanUtils.instantiateClass(persistenceProviderClass);
    }

    public void setPersistenceProvider(PersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    public PersistenceProvider getPersistenceProvider() {
        return this.persistenceProvider;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public String getPersistenceUnitName() {
        return this.persistenceUnitName;
    }

    public void setJpaProperties(Properties jpaProperties) {
        CollectionUtils.mergePropertiesIntoMap(jpaProperties, this.jpaPropertyMap);
    }

    public void setJpaPropertyMap(Map<String, ?> jpaProperties) {
        if (jpaProperties != null) {
            this.jpaPropertyMap.putAll(jpaProperties);
        }

    }

    public Map<String, Object> getJpaPropertyMap() {
        return this.jpaPropertyMap;
    }

    public void setEntityManagerFactoryInterface(Class<? extends EntityManagerFactory> emfInterface) {
        this.entityManagerFactoryInterface = emfInterface;
    }

    public void setEntityManagerInterface(Class<? extends EntityManager> emInterface) {
        this.entityManagerInterface = emInterface;
    }

    public Class<? extends EntityManager> getEntityManagerInterface() {
        return this.entityManagerInterface;
    }

    public void setJpaDialect(JpaDialect jpaDialect) {
        this.jpaDialect = jpaDialect;
    }

    public JpaDialect getJpaDialect() {
        return this.jpaDialect;
    }

    public void setJpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    public JpaVendorAdapter getJpaVendorAdapter() {
        return this.jpaVendorAdapter;
    }

    public void setBootstrapExecutor(AsyncTaskExecutor bootstrapExecutor) {
        this.bootstrapExecutor = bootstrapExecutor;
    }

    public AsyncTaskExecutor getBootstrapExecutor() {
        return this.bootstrapExecutor;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }*/
}
