/*
 * IteratorConceptProperty.java
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

import java.util.*;

public class IteratorConceptProperty implements IConcept {
    private final IConcept PARENT;
    final Map.Entry<String, IConcept> curr;
    public IteratorConceptProperty(IConcept PARENT, Map.Entry<String, IConcept> curr) {
        this.PARENT = PARENT;
        this.curr = curr;
    }

    @Override
    public int hashCode() {
        return curr.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IteratorConceptProperty that = (IteratorConceptProperty) o;
        return Objects.equals(curr, that.curr);
    }

    @Override
    public IConcept getAncestor() {
        return PARENT;
    }

    @Override
    public String getSemanticName() {
        return curr.getKey();
    }

    @Override
    public NativeTypes getType() {
        return curr.getValue().isBasic() ? curr.getValue().getType() : NativeTypes.CONCEPT;
    }

    @Override
    public boolean isBasic() {
        return curr.getValue().isBasic();
    }

    @Override
    public Optional<Concept> asConcept() {
        return curr.getValue().asConcept();
    }

    @Override
    public int countConceptNodes(Set<String> visited) {
        return curr.getValue().isBasic() ? 0 : curr.getValue().countConceptNodes(visited);
    }

    @Override
    public boolean isList() {
        return curr.getValue().isList();
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return curr.getValue().components();
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return curr.getValue().components2();
    }
}
