/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the ServiceType table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@PersistenceCapable
public class ServiceType {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;

    @Persistent
    private String serviceTypeName;
    
    @Persistent
    private String serviceTypeDescription;

    @Persistent
    private String serviceTypeComments;
    
    @Persistent
    private Date serviceTypeCreationDate;
    
    @Persistent
    private Date serviceTypeModificationDate;

    /**
     * ServiceType constructor.
     * @param serviceTypeName
     * 			: ServiceType name
     * @param serviceTypeDescription
     * 			: ServiceType description
     * @param serviceTypeComments
     * 			: ServiceType comments
     * @throws MissingRequiredFieldsException
     */
    public ServiceType(
    		String serviceTypeName, 
    		String serviceTypeDescription,
    		String serviceTypeComments
    		) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (serviceTypeName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (serviceTypeName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

        this.serviceTypeName = serviceTypeName;
        this.serviceTypeDescription = serviceTypeDescription;
        this.serviceTypeComments = serviceTypeComments;
        
        Date now = new Date();
        this.serviceTypeCreationDate = now;
        this.serviceTypeModificationDate = now;
    }

    /**
     * Get ServiceType key.
     * @return ServiceType key
     */
    public Long getKey() {
        return key;
    }
    
    /**
     * Get ServiceType name.
     * @return ServiceType name
     */
    public String getServiceTypeName() {
        return serviceTypeName;
    }

    /**
     * Get ServiceType description.
     * @return ServiceType description
     */
    public String getServiceTypeDescription() {
    	return serviceTypeDescription;
    }

	/**
     * Get ServiceType comments.
     * @return ServiceType comments
     */
    public String getServiceTypeComments() {
    	return serviceTypeComments;
    }

	/**
     * Get ServiceType creation date.
     * @return ServiceType creation date
     */
    public Date getServiceTypeCreationDate() {
    	return serviceTypeCreationDate;
    }
    
    /**
     * Get ServiceType modification date.
     * @return ServiceType modification date
     */
    public Date getServiceTypeModificationDate() {
    	return serviceTypeModificationDate;
    }
    
    /**
     * Set ServiceType name.
     * @param serviceTypeName
     * 			: ServiceType name
     * @throws MissingRequiredFieldsException
     */
    public void setServiceTypeName(
    		String serviceTypeName)
    		throws MissingRequiredFieldsException {
    	if (serviceTypeName == null || 
    			serviceTypeName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"ServiceType name is missing.");
    	}
    	this.serviceTypeName = serviceTypeName;
    	this.serviceTypeModificationDate = new Date();
    }
    
    /**
     * Set ServiceType description.
     * @param serviceTypeDescription
     * 			: ServiceType description
     */
    public void setServiceTypeDescription(
    		String serviceTypeDescription) {
    	this.serviceTypeDescription = 
    			serviceTypeDescription;
    	this.serviceTypeModificationDate = new Date();
    }
    
    /**
     * Set ServiceType comments.
     * @param serviceTypeComments
     * 			: ServiceType comments
     */
    public void setServiceTypeComments(
    		String serviceTypeComments) {
    	this.serviceTypeComments = serviceTypeComments;
    	this.serviceTypeModificationDate = new Date();
    }
}