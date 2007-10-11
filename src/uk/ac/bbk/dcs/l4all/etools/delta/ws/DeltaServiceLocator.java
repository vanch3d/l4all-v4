/**
 * DeltaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2beta Mar 31, 2004 (12:47:03 EST) WSDL2Java emitter.
 */

package uk.ac.bbk.dcs.l4all.etools.delta.ws;

public class DeltaServiceLocator extends org.apache.axis.client.Service implements uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaService {

    // Use to get a proxy class for delta
//    private java.lang.String delta_address = "http://chimeraweb.essex.ac.uk:8000/DELTA/services/delta";
	private java.lang.String delta_address = "http://193.61.44.102:8000/DELTA/services/delta";

    public java.lang.String getdeltaAddress() {
        return delta_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String deltaWSDDServiceName = "delta";

    public java.lang.String getdeltaWSDDServiceName() {
        return deltaWSDDServiceName;
    }

    public void setdeltaWSDDServiceName(java.lang.String name) {
        deltaWSDDServiceName = name;
    }

    public uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta getdelta() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(delta_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getdelta(endpoint);
    }

    public uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta getdelta(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaSoapBindingStub _stub = new uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaSoapBindingStub(portAddress, this);
            _stub.setPortName(getdeltaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setdeltaEndpointAddress(java.lang.String address) {
        delta_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (uk.ac.bbk.dcs.l4all.etools.delta.ws.Delta.class.isAssignableFrom(serviceEndpointInterface)) {
            	uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaSoapBindingStub _stub = new uk.ac.bbk.dcs.l4all.etools.delta.ws.DeltaSoapBindingStub(new java.net.URL(delta_address), this);
                _stub.setPortName(getdeltaWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("delta".equals(inputPortName)) {
            return getdelta();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:delta", "DeltaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("delta"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("delta".equals(portName)) {
            setdeltaEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
