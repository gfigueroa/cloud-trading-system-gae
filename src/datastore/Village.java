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
 * This class represents the Village table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class Village implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String villageName;
    
    @Persistent
    private String villageComments;
    
    @Persistent
    private Date villageCreationTime;

	@Persistent
    private Date villageModificationTime;

    /**
     * Village constructor.
     * @param villageName
     * 			: village name
     * @param villageComments
     * 			: village comments
     * @throws MissingRequiredFieldsException
     */
    public Village(String villageName, 
    		String villageComments) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (villageName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (villageName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
        this.villageName = villageName;
        this.villageComments = villageComments;
        
        Date now = new Date();
        this.villageCreationTime = now;
        this.villageModificationTime = now;
    }

    /**
     * Get Village key.
     * @return village key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Get Village name.
     * @return restaurant village name
     */
    public String getVillageName() {
        return villageName;
    }

	/**
     * Get Village comments.
     * @return restaurant village comments
     */
    public String getVillageComments() {
    	return villageComments;
    }
    
    /**
	 * @return the villageCreationTime
	 */
	public Date getVillageCreationTime() {
		return villageCreationTime;
	}

	/**
	 * @return the villageModificationTime
	 */
	public Date getVillageModificationTime() {
		return villageModificationTime;
	}
	
	/**
     * Compare this Village with another DVillage
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this Village, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if ( !(o instanceof Village ) ) return false;
        Village r = (Village) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(r.getKey()));
    }
    
    /**
     * Set Village name.
     * @param villageName
     * 			: village name
     * @throws MissingRequiredFieldsException
     */
    public void setVillageName(String villageName)
    		throws MissingRequiredFieldsException {
    	if (villageName == null || villageName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Village name is missing.");
    	}
    	this.villageName = villageName;
        this.villageModificationTime = new Date();
    }
    
    /**
     * Set Village comments.
     * @param villageComments
     * 			: village comments
     */
    public void setVillageComments(String villageComments) {
    	this.villageComments = villageComments;
    	this.villageModificationTime = new Date();
    }
}