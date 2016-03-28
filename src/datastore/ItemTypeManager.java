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
 * made on the ItemType class.
 * 
 */

public class ItemTypeManager {
	
	private static final Logger log = 
        Logger.getLogger(ItemTypeManager.class.getName());
	
	/**
     * Get a ItemType instance from the datastore given the ItemType key.
     * @param key
     * 			: the ItemType's key
     * @return ItemType instance, null if ItemType is not found
     */
	public static ItemType getItemType(Long key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ItemType itemType;
		try  {
			itemType = pm.getObjectById(ItemType.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return itemType;
	}
	
	/**
     * Get all ItemType instances from the datastore.
     * @return All ItemType instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ItemType> getAllItemTypes() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(ItemType.class);

        List<ItemType> types = null;
        try {
        	types = (List<ItemType>) query.execute();
            // touch all elements
            for (ItemType t : types)
                t.getItemTypeName();
        } 
        finally {
        	pm.close();
            query.closeAll();
        }

        return types;
    }
	
	/**
     * Get all ItemType instances from the datastore that belong
     * to the given ItemCategory.
     * @param itemCategoryKey:
     * 			the key of the ItemCategory to filter
     * @return All ItemType instances that belong to this ItemCategory
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ItemType> getItemTypes(Long itemCategoryKey) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(ItemType.class);
        query.setFilter("itemCategory == itemCategoryParam");
        query.setOrdering("itemTypeName asc");
        query.declareParameters("Long itemCategoryParam");

        List<ItemType> types = null;
        try {
        	types = (List<ItemType>) query.execute(itemCategoryKey);
            // touch all elements
            for (ItemType t : types)
                t.getItemTypeName();
        } 
        finally {
        	pm.close();
            query.closeAll();
        }

        return types;
    }	
	
	/**
     * Put ItemType into datastore.
     * Stations the given ItemType instance in the datastore calling the 
     * PersistenceManager's makePersistent() method.
     * @param itemType
     * 			: the ItemType instance to store
     */
	public static void putItemType(ItemType itemType) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(itemType);
			tx.commit();
			log.info("ItemType \"" + itemType.getItemTypeName() + 
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
    * Delete ItemType from datastore.
    * Deletes the ItemType corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    * 			: the key of the ItemType instance to delete
    */
	public static void deleteItemType(Long key) {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ItemType itemType = pm.getObjectById(ItemType.class, key);
			String ItemTypeName = itemType.getItemTypeName();
			tx.begin();
			pm.deletePersistent(itemType);
			tx.commit();
			log.info("ItemType \"" + ItemTypeName + 
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
    * Update ItemType attributes.
    * Update's the given ItemType's attributes in the datastore.
    * @param key
    * 			: the key of the ItemType whose attributes will be updated
    * @param itemCategory
    * 			: itemCategory
    * @param itemTypeName
    * 			: the new name to give to the ItemType
    * @param itemTypeDescription
    * 			: the new description to give to the ItemType
    * @param itemTypeComments
    * 			: the new comments to give to the ItemType
	* @throws MissingRequiredFieldsException 
    */
	public static void updateItemTypeAttributes(
			Long key,
			Long itemCategory,
    		String itemTypeName, 
    		String itemTypeDescription,
    		String itemTypeComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ItemType itemType = pm.getObjectById(ItemType.class, key);
			tx.begin();
			itemType.setItemCategory(itemCategory);
			itemType.setItemTypeName(itemTypeName);
			itemType.setItemTypeDescription(itemTypeDescription);
			itemType.setItemTypeComments(itemTypeComments);
			tx.commit();
			log.info("ItemType \"" + itemTypeName + 
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
