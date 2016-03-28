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

import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the ServiceType class.
 * 
 */

public class ServiceTypeManager {
	
	private static final Logger log = 
        Logger.getLogger(ServiceTypeManager.class.getName());
	
	/**
     * Get a ServiceType instance from the datastore given the ServiceType key.
     * @param key
     * 			: the ServiceType's key
     * @return ServiceType instance, null if ServiceType is not found
     */
	public static ServiceType getServiceType(Long key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServiceType serviceType;
		try  {
			serviceType = pm.getObjectById(ServiceType.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return serviceType;
	}
	
	/**
     * Get all ServiceType instances from the datastore.
     * @return All ServiceType instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ServiceType> getAllServiceTypes() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(ServiceType.class);

        List<ServiceType> types = null;
        try {
        	types = (List<ServiceType>) query.execute();
            // touch all elements
            for (ServiceType t : types)
                t.getServiceTypeName();
        } 
        finally {
        	pm.close();
            query.closeAll();
        }

        return types;
    }
	
	/**
     * Put ServiceType into datastore.
     * Stations the given ServiceType instance in the datastore calling the 
     * PersistenceManager's makePersistent() method.
     * @param serviceType
     * 			: the ServiceType instance to store
     */
	public static void putServiceType(ServiceType serviceType) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(serviceType);
			tx.commit();
			log.info("ServiceType \"" + serviceType.getServiceTypeName() + 
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
    * Delete ServiceType from datastore.
    * Deletes the ServiceType corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    * 			: the key of the ServiceType instance to delete
    */
	public static void deleteServiceType(Long key) {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ServiceType serviceType = pm.getObjectById(ServiceType.class, key);
			String ServiceTypeName = serviceType.getServiceTypeName();
			tx.begin();
			pm.deletePersistent(serviceType);
			tx.commit();
			log.info("ServiceType \"" + ServiceTypeName + 
                     "\" deleted successfully from datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
    * Update ServiceType attributes.
    * Update's the given ServiceType's attributes in the datastore.
    * @param key
    * 			: the key of the ServiceType whose attributes will be updated
    * @param serviceTypeName
    * 			: the new name to give to the ServiceType
    * @param serviceTypeDescription
    * 			: the new description to give to the ServiceType
    * @param serviceTypeComments
    * 			: the new comments to give to the ServiceType
	* @throws MissingRequiredFieldsException 
    */
	public static void updateServiceTypeAttributes(
			Long key,
    		String serviceTypeName, 
    		String serviceTypeDescription,
    		String serviceTypeComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ServiceType serviceType = pm.getObjectById(ServiceType.class, key);
			tx.begin();
			serviceType.setServiceTypeName(serviceTypeName);
			serviceType.setServiceTypeDescription(serviceTypeDescription);
			serviceType.setServiceTypeComments(serviceTypeComments);
			tx.commit();
			log.info("ServiceType \"" + serviceTypeName + 
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
