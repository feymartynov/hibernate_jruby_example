package org.hibernaterb;

import org.hibernate.tuple.component.PojoComponentTuplizer;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.Instantiator;

class PoroComponentTuplizer extends PojoComponentTuplizer {
    public PoroComponentTuplizer(Component component) {
        super(component);
    }

    @Override
    protected Instantiator buildInstantiator(Component component) {
        return new PoroInstantiator(component);
    }
}
