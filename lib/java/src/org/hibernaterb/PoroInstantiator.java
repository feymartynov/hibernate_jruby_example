package org.hibernaterb;

import java.io.IOException;
import java.io.Serializable;

import org.hibernate.InstantiationException;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.tuple.Instantiator;
import org.jruby.RubyObject;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PoroInstantiator implements Instantiator, Serializable {
    private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

    private final Class mappedClass;
    private final boolean embeddedIdentifier;
    private final Class proxyInterface;

    public PoroInstantiator(Component component) {
        this.mappedClass = component.getComponentClass();
        this.proxyInterface = null;
        this.embeddedIdentifier = false;
    }

    public PoroInstantiator(PersistentClass persistentClass) {
        this.mappedClass = persistentClass.getMappedClass();
        this.proxyInterface = persistentClass.getProxyInterface();
        this.embeddedIdentifier = persistentClass.hasEmbeddedIdentifier();
    }

    public PoroInstantiator(EntityBinding entityBinding) {
        this.mappedClass = entityBinding.getEntity().getClassReference();
        this.proxyInterface = entityBinding.getProxyInterfaceType().getValue();
        this.embeddedIdentifier = entityBinding.getHierarchyDetails().getEntityIdentifier().isEmbedded();
    }

    public Object instantiate() {
        try {
            ScriptContext context = rubyEngine.getContext();
            context.setAttribute("klass", mappedClass, ScriptContext.ENGINE_SCOPE);
            rubyEngine.eval("binding.pry; $result = $klass.new");
            return context.getAttribute("result");
        } catch (ScriptException exc) {
            throw new InstantiationException(
                    "Ruby error occured while instantiating " + mappedClass.getName(),
                    RubyObject.class,
                    exc);
        }
    }

    public Object instantiate(Serializable id) {
        final boolean useEmbeddedIdentifierInstanceAsEntity = embeddedIdentifier &&
                id != null &&
                id.getClass().equals(mappedClass);
        return useEmbeddedIdentifierInstanceAsEntity ? id : instantiate();
    }

    public boolean isInstance(Object object) {
        return object.getClass().equals(mappedClass) ||
                (proxyInterface != null && proxyInterface.isInstance(object));
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws ClassNotFoundException, IOException {
        stream.defaultReadObject();
    }
}
