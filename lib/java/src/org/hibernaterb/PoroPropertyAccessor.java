package org.hibernaterb;

import java.lang.Object;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.Integer;
import java.lang.Double;
import java.lang.Boolean;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;

import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.Ruby;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyBignum;
import org.jruby.RubyTime;
import org.jruby.RubyBoolean;
import org.jruby.RubyString;
import org.jruby.RubySymbol;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.RubyArray;
import org.jruby.RubyNil;
import org.jruby.ext.bigdecimal.RubyBigDecimal;

public class PoroPropertyAccessor implements PropertyAccessor {

    public static final class PoroGetter implements Getter {
        private Class klass;
        private final String propertyName;
        private final String name;

        private PoroGetter(Class klass, String propertyName) {
            this.klass = klass;
            this.propertyName = propertyName;
            this.name = propertyName.toLowerCase();
        }

        @Override
        public Object get(Object owner) throws HibernateException {
            if (!(owner instanceof IRubyObject)) {
                throw new HibernateException("Attempted to get an attribute from a non-ruby object.");
            }

            Ruby runtime = ((IRubyObject) owner).getRuntime();
            IRubyObject rubyValue = ((IRubyObject) owner).callMethod(runtime.getCurrentContext(), name);

            if (rubyValue instanceof RubyFixnum) {
                return rubyValue.toJava(Integer.class);
            } else if (rubyValue instanceof RubyFloat) {
                return rubyValue.toJava(Double.class);
            } else if (rubyValue instanceof RubyBigDecimal) {
                return rubyValue.toJava(BigDecimal.class);
            } else if (rubyValue instanceof RubyBignum) {
                return rubyValue.toJava(BigInteger.class);
            } else if (rubyValue instanceof RubyTime) {
                return rubyValue.toJava(Date.class);
            } else if (rubyValue instanceof RubyBoolean) {
                return rubyValue.toJava(Boolean.class);
            } else if (rubyValue instanceof RubyString ||
                       rubyValue instanceof RubySymbol ||
                       rubyValue instanceof RubyClass ||
                       rubyValue instanceof RubyModule) {
                return rubyValue.toJava(String.class);
            } else if (rubyValue instanceof RubyArray) {
                return rubyValue;
            } else if (rubyValue instanceof RubyNil) {
                return null;
            } else {
                throw new HibernateException(
                        String.format(
                                "Unknown value type `%s` for property `%s`.",
                                rubyValue.getMetaClass().getName(),
                                name));
            }
        }

        @Override
        public Object getForInsert(Object target, Map mergeMap, SessionImplementor session) {
            return get(target);
        }

        @Override
        public Class getReturnType() {
            return Object.class;
        }

        @Override
        public Member getMember() {
            return null;
        }

        @Override
        public Method getMethod() {
            return null;
        }

        @Override
        public String getMethodName() {
            return null;
        }

        @Override
        public String toString() {
            return "PoroGetter(" + klass.getName() + '.' + propertyName + ')';
        }
    }

    public static final class PoroSetter implements Setter {
        private Class klass;
        private final String propertyName;
        private final String name;

        private PoroSetter(Class klass, String propertyName) {
            this.klass = klass;
            this.propertyName = propertyName;
            this.name = propertyName.toLowerCase();
        }

        @Override
        public void set(Object target, Object value, SessionFactoryImplementor factory) throws HibernateException {
            Ruby runtime = ((IRubyObject) target).getRuntime();
            IRubyObject rubyObject = null;

            if (value instanceof Date) {
                long milisecs = ((Date) value).getTime();
                rubyObject = RubyTime.newTime(runtime, milisecs);
            } else {
                rubyObject = JavaUtil.convertJavaToRuby(runtime, value);
            }

            ((IRubyObject)target).callMethod(runtime.getCurrentContext(), name + "=", rubyObject);
        }

        @Override
        public Method getMethod() {
            return null;
        }

        @Override
        public String getMethodName() {
            return null;
        }

        @Override
        public String toString() {
            return "PoroSetter(" + klass.getName() + '.' + propertyName + ')';
        }
    }

    public Getter getGetter(Class klass, String propertyName) {
        return new PoroGetter(klass, propertyName);
    }

    public Setter getSetter(Class klass, String propertyName) {
        return new PoroSetter(klass, propertyName);
    }
}
