class PoroEntityTuplizer
  include Java::OrgHibernateTupleEntity::EntityTuplizer

  # classes = [Java::OrgHibernateTupleEntity::EntityMetamodel, Java::OrgHibernateMapping::PersistentClass].to_java(Java::JavaLang::Class)
  # PoroEntityTuplizer.java_class.getDeclaredConstructor(classes)

  java_signature('PoroEntityTuplizer(org.hibernate.tuple.entity.EntityMetamodel entityMetamodel,
                                     org.hibernate.mapping.PersistentClass mappedEntity)')
  def initialize(entity_metamodel, mapped_entity)
  end

  java_signature 'java.lang.Object[] getPropertyValues(java.lang.Object entity)'
  def getPropertyValues(entity)
    fail NotImplementedError
  end

  java_signature 'void setPropertyValues(java.lang.Object entity, java.lang.Object[] values)'
  def setPropertyValues(entity, values)
    fail NotImplementedError
  end

  java_signature 'boolean isInstance(java.lang.Object object)'
  def isInstance(object)
    fail NotImplementedError
  end

  java_signature 'java.lang.Class getMappedClass()'
  def getMappedClass
    fail NotImplementedError
  end

  java_signature 'org.hibernate.property.Getter getGetter(int i)'
  def getGetter(index)
    fail NotImplementedError
  end

  java_signature 'org.hibernate.EntityMode getEntityMode()'
  def getEntityMode
    fail NotImplementedError
  end

  java_signature 'java.lang.Object instantiate()'
  java_signature 'java.lang.Object instantiate(java.io.Serializable id,
                                               org.hibernate.engine.spi.SessionImplementor session)'
  def instantiate(id, session = nil)
    fail NotImplementedError
  end

  java_signature 'java.io.Serializable getIdentifier(java.lang.Object entity,
                                                     org.hibernate.engine.spi.SessionImplementor session)'
  def getIdentifier(entity, session = nil)
    fail NotImplementedError
  end

  java_signature 'void setIdentifier(java.lang.Object entity,
                                     java.io.Serializable id,
                                     org.hibernate.engine.spi.SessionImplementor session)'
  def setIdentifier(entity, id, session = nil)
    fail NotImplementedError
  end

  java_signature 'void resetIdentifier(java.lang.Object entity,
                                       java.io.Serializable currentId,
                                       java.lang.Object currentVersion,
                                       org.hibernate.engine.spi.SessionImplementor session)'
  def resetIdentifier(entity, current_id, current_version, session = nil)
    fail NotImplementedError
  end

  java_signature 'java.lang.Object getVersion(java.lang.Object entity) throws org.hibernate.HibernateException'
  def getVersion(entity)
    fail NotImplementedError
  end

  java_signature 'void setPropertyValue(java.lang.Object entity, int i, java.lang.Object value)
                    throws org.hibernate.HibernateException'
  java_signature 'void setPropertyValue(java.lang.Object entity,
                                        java.lang.String propertyName,
                                        java.lang.Object value)
                    throws org.hibernate.HibernateException'
  def setPropertyValue(entity, property_name_or_index, value)
    fail NotImplementedError
  end

  java_signature 'java.lang.Object[] getPropertyValuesToInsert(java.lang.Object entity,
                                                               java.util.Map mergeMap,
                                                               org.hibernate.engine.spi.SessionImplementor session)'
  def getPropertyValuesToInsert(entity, merge_map, session)
    fail NotImplementedError
  end

  java_signature 'java.lang.Object getPropertyValue(java.lang.Object entity, int i)'
  java_signature 'java.lang.Object getPropertyValue(java.lang.Object entity, java.lang.String propertyName)
                    throws org.hibernate.HibernateException'
  def getPropertyValue(entity, property_name_or_index)
    fail NotImplementedError
  end

  java_signature 'void afterInitialize(java.lang.Object entity,
                                       boolean lazyPropertiesAreUnfetched,
                                       org.hibernate.engine.spi.SessionImplementor session)'
  def afterInitialize(entity, lazy_poperties_are_unfetched, session)
    fail NotImplementedError
  end

  java_signature 'boolean hasProxy()'
  def hasProxy
    false.to_java
  end

  java_signature 'java.lang.Object createProxy(java.io.Serializable id,
                                               org.hibernate.engine.spi.SessionImplementor session)
                    throws org.hibernate.HibernateException'
  def createProxy(id, session)
    fail NotImplementedError
  end

  java_signature 'boolean isLifecycleImplementor()'
  def isLifecycleImplementor
    false.to_java
  end

  java_signature 'java.lang.Class getConcreteProxyClass()'
  def getConcreteProxyClass
    fail NotImplementedError
  end

  java_signature 'boolean hasUninitializedLazyProperties(java.lang.Object entity)'
  def hasUninitializedLazyProperties(entity)
    fail NotImplementedError
  end

  java_signature 'boolean isInstrumented()'
  def isInstrumented
    false.to_java
  end

  java_signature 'org.hibernate.EntityNameResolver[] getEntityNameResolvers()'
  def getEntityNameResolvers
    fail NotImplementedError
  end

  java_signature 'java.lang.String determineConcreteSubclassEntityName(
                                     java.lang.Object entityInstance,
                                     org.hibernate.engine.spi.SessionFactoryImplementor factory)'
  def determineConcreteSubclassEntityName(entity_instance, factory)
    fail NotImplementedError
  end

  java_signature 'org.hibernate.property.Getter getIdentifierGetter()'
  def getIdentifierGetter
    fail NotImplementedError
  end

  java_signature 'org.hibernate.property.Getter getVersionGetter()'
  def getVersionGetter
    fail NotImplementedError
  end

  become_java!(false)
end
