/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

import exceptions.InexistentObjectException;
import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the Item class.
 * 
 */

public class ItemManager {
	
	private static final Logger log = 
        Logger.getLogger(ItemManager.class.getName());
	
	/**
     * Get a Item instance from the datastore given the Item key.
     * @param key
     * 			: the Item's key
     * @return Item instance, null if Item is not found
     */
	public static Item getItem(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Item item;
		try  {
			item = pm.getObjectById(Item.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return item;
	}
	
	/**
     * Get all Item instances from the datastore.
     * @return All Item instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<Item> getAllItems() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(Item.class);
		
        List<Item> result = null;
        try {
        	result = (List<Item>) query.execute();
            for (Item item : result) {
            	item.getItemComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all Item instances from the datastore that belong to this ItemType.
     * @param itemTypeKey:
     * 			the key of the ItemType
     * @return All Item instances from this ItemType
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<Item> getAllItemsFromItemType(Long itemTypeKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Query query = pm.newQuery(Item.class);
        query.setFilter("itemType == itemTypeParam");
        query.setOrdering("itemName asc");
        query.declareParameters("Long itemTypeParam");
		
        List<Item> result = null;
        try {
        	result = (List<Item>) query.execute(itemTypeKey);
            for (Item item : result) {
            	item.getItemComments();
            }
        }
        finally {
            pm.close();
        }

        return result;
    }
	
	/**
     * Get all Item instances from the datastore that belong to
     * this Customer.
     * @param customerKey
     * @return All Item instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	public static List<Item> getAllItemsFromCustomer(
			Key customerKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        
        Customer customer = 
        		pm.getObjectById(Customer.class, customerKey);
         
        List<Item> result = null;
        ArrayList<Item> finalResult = new ArrayList<Item>();
        try {
            result = customer.getItems();
            for (Item item : result) {
            	item.getItemComments();
            	finalResult.add(item);
            }
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
	/**
     * Search for Items given the search string. The search works
     * with partial matching, and is case insensitive. It can be filtered
     * down to a specific Customer's Items.
     * The search can be done according to any of the following fields:
     * itemName
     * @param customerKey (can be null)
     * @param searchString
     * @return All Item instances that belong to the given Customer
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<Item> searchItems(
			Key customerKey, String searchString) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
         
        List<Item> result = null;
        ArrayList<Item> finalResult = new ArrayList<Item>();
        try {
        	Query query = pm.newQuery(Item.class);
        	query.setOrdering("itemName asc");
        	
        	if (customerKey != null) {
        		query.setFilter("customerKey == customerKeyParam && " +
        				"itemName == searchStringParam");
                query.declareParameters(Key.class.getName() + " customerKeyParam, " +
                		"String searchStringParam");
                result = (List<Item>) query.execute(customerKey, searchString);
        	}
        	else {
        		query.setFilter("itemName.toLowerCase().matches(searchStringParam)");
                query.declareParameters("String searchStringParam");
                result = (List<Item>) query.execute(searchString);
        	}
            
        	for (Item item : result) {
        		finalResult.add(item);
        	}
        }
        finally {
            pm.close();
        }

        return finalResult;
    }
	
    /**
     * Put Item into datastore.
     * Stores the given Item instance in the datastore for this
     * Customer.
     * @param customerKey
     *          : the key of the Customer where the item will be added
     * @param item
     *          : the Item instance to item
     */
    public static void putItem(Key customerKey, 
    		Item item) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = 
                    pm.getObjectById(Customer.class, customerKey);
            tx.begin();
            customer.addItem(item);
            customer.updateItemVersion();
            tx.commit();
            log.info("Item \"" + item.getItemName() + 
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
    * Delete Item from datastore.
    * Deletes the Item corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    *           : the key of the Item instance to delete
    */
    public static void deleteItem(Key key) { 
         
        PersistenceManager pm = PMF.get().getPersistenceManager();
         
        Transaction tx = pm.currentTransaction();
        try {
            Customer customer = pm.getObjectById(Customer.class, key.getParent());
            Item item = pm.getObjectById(Item.class, key);
            String itemContent = item.getItemName();
            tx.begin();
            customer.removeItem(item);
            customer.updateItemVersion();
            tx.commit();
            log.info("Item \"" + itemContent + 
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
    * Update Item attributes.
    * Update's the given Item's attributes in the datastore.
    * @param key
    * 			: the key of the Item whose attributes will be updated
    * @param itemType
    * 			: item type key
    * @param itemName
    * 			: item name
    * @param itemDescription
    * 			: item description
    * @param itemPrice
    * 			: item price
    * @param itemIsForDonation
    * 			: item isForDonation
    * @param itemIsForExchange
    * 			: item isForExchange
    * @param itemImage1
    * 			: item image1
    * @param itemManufacturingTime
    * 			: item manufacturingTime
    * @param itemAcquisitionTime
    * 			: item acquisitionTime
    * @param itemExpirationDate
    * 			: item expirationDate
    * @param itemComments
    * 			: item comments
	* @throws MissingRequiredFieldsException 
    */
	public static void updateItemAttributes(
			Key key,
			Long itemType,
    		String itemName, 
    		String itemDescription, 
    		Double itemPrice,
    		Boolean itemIsForDonation,
    		Boolean itemIsForExchange,
    		BlobKey itemImage1,
    		Date itemManufacturingTime,
    		Date itemAcquisitionTime,
    		Date itemExpirationTime,
    		String itemComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
        Customer customer = pm.getObjectById(Customer.class, key.getParent());
		
		Transaction tx = pm.currentTransaction();
		try {
			Item item = pm.getObjectById(Item.class, key);
			tx.begin();
			
			item.setItemType(itemType);
			item.setItemName(itemName);
			item.setItemDescription(itemDescription);
			item.setItemPrice(itemPrice);
			item.setItemIsForDonation(itemIsForDonation);
			item.setItemIsForExchange(itemIsForExchange);
			item.setItemImage1(itemImage1);
			item.setItemManufacturingTime(itemManufacturingTime);
			item.setItemAcquisitionTime(itemAcquisitionTime);
			item.setItemExpirationTime(itemExpirationTime);
			item.setItemComments(itemComments);
			
			customer.updateItemVersion();
			tx.commit();
			log.info("Item \"" + itemName + 
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
