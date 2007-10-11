/**
 * Delta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta Mar 31, 2004 (12:47:03 EST) WSDL2Java emitter.
 */

package uk.ac.bbk.dcs.l4all.etools.delta.ws;

public interface Delta extends java.rmi.Remote {
    public java.lang.Object[] searchByWizard(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.Object[] searchByWizard() throws java.rmi.RemoteException;
    public java.lang.Object[] freeSearch(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public java.lang.Object[] advancedSearch(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, double in5) throws java.rmi.RemoteException;
    public boolean createELearnResource(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5, java.lang.String in6, java.lang.String in7, java.lang.String in8) throws java.rmi.RemoteException;
    public boolean editELearnResource(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public boolean deleteELearnResource(java.lang.String in0) throws java.rmi.RemoteException;
    public boolean createMember(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public boolean editMember(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public boolean deleteMember(java.lang.String in0) throws java.rmi.RemoteException;
    public boolean createAnnotation(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException;
    public boolean addFavorite(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public boolean deleteFavorite(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String getPedagogicalDefinition(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.Object[] getAnnotationsByResource(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.Object getMemberDetails(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.Object[] listMostRecentDC() throws java.rmi.RemoteException;
    public java.lang.Object[] listMostRecentLOMMETA() throws java.rmi.RemoteException;
    public java.lang.Object[] listHighestRate() throws java.rmi.RemoteException;
    public java.lang.Object[] listMemberResources(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.Object[] listFavorite(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String login(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public double addRating(java.lang.String in0, int in1) throws java.rmi.RemoteException;
    public java.lang.String getPassword(java.lang.String in0) throws java.rmi.RemoteException;
}
