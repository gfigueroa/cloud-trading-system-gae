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
 * This class represents the City table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@PersistenceCapable
public class City {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String cityName;
    
    @Persistent
    private String cityComments;
    
    @Persistent
    private Date cityCreationTime;

	@Persistent
    private Date cityModificationTime;
	
    @Persistent
    @Element(dependent = "true")
    private ArrayList<District> districts;

    /**
     * City constructor.
     * @param cityName
     * 			: city name
     * @param cityComments
     * 			: city comments
     * @throws MissingRequiredFieldsException
     */
    public City(String cityName, 
    		String cityComments) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (cityName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (cityName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
        this.cityName = cityName;
        this.cityComments = cityComments;
        
        Date now = new Date();
        this.cityCreationTime = now;
        this.cityModificationTime = now;
        
        this.districts = new ArrayList<>();
    }

    /**
     * Get City key.
     * @return city key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Get City name.
     * @return restaurant city name
     */
    public String getCityName() {
        return cityName;
    }

	/**
     * Get City comments.
     * @return restaurant city comments
     */
    public String getCityComments() {
    	return cityComments;
    }
    
    /**
	 * @return the cityCreationTime
	 */
	public Date getCityCreationTime() {
		return cityCreationTime;
	}

	/**
	 * @return the cityModificationTime
	 */
	public Date getCityModificationTime() {
		return cityModificationTime;
	}
	
    /**
     * Get Districts
     * @return districts
     */
    public ArrayList<District> getDistricts() {
    	return districts;
    }
    
    /**
     * Set City name.
     * @param cityName
     * 			: city name
     * @throws MissingRequiredFieldsException
     */
    public void setCityName(String cityName)
    		throws MissingRequiredFieldsException {
    	if (cityName == null || cityName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"City name is missing.");
    	}
    	this.cityName = cityName;
        this.cityModificationTime = new Date();
    }
    
    /**
     * Set City comments.
     * @param cityComments
     * 			: city comments
     */
    public void setCityComments(String cityComments) {
    	this.cityComments = cityComments;
    	this.cityModificationTime = new Date();
    }
    
	/**
	 * Add a district to this DeviceModel
	 * @param district
	 */
	public void addDistrict(District district) {
		districts.add(district);
	}
	
    /**
     * Remove a District from the DeviceModel.
     * @param district
     * 			: District to be removed
     * @throws InexistentObjectException
     */
    public void removeDistrict(District district) 
    		throws InexistentObjectException {
    	if (!districts.remove(district)) {
    		throw new InexistentObjectException(
    				District.class, "District not found!");
    	}
    }
}