package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.*;

/**
 * Default implementation for interactor pool
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the InteractorPool object is a complex object.
 * To compare InteractorPool objects, you can use some comparators provided by default:
 * - DefaultInteractorPoolComparator
 * - UnambiguousInteractorPoolComparator
 * - DefaultExactInteractorPoolComparator
 * - UnambiguousExactInteractorPoolComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteractorPool extends DefaultInteractor implements InteractorPool {

    private Collection<Interactor> interactors;

    public DefaultInteractorPool(String name, CvTerm type) {
        super(name, type != null ? type : CvTermUtils.createMoleculeSetType());
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, CvTerm type) {
        super(name, fullName, type != null ? type : CvTermUtils.createMoleculeSetType());
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, CvTerm type, Organism organism) {
        super(name, type != null ? type : CvTermUtils.createMoleculeSetType(), organism);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type != null ? type : CvTermUtils.createMoleculeSetType(), organism);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, CvTerm type, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createMoleculeSetType(), uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createMoleculeSetType(), uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type != null ? type : CvTermUtils.createMoleculeSetType(), organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type != null ? type : CvTermUtils.createMoleculeSetType(), organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name) {
        super(name, CvTermUtils.createMoleculeSetType());
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName) {
        super(name, fullName, CvTermUtils.createMoleculeSetType());
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, Organism organism) {
        super(name, CvTermUtils.createMoleculeSetType(), organism);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermUtils.createMoleculeSetType(), organism);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, Xref uniqueId) {
        super(name, CvTermUtils.createMoleculeSetType(), uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createMoleculeSetType(), uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermUtils.createMoleculeSetType(), organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public DefaultInteractorPool(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermUtils.createMoleculeSetType(), organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    protected void initialiseInteractorCandidatesSet(){
        this.interactors = new ArrayList<Interactor>();
    }

    protected void initialiseInteractorCandidatesSetWith(Set<Interactor> interactorCandidates){
        if (interactorCandidates == null){
            this.interactors = Collections.EMPTY_LIST;
        }
        else {
            this.interactors = interactorCandidates;
        }
    }

    @Override
    /**
     * Sets the interactor type.
     * Sets the type to molecule set (MI:1304) if the given type is null
     */
    public void setInteractorType(CvTerm type) {
        if (type == null){
            super.setInteractorType(CvTermUtils.createMoleculeSetType());
        }
        else {
            super.setInteractorType(type);
        }
    }

    public int size() {
        return interactors.size();
    }

    public boolean isEmpty() {
        return interactors.isEmpty();
    }

    public boolean contains(Object o) {
        return interactors.contains(o);
    }

    public Iterator<Interactor> iterator() {
        return interactors.iterator();
    }

    public Object[] toArray() {
        return interactors.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return interactors.toArray(ts);
    }

    public boolean add(Interactor interactor) {
        return interactors.add(interactor);
    }

    public boolean remove(Object o) {
        return interactors.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return interactors.containsAll(objects);
    }

    public boolean addAll(Collection<? extends Interactor> interactors) {
        return this.interactors.addAll(interactors);
    }

    public boolean retainAll(Collection<?> objects) {
        return interactors.retainAll(objects);
    }

    public boolean removeAll(Collection<?> objects) {
        return interactors.removeAll(objects);
    }

    public void clear() {
        interactors.clear();
    }
}
