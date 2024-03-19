/*
 * IConcept.java
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

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IConcept {

    IConcept getAncestor();
    String getSemanticName();
    NativeTypes getType();
    boolean isBasic();

    Optional<Concept> asConcept();

    int countConceptNodes(Set<String> visited);

    boolean isList();

    Set<Map.Entry<String, IConcept>> components();
    Collection<? extends IConcept> components2();
}
