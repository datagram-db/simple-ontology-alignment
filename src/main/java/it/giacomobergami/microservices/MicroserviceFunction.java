/*
 * MicroserviceFunction.java
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

import it.giacomobergami.simpleschema.IConcept;

import java.util.HashMap;

public class MicroserviceFunction {
    public HashMap<String, IConcept> input_field_function;
    public String microserviceFunctionName;
    public IConcept microserviceResult;

    public MicroserviceFunction(HashMap<String, IConcept> input_field_function, String microserviceFunctionName, IConcept microserviceResult) {
        this.input_field_function = input_field_function;
        this.microserviceFunctionName = microserviceFunctionName;
        this.microserviceResult = microserviceResult;
    }
}
