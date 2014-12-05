class PoroPropertyAccessor::Setter
  include Java::OrgHibernateProperty::Setter

  def initialize(_, property_name)
    @property_name = property_name
  end

  java_signature('void set(java.lang.Object target, java.lang.Object value)')
  def set(target, value, *_)
    target.public_send(getMethodName, value)
  end

  java_signature('java.lang.String getMethodName()')
  def getMethodName
    "#{@property_name}="
  end

  java_signature('java.lang.reflect.Method getMethod()')
  def getMethod
    @klass.instance_method(getMethodName)
  end

  become_java!(false)
end
