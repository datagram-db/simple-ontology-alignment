package it.giacomobergami.simpleschema;

import java.util.*;

public class Concept extends ConceptProperty {
    public final Map<String, IConcept> mapping;
    public final Set<String> listProperties;
    public Concept(Ontology ontology, String key) {
        super(ontology, key, NativeTypes.CONCEPT);
        mapping = new HashMap<>();
        listProperties = new HashSet<>();
    }

    @Override
    public Optional<Concept> asConcept() {
        return Optional.of(this);
    }

    public void collectConcepts(Set<String> concepts) {
        if (concepts.contains(getKey())) {
            return;
        }
        concepts.add(getKey());
        for (IConcept var : mapping.values()) {
            if (var instanceof Concept) {
                ((Concept)var).collectConcepts(concepts);
            }
        }
    }

    public void addListProperty(String name) {
        listProperties.add(name);
    }

    public void addProperty(String name, IConcept prop) {
        mapping.put(name, prop);
    }

    @Override
    public boolean isBasic() {
        return false;
    }

    public int countConceptNodes(Set<String> visited) {
        if (visited.contains(getKey())) {
            return 0;
        } else {
            visited.add(getKey());
        }
        int count = 2;
        for (IConcept concept : mapping.values())
            count += concept.countConceptNodes(visited);
        return count;
    }

    @Override
    public Set<Map.Entry<String, IConcept>> components() {
        return mapping.entrySet();
    }

    @Override
    public Collection<? extends IConcept> components2() {
        return new Set<IConcept>() {
            @Override
            public int size() {
                return mapping.size();
            }

            @Override
            public boolean isEmpty() {
                return mapping.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                if (o instanceof String) {
                    return mapping.containsKey(o);
                } else if (o instanceof IConcept) {
                    return mapping.containsValue(o);
                }
                return false;
            }

            @Override
            public Iterator<IConcept> iterator() {
                return new Iterator<>() {
                    Iterator<Map.Entry<String, IConcept>> it = mapping.entrySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public IConcept next() {
                        return new IteratorConceptProperty(getAncestor(), it.next());
                    }
                };
            }

            @Override
            public Object[] toArray() {
                List<IConcept> result = new ArrayList<>(mapping.size());
                var it = iterator();
                while (it.hasNext())
                    result.add(it.next());
                return result.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a) {
                List<IConcept> result = new ArrayList<>(mapping.size());
                var it = iterator();
                while (it.hasNext())
                    result.add(it.next());
                return result.toArray(a);
            }

            @Override
            public boolean add(IConcept iConcept) {
                throw new RuntimeException("add: CANNOT MUTATE!");
            }

            @Override
            public boolean remove(Object o) {
                throw new RuntimeException("remove: CANNOT MUTATE!");
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                for (var x : c)
                    if (!contains(x))
                        return false;
                return true;
            }

            @Override
            public boolean addAll(Collection<? extends IConcept> c) {
                throw new RuntimeException("addAll: CANNOT MUTATE!");
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                throw new RuntimeException("retainAll: CANNOT MUTATE!");
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                throw new RuntimeException("removeAll: CANNOT MUTATE!");
            }

            @Override
            public void clear() {
                throw new RuntimeException("Clear: CANNOT MUTATE!");
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Concept concept = (Concept) o;
        Set<String> ccL = new HashSet<>();
        Set<String> ccR = new HashSet<>();
        collectConcepts(ccL);
        concept.collectConcepts(ccR);
        return Objects.equals(ccL, ccR) && Objects.equals(listProperties, concept.listProperties);
    }

    @Override
    public int hashCode() {
        Set<String> ccL = new HashSet<>();
        collectConcepts(ccL);
        return Objects.hash(super.hashCode(), ccL, listProperties);
    }
}
