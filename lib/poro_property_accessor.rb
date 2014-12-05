class PoroPropertyAccessor
  include Java::OrgHibernateProperty::PropertyAccessor

  java_signature('org.hibernate.property.Getter getGetter(java.lang.Class klass, java.lang.String propertyName)')
  def getGetter(klass, property_name)
    PoroPropertyAccessor::Getter.new(klass, property_name)
  end

  java_signature('org.hibernate.property.Setter getSetter(java.lang.Class klass, java.lang.String propertyName)')
  def getSetter(klass, property_name)
    PoroPropertyAccessor::Setter.new(klass, property_name)
  end

  become_java!(false)
end

require 'lib/poro_property_accessor/getter'
require 'lib/poro_property_accessor/setter'
