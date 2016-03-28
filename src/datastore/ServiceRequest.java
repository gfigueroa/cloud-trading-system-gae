/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the ServiceRequest table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class ServiceRequest implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long serviceType;
	
	@Persistent
	private Key customerKey;
    
    @Persistent
    private String serviceRequestDescription;
    
    @Persistent
    private String serviceRequestComments;
    
    @Persistent
    private Date serviceRequestCreationDate;
    
    @Persistent
    private Date  serviceRequestModificationDate;

    /**
     * ServiceRequest constructor.
     * @param serviceRequestType
     * 			: ServiceRequestType key
     * @param customerKey
     * 			: Customer key
     * @param serviceRequestDescription
     * 			: serviceRequest description
     * @param serviceRequestComments
     * 			: serviceRequest comments
     * @throws MissingRequiredFieldsException
     */
    public ServiceRequest(
    		Long serviceType,
    		Key customerKey,
    		String serviceRequestDescription, 
    		String serviceRequestComments) 
		throws MissingRequiredFieldsException {
        
    	// Check "required field" constraints
    	if (serviceType == null ||
    			customerKey == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
    	if (serviceRequestDescription.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

    	this.serviceType = serviceType;
    	this.customerKey = customerKey;
    	this.serviceRequestDescription = serviceRequestDescription;
    	this.serviceRequestComments = serviceRequestComments;
    	
    	Date now = new Date();
    	this.serviceRequestCreationDate = now;
    	this.serviceRequestModificationDate = now;
    }

	/**
     * Get ServiceRequest key.
     * @return serviceRequest key
     */
    public Key getKey() {
        return key;
    }
    
	/**
	 * @return the serviceType
	 */
	public Long getServiceType() {
		return serviceType;
	}
    
    /**
     * Get Customer key
     * @return customerKey
     */
    public Key getCustomerKey() {
    	return customerKey;
    }
    
    /**
	 * @return the serviceRequestDescription
	 */
	public String getServiceRequestDescription() {
		return serviceRequestDescription;
	}

	/**
	 * @return the serviceRequestComments
	 */
	public String getServiceRequestComments() {
		return serviceRequestComments;
	}

    /**
     * Get serviceRequest creation date.
     * @return the time this serviceRequest was created
     */
    public Date getServiceRequestCreationDate() {
        return serviceRequestCreationDate;
    }

    /**
     * Get serviceRequest modification date.
     * @return the time this serviceRequest was last modified
     */
    public Date getServiceRequestModificationDate() {
        return serviceRequestModificationDate;
    }
    
    /**
     * Compare this serviceRequest with another serviceRequest
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this ServiceRequest, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof ServiceRequest ) ) return false;
        ServiceRequest serviceRequest = (ServiceRequest) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(serviceRequest.getKey()));
    }
    
	/**
	 * @param serviceType the serviceType to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setServiceType(Long serviceType) 
			throws MissingRequiredFieldsException {
		if (serviceType == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Service type is missing.");
    	}
		this.serviceType = serviceType;
		this.serviceRequestModificationDate = new Date();
	}
    
    /**
	 * @param serviceRequestDescription the serviceRequestDescription to set
     * @throws MissingRequiredFieldsException 
	 */
	public void setServiceRequestDescription(String serviceRequestDescription) 
			throws MissingRequiredFieldsException {
		if (serviceRequestDescription == null || serviceRequestDescription.isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"ServiceRequest Description is missing.");
    	}
		this.serviceRequestDescription = serviceRequestDescription;
		this.serviceRequestModificationDate = new Date();
	}

	/**
	 * @param serviceRequestComments the serviceRequestComments to set
	 */
	public void setServiceRequestComments(String serviceRequestComments) {
		this.serviceRequestComments = serviceRequestComments;
		this.serviceRequestModificationDate = new Date();
	}
}