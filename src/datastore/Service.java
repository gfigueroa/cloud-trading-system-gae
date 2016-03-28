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
 * This class represents the Service table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class Service implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long serviceType;
	
	@Persistent
	private Key customerKey;
    
    @Persistent
    private String serviceDescription;
    
    @Persistent
    private String serviceComments;
    
    @Persistent
    private Date serviceCreationDate;
    
    @Persistent
    private Date  serviceModificationDate;

    /**
     * Service constructor.
     * @param serviceType
     * 			: ServiceType key
     * @param customerKey
     * 			: Customer key
     * @param serviceDescription
     * 			: service description
     * @param serviceComments
     * 			: service comments
     * @throws MissingRequiredFieldsException
     */
    public Service(
    		Long serviceType,
    		Key customerKey,
    		String serviceDescription, 
    		String serviceComments) 
		throws MissingRequiredFieldsException {
        
    	// Check "required field" constraints
    	if (serviceType == null || customerKey == null || 
    			serviceDescription == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
    	if (serviceDescription.isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

    	this.serviceType = serviceType;
    	this.customerKey = customerKey;
    	this.serviceDescription = serviceDescription;
    	this.serviceComments = serviceComments;
    	
    	Date now = new Date();
    	this.serviceCreationDate = now;
    	this.serviceModificationDate = now;
    }

	/**
     * Get Service key.
     * @return service key
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
	 * @return the serviceDescription
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}

	/**
	 * @return the serviceComments
	 */
	public String getServiceComments() {
		return serviceComments;
	}

    /**
     * Get service creation date.
     * @return the time this service was created
     */
    public Date getServiceCreationDate() {
        return serviceCreationDate;
    }

    /**
     * Get service modification date.
     * @return the time this service was last modified
     */
    public Date getServiceModificationDate() {
        return serviceModificationDate;
    }
    
    /**
     * Compare this service with another service
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this Service, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof Service ) ) return false;
        Service service = (Service) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(service.getKey()));
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
		this.serviceModificationDate = new Date();
	}
    
    /**
	 * @param serviceDescription the serviceDescription to set
     * @throws MissingRequiredFieldsException 
	 */
	public void setServiceDescription(String serviceDescription) 
			throws MissingRequiredFieldsException {
		if (serviceDescription == null || serviceDescription.isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Service description is missing.");
    	}
		this.serviceDescription = serviceDescription;
		this.serviceModificationDate = new Date();
	}

	/**
	 * @param serviceComments the serviceComments to set
	 */
	public void setServiceComments(String serviceComments) {
		this.serviceComments = serviceComments;
		this.serviceModificationDate = new Date();
	}
}