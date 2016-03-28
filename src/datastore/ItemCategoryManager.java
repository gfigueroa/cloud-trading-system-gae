/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.logging.Logger;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.Query;

import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the ItemCategory class.
 * 
 */

public class ItemCategoryManager {
	
	private static final Logger log = 
        Logger.getLogger(ItemCategoryManager.class.getName());
	
	/**
     * Get a ItemCategory instance from the datastore given the ItemCategory key.
     * @param key
     * 			: the ItemCategory's key
     * @return ItemCategory instance, null if ItemCategory is not found
     */
	public static ItemCategory getItemCategory(Long key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ItemCategory itemCategory;
		try  {
			itemCategory = pm.getObjectById(ItemCategory.class, key);
		}
		catch (JDOObjectNotFoundException e) {
			return null;
		}
		pm.close();
		return itemCategory;
	}
	
	/**
     * Get all ItemCategory instances from the datastore.
     * @return All ItemCategory instances
     * TODO: Inefficient touching of objects
     */
	@SuppressWarnings("unchecked")
	public static List<ItemCategory> getAllItemCategories() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(ItemCategory.class);

        List<ItemCategory> types = null;
        try {
        	types = (List<ItemCategory>) query.execute();
            // touch all elements
            for (ItemCategory t : types)
                t.getItemCategoryName();
        } 
        finally {
        	pm.close();
            query.closeAll();
        }

        return types;
    }
	
	/**
     * Put ItemCategory into datastore.
     * Stations the given ItemCategory instance in the datastore calling the 
     * PersistenceManager's makePersistent() method.
     * @param itemCategory
     * 			: the ItemCategory instance to store
     */
	public static void putItemCategory(ItemCategory itemCategory) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(itemCategory);
			tx.commit();
			log.info("ItemCategory \"" + itemCategory.getItemCategoryName() + 
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
    * Delete ItemCategory from datastore.
    * Deletes the ItemCategory corresponding to the given key
    * from the datastore calling the PersistenceManager's 
    * deletePersistent() method.
    * @param key
    * 			: the key of the ItemCategory instance to delete
    */
	public static void deleteItemCategory(Long key) {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ItemCategory itemCategory = pm.getObjectById(ItemCategory.class, key);
			String ItemCategoryName = itemCategory.getItemCategoryName();
			tx.begin();
			pm.deletePersistent(itemCategory);
			tx.commit();
			log.info("ItemCategory \"" + ItemCategoryName + 
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
    * Update ItemCategory attributes.
    * Update's the given ItemCategory's attributes in the datastore.
    * @param key
    * 			: the key of the ItemCategory whose attributes will be updated
    * @param itemCategoryName
    * 			: the new name to give to the ItemCategory
    * @param itemCategoryDescription
    * 			: the new description to give to the ItemCategory
    * @param itemCategoryComments
    * 			: the new comments to give to the ItemCategory
	* @throws MissingRequiredFieldsException 
    */
	public static void updateItemCategoryAttributes(Long key,
			String itemCategoryName, String itemCategoryDescription,
			String itemCategoryComments) 
                       throws MissingRequiredFieldsException {	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		try {
			ItemCategory itemCategory = pm.getObjectById(ItemCategory.class, key);
			tx.begin();
			itemCategory.setItemCategoryName(itemCategoryName);
			itemCategory.setItemCategoryDescription(itemCategoryDescription);
			itemCategory.setItemCategoryComments(itemCategoryComments);
			tx.commit();
			log.info("ItemCategory \"" + itemCategoryName + 
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
