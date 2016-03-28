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
 * made on the ServiceRequest class.
 * 
 */

public class ServiceRequestManager {
	
	private static final Logger log = 
        Logger.getLogger(ServiceRequestManager.class.getName());
	
	/**
     * Get a ServiceRequest instance from the datastore given the ServiceRequest key.
     * @param key
     * 			: the ServiceRequest's key
     * @return ServiceRequest instance, null if ServiceRequest is not found
     */
	public static ServiceRequest getServiceRequest(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServiceRequest serviceRequest;
		try  {
			serviceRequest = pm.getObjectById(ServiceRequest.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return serviceRequest;
	}
	
	/**
     * Get all ServiceRequest instances from the datastore.
     * @return All ServiceRequest instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ServiceRequest> getAllServiceRequests() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(ServiceRequest.class);
		
        List<ServiceRequest> result = null;
        try {
        	result = (List<ServiceRequest>) query.execute();
            for (ServiceRequest serviceRequest : result) {
            	serviceRequest.getServiceRequestComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all ServiceRequest instances from the datastore that belong to this ServiceType.
     * @param serviceTypeKey:
     * 			the key of the ServiceType
     * @return All ServiceRequest instances from this ServiceType
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ServiceRequest> getAllServiceRequestsFromServiceType(Long serviceTypeKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(ServiceRequest.class);
        query.setFilter("serviceType == serviceTypeParam");
        query.setOrdering("serviceRequestDescription asc");
        query.declareParameters("Long serviceTypeParam");
		
        List<ServiceRequest> result = null;
        try {
        	result = (List<ServiceRequest>) query.execute(serviceTypeKey);
            for (ServiceRequest serviceRequest : result) {
            	serviceRequest.getServiceRequestComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all ServiceRequest instances from the datastore that belong to
     * this Customer.
     * @param customerKey
     * @return All ServiceRequest instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	public static List<ServiceRequest> getAllServiceRequestsFromCustomer(
			Key customerKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Customer customer = 
        		pm.getObjectById(Customer.class, customerKey);
         
        List<ServiceRequest> result = null;
        ArrayList<ServiceRequest> finalResult = new ArrayList<ServiceRequest>();
        try {
            result = customer.getServiceRequests();
            for (ServiceRequest serviceRequest : result) {
            	serviceRequest.getServiceRequestComments();
            	finalResult.add(serviceRequest);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
    /**
     * Put ServiceRequest into datastore.
     * Stores the given ServiceRequest instance in the datastore for this
     * Customer.
     * @param customerKey
     *          : the key of the Customer where the serviceRequest will be added
     * @param serviceRequest
     *          : the ServiceRequest instance to serviceRequest
     */
    public static void putServiceRequest(Key customerKey, 
    		ServiceRequest serviceRequest) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = 
                    pm.getObjectById(Customer.class, customerKey);
            tx.begin();
            customer.addServiceRequest(serviceRequest);
            customer.updateServiceRequestVersion();
            tx.commit();
            log.info("ServiceRequest \"" + serviceRequest.getServiceRequestDescription() + 
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
    * Delete ServiceRequest from datastore.
    * Deletes the ServiceRequest corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the ServiceRequest instance to delete
    */
    public static void deleteServiceRequest(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = pm.getObjectById(Customer.class, key.getParent());
            ServiceRequest serviceRequest = pm.getObjectById(ServiceRequest.class, key);
            String serviceRequestContent = serviceRequest.getServiceRequestDescription();
            tx.begin();
            customer.removeServiceRequest(serviceRequest);
            customer.updateServiceRequestVersion();
            tx.commit();
            log.info("ServiceRequest \"" + serviceRequestContent + 
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
    * Update ServiceRequest attributes.
    * Update's the given ServiceRequest's attributes in the datastore.
    * @param key
    * 			: the key of the ServiceRequest whose attributes will be updated
    * @param serviceType
    * 			: serviceRequest type key
    * @param serviceRequestDescription
    * 			: serviceRequest description
    * @param serviceRequestComments
    * 			: serviceRequest comments
	* @throws MissingRequiredFieldsException 
    */
	public static void updateServiceRequestAttributes(
			Key key,
			Long serviceType,
    		String serviceRequestDescription, 
    		String serviceRequestComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
        Customer customer = pm.getObjectById(Customer.class, key.getParent());
		
		Transaction tx = pm.currentTransaction();
		try {
			ServiceRequest serviceRequest = pm.getObjectById(ServiceRequest.class, key);
			tx.begin();
			
			serviceRequest.setServiceType(serviceType);
			serviceRequest.setServiceRequestDescription(serviceRequestDescription);
			serviceRequest.setServiceRequestComments(serviceRequestComments);
			
			customer.updateServiceRequestVersion();
			tx.commit();
			log.info("ServiceRequest \"" + serviceRequestDescription + 
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
