/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;

import exceptions.InexistentObjectException;
import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the Village class.
 * 
 */

public class VillageManager {
	
	private static final Logger log = 
        Logger.getLogger(VillageManager.class.getName());
	
	/**
     * Get a Village instance from the datastore given the Village key.
     * @param key
     * 			: the Village's key
     * @return Village instance, null if Village is not found
     */
	public static Village getVillage(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Village village;
		try  {
			village = pm.getObjectById(Village.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return village;
	}
	
	/**
     * Get all Village instances from the datastore.
     * @return All Village instances
     * TODO: Make "touching" of villages more efficient
     */
	@SuppressWarnings("unchecked")
	public static List<Village> getAllVillages() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(Village.class);

        List<Village> villages = null;
        try {
        	villages = (List<Village>) query.execute();
            // touch all elements
            for (Village r : villages)
                r.getVillageName();
        } finally {
        	pm.close();
            query.closeAll();
        }

        return villages;
    }
	
	/**
     * Get all Village instances from the datastore that belong to
     * this District.
     * @param districtKey
     * @return All Village instances that belong to the given District
     * TODO: Inefficient touching of objects
     */
	public static List<Village> getAllVillagesFromDistrict(
			Key districtKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        District district = pm.getObjectById(District.class, districtKey);
         
        List<Village> result = null;
        ArrayList<Village> finalResult = new ArrayList<Village>();
        try {
            result = district.getVillages();
            for (Village village : result) {
            	village.getVillageName();
            	finalResult.add(village);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
    /**
     * Put Village into datastore.
     * Stores the given Village instance in the datastore for this
     * district.
     * @param districtKey
     *          : the key of the District where the village will be added
     * @param village
     *          : the Village instance to district
     */
    public static void putVillage(Key districtKey, Village village) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            District district = 
                    pm.getObjectById(District.class, districtKey);
            tx.begin();
            district.addVillage(village);
            tx.commit();
            log.info("Village \"" + village.getVillageName() + 
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
    * Delete Village from datastore.
    * Deletes the Village corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the Village instance to delete
    */
    public static void deleteVillage(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            District district = 
            		pm.getObjectById(District.class, key.getParent());
            Village village = 
            		pm.getObjectById(Village.class, key);
            String villageContent = village.getVillageName();
            tx.begin();
            district.removeVillage(village);
            tx.commit();
            log.info("Village \"" + villageContent + 
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
    * Update Village attributes.
    * Update's the given Village's attributes in the datastore.
    * @param key
    * 			: the key of the Village whose attributes will be updated
    * @param villageName
    * 			: the new name to give to the Village
    * @param villageComments
    * 			: the new comments to give to the Village
	* @throws MissingRequiredFieldsException 
    */
	public static void updateVillageAttributes(Key key,
			String villageName,
			String villageComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			Village village = pm.getObjectById(Village.class, key);
			tx.begin();
			village.setVillageName(villageName);
			village.setVillageComments(villageComments);
			tx.commit();
			log.info("Village \"" + villageName + 
                     "\"'s attributes updated in datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
}
