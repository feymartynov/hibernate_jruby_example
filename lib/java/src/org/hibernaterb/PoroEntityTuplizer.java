package org.hibernaterb;

import java.io.Serializable;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;
import org.hibernate.engine.spi.SessionImplementor;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.hibernaterb.PoroInstantiator;

class PoroEntityTuplizer extends PojoEntityTuplizer {
    private static ScriptEngine rubyEngine = new ScriptEngineManager().getEngineByName("jruby");

    public PoroEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
        super(entityMetamodel, mappedEntity);
    }

    public PoroEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappedEntity) {
        super(entityMetamodel, mappedEntity);
    }

    @Override
    protected Instantiator buildInstantiator(PersistentClass mappingInfo) {
        return new PoroInstantiator(mappingInfo);
    }

    @Override
    protected Instantiator buildInstantiator(EntityBinding mappingInfo) {
        return new PoroInstantiator(mappingInfo);
    }
}
