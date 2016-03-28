/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

import exceptions.InexistentObjectException;
import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the District table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@PersistenceCapable
public class District {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String districtName;
    
    @Persistent
    private String districtComments;
    
    @Persistent
    private Date districtCreationTime;

	@Persistent
    private Date districtModificationTime;
	
    @Persistent
    @Element(dependent = "true")
    private ArrayList<Village> villages;

    /**
     * District constructor.
     * @param districtName
     * 			: district name
     * @param districtComments
     * 			: district comments
     * @throws MissingRequiredFieldsException
     */
    public District(String districtName, 
    		String districtComments) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (districtName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (districtName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
        this.districtName = districtName;
        this.districtComments = districtComments;
        
        Date now = new Date();
        this.districtCreationTime = now;
        this.districtModificationTime = now;
        
        this.villages = new ArrayList<>();
    }

    /**
     * Get District key.
     * @return district key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Get District name.
     * @return restaurant district name
     */
    public String getDistrictName() {
        return districtName;
    }

	/**
     * Get District comments.
     * @return restaurant district comments
     */
    public String getDistrictComments() {
    	return districtComments;
    }
    
    /**
	 * @return the districtCreationTime
	 */
	public Date getDistrictCreationTime() {
		return districtCreationTime;
	}

	/**
	 * @return the districtModificationTime
	 */
	public Date getDistrictModificationTime() {
		return districtModificationTime;
	}
	
    /**
     * Get Villages
     * @return villages
     */
    public ArrayList<Village> getVillages() {
    	return villages;
    }
    
    /**
     * Set District name.
     * @param districtName
     * 			: district name
     * @throws MissingRequiredFieldsException
     */
    public void setDistrictName(String districtName)
    		throws MissingRequiredFieldsException {
    	if (districtName == null || districtName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"District name is missing.");
    	}
    	this.districtName = districtName;
        this.districtModificationTime = new Date();
    }
    
    /**
     * Set District comments.
     * @param districtComments
     * 			: district comments
     */
    public void setDistrictComments(String districtComments) {
    	this.districtComments = districtComments;
    	this.districtModificationTime = new Date();
    }
    
	/**
	 * Add a village to this DeviceModel
	 * @param village
	 */
	public void addVillage(Village village) {
		villages.add(village);
	}
	
    /**
     * Remove a Village from the DeviceModel.
     * @param village
     * 			: Village to be removed
     * @throws InexistentObjectException
     */
    public void removeVillage(Village village) 
    		throws InexistentObjectException {
    	if (!villages.remove(village)) {
    		throw new InexistentObjectException(
    				Village.class, "Village not found!");
    	}
    }
}