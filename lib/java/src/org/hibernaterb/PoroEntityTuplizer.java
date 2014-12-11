package org.hibernaterb;

import java.io.Serializable;
import java.lang.Override;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.HibernateException;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.EntityMode;
import org.hibernate.EntityNameResolver;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;

import org.jruby.RubyObject;
import org.jruby.RubyClass;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.hibernaterb.PoroInstantiator;
import org.hibernaterb.PoroPropertyAccessor;

class PoroEntityTuplizer extends AbstractEntityTuplizer {
    private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

    private final Set lazyPropertyNames = new HashSet();

    public PoroEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
        super(entityMetamodel, mappedEntity);

        Iterator iter = mappedEntity.getPropertyClosureIterator();
        while (iter.hasNext()) {
            Property property = (Property) iter.next();
            if (property.isLazy()) {
                lazyPropertyNames.add(property.getName());
            }
        }

        String[] getterNames = new String[propertySpan];
        String[] setterNames = new String[propertySpan];

        for (int i = 0; i < propertySpan; i++) {
            getterNames[i] = getters[i].getMethodName();
            setterNames[i] = setters[i].getMethodName();
        }
    }

    public PoroEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappedEntity) {
        super(entityMetamodel, mappedEntity);

        for (AttributeBinding property : mappedEntity.getAttributeBindingClosure()) {
            if (property.isLazy()) {
                lazyPropertyNames.add(property.getAttribute().getName());
            }
        }

        String[] getterNames = new String[propertySpan];
        String[] setterNames = new String[propertySpan];

        for (int i = 0; i < propertySpan; i++) {
            getterNames[i] = getters[i].getMethodName();
            setterNames[i] = setters[i].getMethodName();
        }
    }

    @Override
    public EntityMode getEntityMode() {
        return EntityMode.POJO;
    }

    @Override
    protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity) {
        return mappedProperty.getPropertyAccessor(null).getGetter(null, mappedProperty.getName());
    }

    @Override
    protected Getter buildPropertyGetter(AttributeBinding mappedProperty) {
        return (new PoroPropertyAccessor()).getGetter(null, mappedProperty.getAttribute().getName());
    }

    @Override
    protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity) {
        return mappedProperty.getPropertyAccessor(null).getSetter(null, mappedProperty.getName());
    }

    @Override
    protected Setter buildPropertySetter(AttributeBinding mappedProperty) {
        return (new PoroPropertyAccessor()).getSetter(null, mappedProperty.getAttribute().getName());
    }

    @Override
    protected Instantiator buildInstantiator(PersistentClass mappingInfo) {
        return new PoroInstantiator(mappingInfo);
    }

    @Override
    protected Instantiator buildInstantiator(EntityBinding mappingInfo) {
        return new PoroInstantiator(mappingInfo);
    }

    @Override
    protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter) {
        return null;
    }

    @Override
    protected ProxyFactory buildProxyFactory(EntityBinding mappingInfo, Getter idGetter, Setter idSetter) {
        return null;
    }

    @Override
    public Class getMappedClass() {
        return RubyObject.class;
    }

    @Override
    public Class getConcreteProxyClass() {
        return RubyObject.class;
    }

    @Override
    public boolean isInstrumented() {
        return false;
    }

    @Override
    public EntityNameResolver[] getEntityNameResolvers() {
        return new EntityNameResolver[] { BasicEntityNameResolver.INSTANCE };
    }

    @Override
    public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
        return extractEmbeddedEntityName((RubyObject) entityInstance);
    }

    public static String extractEmbeddedEntityName(RubyObject entity) {
        return entity.getMetaClass().getName();
    }

    public static class BasicEntityNameResolver implements EntityNameResolver {
        public static final BasicEntityNameResolver INSTANCE = new BasicEntityNameResolver();

        @Override
        public String resolveEntityName(Object entity) {
            if (!RubyObject.class.isInstance(entity)) return null;

            final String entityName = extractEmbeddedEntityName((RubyObject) entity );

            if (entityName == null) {
                throw new HibernateException( "Could not determine type of RubyObject entity" );
            }

            return entityName;
        }

        @Override
        public boolean equals(Object obj) {
            return getClass().equals( obj.getClass() );
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }
}
