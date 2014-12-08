package org.hibernaterb;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.hibernate.tuple.entity.EntityMetamodel;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jruby.RubyObject;

class PoroEntityTuplizer extends AbstractEntityTuplizer {
	private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

	@Override
	public EntityMode getEntityMode() {
		return EntityMode.POJO;
	}

	@Override
	public Object instantiate() {
    	try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("klass", entityMetamodel.name, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$result = Object.const_get($klass).new");
			return context.getAttribute("result");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public Object instantiate(Serializable id) throws HibernateException {
		Object entity = instantiate();
		setIdentifier(entity, id);
		return entity;
	}

	@Override
	public Object instantiate(Serializable id, SessionImplementor session) {
		return instantiate(id);
	}

	@Override
	public Serializable getIdentifier(Object entity) throws HibernateException {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$result = $entity.id");
			return context.getAttribute("result");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public Serializable getIdentifier(Object entity, SessionImplementor session) {
		return getIdentifier(entity);
	}

	@Override
	public void setIdentifier(Object entity, Serializable id) throws HibernateException {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("id", id, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$entity.id = $id");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public void setIdentifier(Object entity, Serializable id, SessionImplementor session) {
		setIdentifier(entity, id);
	}

	@Override
	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion) {
		setIdentifier(entity, currentId);
		// TODO: set version
	}

	@Override
	public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion, SessionImplementor session) {
		resetIdentifier(entity, currentId, currentVersion);
	}

	@Override
	public Object getVersion(Object entity) throws HibernateException {
		// TODO: version support for optimistic locking
		return null;
	}

	@Override
	public void setPropertyValues(Object entity, Object[] values) {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("values", values, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("for (k, v) in $values; $entity.public_send(\"#{k}=\", v); end");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public void setPropertyValue(Object entity, int i, Object value) throws HibernateException {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("idx", i, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("value", value, ScriptContext.ENGINE_SCOPE);
			// TODO: trash
			rubyEngine.eval("attr = $entity.to_h.keys[$idx]; $entity.public_send(\"#{attr}=\", $value)");
		} catch (ScriptException exc) {
			// TODO
		}	
	}

	@Override
	public void setPropertyValue(Object entity, String propertyName, Object value) throws HibernateException {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("property_name", propertyName, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("value", value, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$entity.public_send(\"#{$property_name}=\", $value)");
		} catch (ScriptException exc) {
			// TODO
		}		
	}

	@Override
	public Object[] getPropertyValues(Object entity) {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$result = $entity.to_h");
			return context.getAttribute("result");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
	throws HibernateException;

	@Override
	public Object getPropertyValue(Object entity, String propertyName) throws HibernateException {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("property_name", propertyName, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$entity.public_send($property_name)");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public Object getPropertyValue(Object entity, int i) {
		try {
			ScriptContext context = rubyEngine.getContext();
			context.setAttribute("entity", entity, ScriptContext.ENGINE_SCOPE);
			context.setAttribute("idx", i, ScriptContext.ENGINE_SCOPE);
			rubyEngine.eval("$entity.to_h.to_a[$idx][1]");
		} catch (ScriptException exc) {
			// TODO
		}
	}

	@Override
	public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session) {
		// NOOP
	}

	@Override
	public boolean hasProxy() {
		return false;
	}

	@Override
	public Object createProxy(Serializable id, SessionImplementor session) throws HibernateException {
		return null;
	}

	@Override
	public boolean isLifecycleImplementor() {
		return false;
	}

	@Override
	public Class getConcreteProxyClass() {
		return null;
	}

	@Override
	public boolean hasUninitializedLazyProperties(Object entity) {
		// TODO: check for uninitialized lazy properties for lazy loading support
		return false;
	}

	@Override
	public boolean isInstrumented() {
		return false;
	}

	@Override
	public EntityNameResolver[] getEntityNameResolvers() {
		return null;
	}

	@Override
	public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
		// TODO: implement if needed
		return null;
	}

	@Override
	public Getter getIdentifierGetter() {
		// TODO: there's no entity or entity klass to get a getter for :(
		return null;
	}

	@Override
	public Getter getVersionGetter() {
		// TODO: version support for optimistic locking
		return null;
	}

	@Override
	public boolean isInstance(Object object) {
		// TODO: compare metaclasses
		return true;
	}

	@Override
	public Class getMappedClass() {
		return RubyObject.class;
	}

	@Override
	public Getter getGetter(int i) {
		// TODO: there's no entity or entity klass to get a getter for :(
		return null;
	}
}
