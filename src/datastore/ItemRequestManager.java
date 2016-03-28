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
 * made on the ItemRequest class.
 * 
 */

public class ItemRequestManager {
	
	private static final Logger log = 
        Logger.getLogger(ItemRequestManager.class.getName());
	
	/**
     * Get a ItemRequest instance from the datastore given the ItemRequest key.
     * @param key
     * 			: the ItemRequest's key
     * @return ItemRequest instance, null if ItemRequest is not found
     */
	public static ItemRequest getItemRequest(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ItemRequest itemRequest;
		try  {
			itemRequest = pm.getObjectById(ItemRequest.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return itemRequest;
	}
	
	/**
     * Get all ItemRequest instances from the datastore.
     * @return All ItemRequest instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ItemRequest> getAllItemRequests() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(ItemRequest.class);
		
        List<ItemRequest> result = null;
        try {
        	result = (List<ItemRequest>) query.execute();
            for (ItemRequest itemRequest : result) {
            	itemRequest.getItemRequestComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all ItemRequest instances from the datastore that belong to this ItemType.
     * @param itemTypeKey:
     * 			the key of the ItemType
     * @return All ItemRequest instances from this ItemType
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ItemRequest> getAllItemRequestsFromItemType(Long itemTypeKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(ItemRequest.class);
        query.setFilter("itemType == itemTypeParam");
        query.setOrdering("itemRequestDescription asc");
        query.declareParameters("Long itemTypeParam");
		
        List<ItemRequest> result = null;
        try {
        	result = (List<ItemRequest>) query.execute(itemTypeKey);
            for (ItemRequest itemRequest : result) {
            	itemRequest.getItemRequestComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all ItemRequest instances from the datastore that belong to
     * this Customer.
     * @param customerKey
     * @return All ItemRequest instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	public static List<ItemRequest> getAllItemRequestsFromCustomer(
			Key customerKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Customer customer = 
        		pm.getObjectById(Customer.class, customerKey);
         
        List<ItemRequest> result = null;
        ArrayList<ItemRequest> finalResult = new ArrayList<ItemRequest>();
        try {
            result = customer.getItemRequests();
            for (ItemRequest itemRequest : result) {
            	itemRequest.getItemRequestComments();
            	finalResult.add(itemRequest);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
    /**
     * Put ItemRequest into datastore.
     * Stores the given ItemRequest instance in the datastore for this
     * Customer.
     * @param customerKey
     *          : the key of the Customer where the itemRequest will be added
     * @param itemRequest
     *          : the ItemRequest instance to itemRequest
     */
    public static void putItemRequest(Key customerKey, 
    		ItemRequest itemRequest) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = 
                    pm.getObjectById(Customer.class, customerKey);
            tx.begin();
            customer.addItemRequest(itemRequest);
            customer.updateItemRequestVersion();
            tx.commit();
            log.info("ItemRequest \"" + itemRequest.getItemRequestDescription() + 
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
    * Delete ItemRequest from datastore.
    * Deletes the ItemRequest corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the ItemRequest instance to delete
    */
    public static void deleteItemRequest(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = pm.getObjectById(Customer.class, key.getParent());
            ItemRequest itemRequest = pm.getObjectById(ItemRequest.class, key);
            String itemRequestContent = itemRequest.getItemRequestDescription();
            tx.begin();
            customer.removeItemRequest(itemRequest);
            customer.updateItemRequestVersion();
            tx.commit();
            log.info("ItemRequest \"" + itemRequestContent + 
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
    * Update ItemRequest attributes.
    * Update's the given ItemRequest's attributes in the datastore.
    * @param key
    * 			: the key of the ItemRequest whose attributes will be updated
    * @param itemType
    * 			: itemRequest type key
    * @param itemRequestDescription
    * 			: itemRequest description
    * @param itemRequestComments
    * 			: itemRequest comments
	* @throws MissingRequiredFieldsException 
    */
	public static void updateItemRequestAttributes(
			Key key,
			Long itemType,
    		String itemRequestDescription, 
    		String itemRequestComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
        Customer customer = pm.getObjectById(Customer.class, key.getParent());
		
		Transaction tx = pm.currentTransaction();
		try {
			ItemRequest itemRequest = pm.getObjectById(ItemRequest.class, key);
			tx.begin();
			
			itemRequest.setItemType(itemType);
			itemRequest.setItemRequestDescription(itemRequestDescription);
			itemRequest.setItemRequestComments(itemRequestComments);
			
			customer.updateItemRequestVersion();
			tx.commit();
			log.info("ItemRequest \"" + itemRequestDescription + 
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
