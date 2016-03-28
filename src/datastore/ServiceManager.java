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
 * made on the Service class.
 * 
 */

public class ServiceManager {
	
	private static final Logger log = 
        Logger.getLogger(ServiceManager.class.getName());
	
	/**
     * Get a Service instance from the datastore given the Service key.
     * @param key
     * 			: the Service's key
     * @return Service instance, null if Service is not found
     */
	public static Service getService(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Service service;
		try  {
			service = pm.getObjectById(Service.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return service;
	}
	
	/**
     * Get all Service instances from the datastore that belong to
     * this Customer.
     * @param customerKey
     * @return All Service instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	public static List<Service> getAllServicesFromCustomer(
			Key customerKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Customer customer = 
        		pm.getObjectById(Customer.class, customerKey);
         
        List<Service> result = null;
        ArrayList<Service> finalResult = new ArrayList<Service>();
        try {
            result = customer.getServices();
            for (Service service : result) {
            	service.getServiceComments();
            	finalResult.add(service);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
	/**
     * Get all Service instances from the datastore that belong to this ServiceType.
     * @param serviceTypeKey:
     * 			the key of the ServiceType
     * @return All Service instances from this ServiceType
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<Service> getAllServicesFromServiceType(Long serviceTypeKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(Service.class);
        query.setFilter("serviceType == serviceTypeParam");
        query.setOrdering("serviceDescription asc");
        query.declareParameters("Long serviceTypeParam");
		
        List<Service> result = null;
        try {
        	result = (List<Service>) query.execute(serviceTypeKey);
            for (Service service : result) {
            	service.getServiceComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Search for Services given the search string. The search works
     * with partial matching, and is case insensitive. It can be filtered
     * down to a specific Customer's Services.
     * The search can be done according to any of the following fields:
     * serviceDescription
     * @param customerKey (can be null)
     * @param searchString
     * @return All Service instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<Service> searchServices(
			Key customerKey, String searchString) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
         
        List<Service> result = null;
        ArrayList<Service> finalResult = new ArrayList<Service>();
        try {
        	Query query = pm.newQuery(Service.class);
        	query.setOrdering("serviceName asc");
        	
        	if (customerKey != null) {
        		query.setFilter("customerKey == customerKeyParam && " +
        				"serviceDescription == searchStringParam");
                query.declareParameters(Key.class.getName() + " customerKeyParam, " +
                		"String searchStringParam");
                result = (List<Service>) query.execute(customerKey, searchString);
        	}
        	else {
        		query.setFilter("serviceDescription.toLowerCase().matches(searchStringParam)");
                query.declareParameters("String searchStringParam");
                result = (List<Service>) query.execute(searchString);
        	}
            
        	for (Service service : result) {
        		finalResult.add(service);
        	}
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
    /**
     * Put Service into datastore.
     * Stores the given Service instance in the datastore for this
     * Customer.
     * @param customerKey
     *          : the key of the Customer where the service will be added
     * @param service
     *          : the Service instance to service
     */
    public static void putService(Key customerKey, 
    		Service service) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = 
                    pm.getObjectById(Customer.class, customerKey);
            tx.begin();
            customer.addService(service);
            customer.updateServiceVersion();
            tx.commit();
            log.info("Service \"" + service.getServiceDescription() + 
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
    * Delete Service from datastore.
    * Deletes the Service corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the Service instance to delete
    */
    public static void deleteService(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = pm.getObjectById(Customer.class, key.getParent());
            Service service = pm.getObjectById(Service.class, key);
            String serviceContent = service.getServiceDescription();
            tx.begin();
            customer.removeService(service);
            customer.updateServiceVersion();
            tx.commit();
            log.info("Service \"" + serviceContent + 
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
    * Update Service attributes.
    * Update's the given Service's attributes in the datastore.
    * @param key
    * 			: the key of the Service whose attributes will be updated
    * @param serviceType
    * 			: service type key
    * @param serviceDescription
    * 			: service description
    * @param serviceComments
    * 			: service comments
	* @throws MissingRequiredFieldsException 
    */
	public static void updateServiceAttributes(
			Key key,
			Long serviceType,
    		String serviceDescription, 
    		String serviceComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
        Customer customer = pm.getObjectById(Customer.class, key.getParent());
		
		Transaction tx = pm.currentTransaction();
		try {
			Service service = pm.getObjectById(Service.class, key);
			tx.begin();
			
			service.setServiceType(serviceType);
			service.setServiceDescription(serviceDescription);
			service.setServiceComments(serviceComments);
			
			customer.updateServiceVersion();
			tx.commit();
			log.info("Service \"" + serviceDescription + 
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
