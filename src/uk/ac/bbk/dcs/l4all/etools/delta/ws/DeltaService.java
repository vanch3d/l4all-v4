/**
 * DeltaService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta Mar 31, 2004 (12:47:03 EST) WSDL2Java emitter.
 */

package uk.ac.bbk.dcs.l4all.etools.delta.ws;

public interface DeltaService extends javax.xml.rpc.Service {
    public java.lang.String getdeltaAddress();

    public uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta getdelta() throws javax.xml.rpc.ServiceException;

    public uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta getdelta(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
