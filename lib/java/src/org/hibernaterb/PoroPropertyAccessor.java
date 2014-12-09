package org.hibernaterb;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.PropertyAccessException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.property.Getter;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;
import org.jruby.RubyObject;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PoroPropertyAccessor implements PropertyAccessor {
    public static final class PoroGetter implements Getter {
        private Class klass;
        private final String propertyName;

        private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

        private PoroGetter(Class klass, String propertyName) {
            this.klass = klass;
            this.propertyName = propertyName;
        }

        @Override
        public Object get(Object target) throws HibernateException {
            try {
                ScriptContext context = rubyEngine.getContext();
                context.setAttribute("target", target, ScriptContext.ENGINE_SCOPE);
                context.setAttribute("reader_name", propertyName, ScriptContext.ENGINE_SCOPE);
                rubyEngine.eval("$result = $target.public_send($reader_name)");
                return context.getAttribute("result");
            } catch (ScriptException exc) {
                throw new PropertyAccessException(
                        exc,
                        "Ruby error occured while getting attribute",
                        true,
                        klass,
                        propertyName);
            }
        }

        @Override
        public Object getForInsert(Object target, Map mergeMap, SessionImplementor session) {
            return get(target);
        }

        @Override
        public Class getReturnType() {
            return RubyObject.class;
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
        private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

        private PoroSetter(Class klass, String propertyName) {
            this.klass = klass;
            this.propertyName = propertyName;
        }

        @Override
        public void set(Object target, Object value, SessionFactoryImplementor factory)
                throws HibernateException {
            try {
                ScriptContext context = rubyEngine.getContext();
                context.setAttribute("target", target, ScriptContext.ENGINE_SCOPE);
                context.setAttribute("writer_name", propertyName + '=', ScriptContext.ENGINE_SCOPE);
                context.setAttribute("value", value, ScriptContext.ENGINE_SCOPE);
                rubyEngine.eval("$target.public_send($writer_name, $value)");
            } catch (ScriptException exc) {
                throw new PropertyAccessException(
                        exc,
                        "Ruby error occured while setting attribute",
                        true,
                        klass,
                        propertyName);
            }
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
