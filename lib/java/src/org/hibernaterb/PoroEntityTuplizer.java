package org.hibernaterb;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.tuple.entity.EntityTuplizer;

class PoroEntityTuplizer implements EntityTuplizer {
	@Override
	public EntityMode getEntityMode() {
		return EntityMode.POJO;
	}

	@Override
	public Object instantiate();

	@Override
	public Object instantiate(Serializable id) throws HibernateException;

	@Override
	public Object instantiate(Serializable id, SessionImplementor session);

	@Override
	public Serializable getIdentifier(Object entity) throws HibernateException;

	@Override
	public Serializable getIdentifier(Object entity, SessionImplementor session);

	@Override
	public void setIdentifier(Object entity, Serializable id) throws HibernateException;

	@Override
	public void setIdentifier(Object entity, Serializable id, SessionImplementor session);

	@Override
	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion);

	@Override
	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion, SessionImplementor session);

	@Override
	public Object getVersion(Object entity) throws HibernateException;

	@Override
	public void setPropertyValues(Object entity, Object[] values);

	@Override
	public void setPropertyValue(Object entity, int i, Object value) throws HibernateException;

	@Override
	public void setPropertyValue(Object entity, String propertyName, Object value) throws HibernateException;

	@Override
	public Object[] getPropertyValues(Object entity);

	@Override
	public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
	throws HibernateException;

	@Override
	public Object getPropertyValue(Object entity, String propertyName) throws HibernateException;

	@Override
	public Object getPropertyValue(Object entity, int i);

	@Override
	public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session);

	@Override
	public boolean hasProxy();

	@Override
	public Object createProxy(Serializable id, SessionImplementor session) throws HibernateException;

	@Override
	public boolean isLifecycleImplementor();

	@Override
	public Class getConcreteProxyClass();

	@Override
	public boolean hasUninitializedLazyProperties(Object entity);

	@Override
	public boolean isInstrumented();

	@Override
	public EntityNameResolver[] getEntityNameResolvers();

	@Override
	public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory);

	@Override
	public Getter getIdentifierGetter();

	@Override
	public Getter getVersionGetter();

	@Override
	public boolean isInstance(Object object);

	@Override
	public Class getMappedClass();

	@Override
	public Getter getGetter(int i);
}
