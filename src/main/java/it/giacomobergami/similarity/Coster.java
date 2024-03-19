/*
 * Coster.java
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

 package it.giacomobergami.similarity;

import it.giacomobergami.simpleschema.ConceptProperty;
import it.giacomobergami.simpleschema.NativeTypes;

public class Coster {

    private IEmbedding weightFunction;

    public Coster(IEmbedding weightFunction) {
        this.weightFunction = weightFunction;
    }

    public double getDoubleWeight(String src, String dst) {
        return  weightFunction.normalised_distance(src, dst);
    }

    public double getDoubleWeight(String src, NativeTypes srcType, String dst, NativeTypes dstType) {
        double semanticCost = getDoubleWeight(src, dst);
        double issueFactor = NativeTypes.isSubsetOf(srcType, dstType);
        return semanticCost * issueFactor;
    }

    public int getInt100Weight(ConceptProperty src, ConceptProperty dst) {
        double semanticCost = weightFunction.normalised_distance(src.getSemanticName(), dst.getSemanticName());
        double issueFactor = NativeTypes.isSubsetOf(src.getType(), dst.getType());
        return ((int)(semanticCost * issueFactor*100.0));
    }
}
