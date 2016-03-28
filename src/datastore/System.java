/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the System table.
 * The System table stores some version numbers required by the Mobile App to download
 * data from the web server.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class System implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;

    @Persistent
    private Integer customerListVersion;
    
    @Persistent
    private Date customerListTimestamp;
    
    @Persistent
    private Integer itemCategoryListVersion;
    
    @Persistent
    private Date itemCategoryListTimestamp;
    
    @Persistent
    private Integer itemTypeListVersion;
    
    @Persistent
    private Date itemTypeListTimestamp;
    
    @Persistent
    private Integer serviceTypeListVersion;
    
    @Persistent
    private Date serviceTypeListTimestamp;
    
    @Persistent
    private Integer oldestAppVersionSupported1;
    
    @Persistent
    private Integer oldestAppVersionSupported2;
    
    @Persistent
    private Integer oldestAppVersionSupported3;
    
    @Persistent
    private Date systemTime;

    /**
     * System constructor.
     */
    public System() {
    	Date now = new Date();
    	
    	customerListVersion = 0;
    	customerListTimestamp = now;
    	itemCategoryListVersion = 0;
    	itemCategoryListTimestamp = now;
    	itemTypeListVersion = 0;
    	itemTypeListTimestamp = now;
    	serviceTypeListVersion = 0;
    	serviceTypeListTimestamp = now;
    	
    	oldestAppVersionSupported1 = 1;
    	oldestAppVersionSupported2 = 0;
    	oldestAppVersionSupported3 = 0;

    	this.systemTime = now;
    }

    /**
     * Get System key.
     * @return system key
     */
    public Long getKey() {
        return key;
    }

    /**
     * Get Customer List Version.
     * @return customer list version
     */
    public Integer getCustomerListVersion() {
        return customerListVersion;
    }
    
    /**
     * Get ItemCategory List Version.
     * @return ItemCategory list version
     */
    public Integer getItemCategoryListVersion() {
        return itemCategoryListVersion;
    }
    
    /**
     * Get ItemType List version.
     * @return ItemType version
     */
    public Integer getItemTypeListVersion() {
    	return itemTypeListVersion;
    }
    
    /**
	 * @return the serviceTypeListVersion
	 */
	public Integer getServiceTypeListVersion() {
		return serviceTypeListVersion;
	}

	/**
     * Get oldest mobile app version supported by this
     * server version.
     * @return oldest app version supported
     */
    public String getOldestAppVersionSupportedString() {
        if (oldestAppVersionSupported1 == null ||
        		oldestAppVersionSupported2 == null ||
        		oldestAppVersionSupported3 == null) {
        	return "";
        }
        else {
	    	return oldestAppVersionSupported1 + "." + 
	        		oldestAppVersionSupported2 + "." + 
	        		oldestAppVersionSupported3;
        }
    }
    
    /**
     * Get first digit of oldest mobile app version supported 
     * by this server version.
     * @return first digit of oldest app version supported
     */
    public Integer getOldestAppVersionSupported1() {
        return oldestAppVersionSupported1;
    }
    
    /**
     * Get second digit of oldest mobile app version supported 
     * by this server version.
     * @return second digit of oldest app version supported
     */
    public Integer getOldestAppVersionSupported2() {
        return oldestAppVersionSupported2;
    }
    
    /**
     * Get third digit of oldest mobile app version supported 
     * by this server version.
     * @return third digit of oldest app version supported
     */
    public Integer getOldestAppVersionSupported3() {
        return oldestAppVersionSupported3;
    }
    
    /**
     * Get time when last update was made.
     * @return system time
     */
    public Date getSystemTime() {
        return systemTime;
    }
    
    /**
     * Compare this system instance with another syste,
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this System, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof System ) ) return false;
        System system = (System) o;
        return (this.getKey() == system.getKey());
    }
    
    /**
     * Update the Customer List Version number by 1.
     */
    public void updateCustomerListVersion() {
    	customerListVersion++;
    	systemTime = new Date();
    }
    
    /**
     * Update the ItemCategory List Version number by 1.
     */
    public void updateItemCategoryListVersion() {
    	itemCategoryListVersion++;
    	systemTime = new Date();
    }
    
    /**
     * Update the ItemType List version number by 1.
     */
    public void updateItemTypeListVersion() {
    	itemTypeListVersion++;
    	systemTime = new Date();
    }
    
    /**
     * Update the ServiceType List version number by 1.
     */
    public void updateServiceTypeListVersion() {
    	serviceTypeListVersion++;
    	systemTime = new Date();
    }
    
    /**
     * Set first digit of the 
     * oldest app version supported by this server version.
     * @param oldestAppVersionSupported1
     * 			: the first digit of the oldest app version 
     * 			  supported by this server
     * @throws MissingRequiredFieldsException 
     */
    public void setOldestAppVersionSupported1(Integer oldestAppVersionSupported1) 
    		throws MissingRequiredFieldsException {
    	// Check required field constraint
    	if (oldestAppVersionSupported1 == null) {
    		throw new MissingRequiredFieldsException(
    				this, "Missing oldest app version supported digit 1.");
    	}
    	this.oldestAppVersionSupported1 = oldestAppVersionSupported1;
    	systemTime = new Date();
    }
    
    /**
     * Set second digit of the 
     * oldest app version supported by this server version.
     * @param oldestAppVersionSupported2
     * 			: the second digit of the oldest app version 
     * 			  supported by this server
     * @throws MissingRequiredFieldsException 
     */
    public void setOldestAppVersionSupported2(Integer oldestAppVersionSupported2) 
    		throws MissingRequiredFieldsException {
    	// Check required field constraint
    	if (oldestAppVersionSupported2 == null) {
    		throw new MissingRequiredFieldsException(
    				this, "Missing oldest app version supported digit 2.");
    	}
    	this.oldestAppVersionSupported2 = oldestAppVersionSupported2;
    	systemTime = new Date();
    }
    
    /**
     * Set third digit of the 
     * oldest app version supported by this server version.
     * @param oldestAppVersionSupported3
     * 			: the third digit of the oldest app version 
     * 			  supported by this server
     * @throws MissingRequiredFieldsException 
     */
    public void setOldestAppVersionSupported3(Integer oldestAppVersionSupported3) 
    		throws MissingRequiredFieldsException {
    	// Check required field constraint
    	if (oldestAppVersionSupported3 == null) {
    		throw new MissingRequiredFieldsException(
    				this, "Missing oldest app version supported digit 3.");
    	}
    	this.oldestAppVersionSupported3 = oldestAppVersionSupported3;
    	systemTime = new Date();
    }
    
}