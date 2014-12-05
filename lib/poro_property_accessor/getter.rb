class PoroPropertyAccessor::Getter
  include Java::OrgHibernateProperty::Getter

  def initialize(klass, property_name)
    @klass = klass
    @property_name = property_name
  end

  java_signature('java.lang.Object get(java.lang.Object target)')
  def get(target)
    target.public_send(getMethodName).to_java
  end

  java_signature('java.lang.Object getForInsert(java.lang.Object owner,
                                                java.util.Map mergeMap,
                                                org.hibernate.engine.spi.SessionImplementor session)')
  def getForInsert(target, *_)
    get(target)
  end

  java_signature('java.lang.reflect.Member getMember()')
  def getMember
    getMethod
  end

  java_signature('java.lang.Class getReturnType()')
  def getReturnType
    Object.java_class
  end

  java_signature('java.lang.String getMethodName()')
  def getMethodName
    @property_name
  end

  java_signature('java.lang.reflect.Method getMethod()')
  def getMethod
    @klass.instance_method(getMethodName)
  end

  become_java!(false)
end
