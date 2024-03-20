/*
 * OntologyLoader.java
 * This file is part of simple-ontology-aligner
 *
 * Copyright (C) 2024 - Giacomo Bergami
 *
 * simple-ontology-aligner is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * simple-ontology-aligner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with simple-ontology-aligner. If not, see <http://www.gnu.org/licenses/>.
 */

 package it.giacomobergami.simpleschema;

import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class OntologyLoader {
    private static ConcurrentHashMap<String, Ontology> ontologies = new ConcurrentHashMap<>();

    public static boolean loadOntology(String name, String file) {
        try {
            ontologies.put(name, Ontology.ontologyFromString(file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadOntology(String name, Path file) {
        try {
            ontologies.put(name, Ontology.ontologyFromFile(file));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Ontology getLoadedOntology(String name) {
        return ontologies.get(name);
    }

}
