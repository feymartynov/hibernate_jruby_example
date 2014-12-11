package org.jruby.ribs;

import org.hibernate.EntityNameResolver;
import org.hibernate.metamodel.binding.AttributeBinding;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.AbstractEntityTuplizer;
import org.jruby.runtime.builtin.IRubyObject;

import java.lang.String;

public class RubyTuplizer extends AbstractEntityTuplizer {
	public RubyTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
		super(entityMetamodel, mappedEntity);
	}

	protected Instantiator buildInstantiator(PersistentClass mappingInfo) {
		return new RubyInstantiator(mappingInfo);
	}

    protected Instantiator buildInstantiator(EntityBinding mappingInfo) {
        return new RubyInstantiator(mappingInfo);
    }

	private PropertyAccessor buildPropertyAccessor(Property mappedProperty) {
		if ( mappedProperty.isBackRef() ) {
			return mappedProperty.getPropertyAccessor(null);
		}
		else {
			return new RubyPropertyAccessor(mappedProperty);
		}
	}

    private PropertyAccessor buildPropertyAccessor(AttributeBinding mappedProperty) {
//        if ( mappedProperty.isBackRef() ) {
//            return mappedProperty.getPropertyAccessor(null);
//        }
//        else {
//        }
        return new RubyPropertyAccessor(mappedProperty);
    }

	protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity) {
		return buildPropertyAccessor(mappedProperty).getGetter(null, mappedProperty.getName());
	}

    protected Getter buildPropertyGetter(AttributeBinding mappedProperty) {
        return buildPropertyAccessor(mappedProperty).getGetter(null, mappedProperty.getAttribute().getName());
    }

	protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity) {
		return buildPropertyAccessor(mappedProperty).getSetter(null, mappedProperty.getName());
	}

    protected Setter buildPropertySetter(AttributeBinding mappedProperty) {
        return buildPropertyAccessor(mappedProperty).getSetter(null, mappedProperty.getAttribute().getName());
    }

	protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter) {
        return null;
	}

    protected ProxyFactory buildProxyFactory(EntityBinding mappingInfo, Getter idGetter, Setter idSetter) {
        return null;
    }

    public EntityMode getEntityMode()  {
        return EntityMode.MAP;
    };

	public Class getConcreteProxyClass() {
		return IRubyObject.class;
	}

	public boolean isInstrumented() {
		return false;
	}

	public Class getMappedClass() {
		return IRubyObject.class;
	}

    @Override
    public EntityNameResolver[] getEntityNameResolvers() {
        return new EntityNameResolver[] { BasicEntityNameResolver.INSTANCE };
    }

    @Override
    public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory) {
        return extractEmbeddedEntityName( ( IRubyObject ) entityInstance );
    }

    public static String extractEmbeddedEntityName(IRubyObject entity) {
        return null; //( String ) entity.get( DynamicMapInstantiator.KEY );
    }

    public static class BasicEntityNameResolver implements EntityNameResolver {
        public static final BasicEntityNameResolver INSTANCE = new BasicEntityNameResolver();


        @Override
        public String resolveEntityName(Object entity) {
            if ( ! IRubyObject.class.isInstance( entity ) ) {
                return null;
            }
            final String entityName = extractEmbeddedEntityName( ( IRubyObject ) entity );
            if ( entityName == null ) {
                throw new HibernateException( "Could not determine type of dynamic map entity" );
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
