package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

/**
 * Xml implementation for InteractorSet
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlInteractorPool extends XmlInteractor implements InteractorPool {

    private Collection<Interactor> interactors;

    public XmlInteractorPool(){

    }
    public XmlInteractorPool(String name, CvTerm type) {
        super(name, type);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name) {
        super(name);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName) {
        super(name, fullName);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, Organism organism) {
        super(name, organism);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, Xref uniqueId) {
        super(name, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    public XmlInteractorPool(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
        initialiseInteractorCandidatesSet();
    }

    protected void initialiseInteractorCandidatesSet(){
        this.interactors = new HashSet<Interactor>();
    }

    protected void initialiseInteractorCandidatesSetWith(Set<Interactor> interactorCandidates){
        if (interactorCandidates == null){
            this.interactors = Collections.EMPTY_SET;
        }
        else {
            this.interactors = interactorCandidates;
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

    @Override
    public String toString() {
        return (getSourceLocator() != null ? "Interactor set: "+getSourceLocator().toString():super.toString());
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(InteractorPool.MOLECULE_SET, InteractorPool.MOLECULE_SET_MI));
    }
}

