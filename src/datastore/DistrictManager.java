/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;

import exceptions.InexistentObjectException;
import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the District class.
 * 
 */

public class DistrictManager {
	
	private static final Logger log = Logger.getLogger(DistrictManager.class.getName());
	
	/**
     * Get a District instance from the datastore given the District key.
     * @param key
     * 			: the district's key
     * @return district instance, null if district is not found
     */
	public static District getDistrict(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		District district;
		try  {
			district = pm.getObjectById(District.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return district;
	}
	
	/**
     * Get all District instances from the datastore.
     * @return All District instances
     * TODO: Make "touching" of districts more efficient
     */
	@SuppressWarnings("unchecked")
	public static List<District> getAllDistricts() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(District.class);

        List<District> districts = null;
        try {
        	districts = (List<District>) query.execute();
            // touch all elements
            for (District r : districts)
                r.getDistrictName();
        } finally {
        	pm.close();
            query.closeAll();
        }

        return districts;
    }
	
	/**
     * Get all District instances from the datastore that belong to
     * this City.
     * @param cityKey
     * @return All District instances that belong to the given City
     * TODO: Inefficient touching of objects
     */
	public static List<District> getAllDistrictsFromCity(
			Key cityKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        City city = pm.getObjectById(City.class, cityKey);
         
        List<District> result = null;
        ArrayList<District> finalResult = new ArrayList<District>();
        try {
            result = city.getDistricts();
            for (District district : result) {
            	district.getDistrictName();
            	finalResult.add(district);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
	/**
     * Put District into datastore.
     * Stores the given District instance in the datastore for this
     * City.
     * @param cityKey
     *          : the key of the City where the District will be added
     * @param district
     *          : the District instance to store
     */
    public static void putDistrict(Key cityKey, District district) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
        	City city = 
                    pm.getObjectById(City.class, cityKey);
            tx.begin();
            city.addDistrict(district);
            tx.commit();
            log.info("District \"" + district.getDistrictName() + 
                "\" stored successfully in datastore.");
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
     
    /**
    * Delete District from datastore.
    * Deletes the District corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the District instance to delete
    */
    public static void deleteDistrict(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
        	City city = 
            		pm.getObjectById(City.class, key.getParent());
            District district = 
            		pm.getObjectById(District.class, key);
            String districtContent = district.getDistrictName();
            tx.begin();
            city.removeDistrict(district);
            tx.commit();
            log.info("District \"" + districtContent + 
                     "\" deleted successfully from datastore.");
        }
        catch (InexistentObjectException e) {
            e.printStackTrace();
        }
        finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

	/**
    * Update District attributes.
    * Update's the given district's attributes in the datastore.
    * @param key
    * 			: the key of the district whose attributes will be updated
    * @param districtName
    * 			: the new name to give to the district
    * @param districtComments
    * 			: the new comments to give to the district
	* @throws MissingRequiredFieldsException 
    */
	public static void updateDistrictAttributes(
			Key key, 
			String districtName,
			String districtComments) 
					throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			District district = pm.getObjectById(District.class, key);
			tx.begin();
			district.setDistrictName(districtName);
			district.setDistrictComments(districtComments);
			tx.commit();
			log.info("District \"" + districtName + "\"'s attributes updated in datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
}
