/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Key;

import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the City class.
 * 
 */

public class CityManager {
	
	private static final Logger log = Logger.getLogger(CityManager.class.getName());
	
	/**
     * Get a City instance from the datastore given the City key.
     * @param key
     * 			: the city's key
     * @return city instance, null if city is not found
     */
	public static City getCity(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		City city;
		try  {
			city = pm.getObjectById(City.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return city;
	}
	
	/**
     * Get all City instances from the datastore.
     * @return All City instances
     * TODO: Make "touching" of citys more efficient
     */
	@SuppressWarnings("unchecked")
	public static List<City> getAllCities() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(City.class);

        List<City> citys = null;
        try {
        	citys = (List<City>) query.execute();
            // touch all elements
            for (City r : citys)
                r.getCityName();
        } finally {
        	pm.close();
            query.closeAll();
        }

        return citys;
    }
	
	/**
     * Put City into datastore.
     * Stores the given city instance in the datastore calling the PersistenceManager's
     * makePersistent() method.
     * @param city
     * 			: the city instance to store
     */
	public static void putCity(City city) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(city);
			tx.commit();
			log.info("City \"" + city.getCityName() + 
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
    * Delete City from datastore.
    * Deletes the city corresponding to the given key
    * from the datastore calling the PersistenceManager's deletePersistent() method.
    * @param key
    * 			: the key of the city instance to delete
    */
	public static void deleteCity(Key key) {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			City city = pm.getObjectById(City.class, key);
			String cityName = city.getCityName();
			tx.begin();
			pm.deletePersistent(city);
			tx.commit();
			log.info("City \"" + cityName + "\" deleted successfully from datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
    * Update City attributes.
    * Update's the given city's attributes in the datastore.
    * @param key
    * 			: the key of the city whose attributes will be updated
    * @param cityName
    * 			: the new name to give to the city
    * @param cityComments
    * 			: the new comments to give to the city
	* @throws MissingRequiredFieldsException 
    */
	public static void updateCityAttributes(
			Key key, 
			String cityName,
			String cityComments) 
					throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			City city = pm.getObjectById(City.class, key);
			tx.begin();
			city.setCityName(cityName);
			city.setCityComments(cityComments);
			tx.commit();
			log.info("City \"" + cityName + "\"'s attributes updated in datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
}
