/*
 * ConceptProperty.java
 * This file is part of simple-ontology-alignment
 *
 * Copyright (C) 2024 - Giacomo Bergami
 *
 * simple-ontology-alignment is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * simple-ontology-alignment is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with simple-ontology-alignment. If not, see <http://www.gnu.org/licenses/>.
 */

 package it.giacomobergami.simpleschema;

import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.util.*;

public class ConceptProperty extends ImmutablePair<String, NativeTypes> implements IConcept {

    public ConceptProperty(IConcept  ancestor, String key, NativeTypes value) {
        super(key, value);
        this.ancestor = ancestor;
    }

    public IConcept ancestor;

    @Override
    public IConcept getAncestor() {
        return ancestor;
    }

    @Override
    public String getSemanticName() {
        return getKey();
    }

    @Override
    public NativeTypes getType() {
        return getValue();
    }

    @Override
    public boolean isBasic() {
        return true;
    }

    @Override
    public Optional<Concept> asConcept() {
        return Optional.empty();
    }


    @Override
    public int countConceptNodes(Set<String> visited) {
        return 1;
    }

    @Override
    public boolean isList() {
        return getValue().asList() == getValue();
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return Collections.emptySet();
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return Collections.emptyList();
    }


}
