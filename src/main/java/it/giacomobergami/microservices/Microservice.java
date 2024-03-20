/*
 * Microservice.java
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

 package it.giacomobergami.microservices;

import it.giacomobergami.simpleschema.Ontology;

import java.util.List;

public class Microservice {
    public final String name;
    public final int port;
    public final  String address;
    public final List<MicroserviceFunction> ls;
    public final Ontology microserviceOntology;

    public Microservice(String name, int port, String address, List<MicroserviceFunction> ls, Ontology microserviceOntology) {
        this.name = name;
        this.port = port;
        this.address = address;
        this.ls = ls;
        this.microserviceOntology = microserviceOntology;
    }
}
