/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import exceptions.MissingRequiredFieldsException;

/**
 * This class is used to manage the GAE Datastore operations (get, put, delete, update)
 * made on the System class.
 * 
 */

public class SystemManager {
	
	private static final Logger log = Logger.getLogger(SystemManager.class.getName());
	
	/**
     * Get System instance from the datastore.
     * @return The only System instance there should be
     */
	public static System getSystem() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        System system = null;
			
        try {
        	List<System> systems = SystemManager.getAllSystems();
        	// Create system if it hasn't been created yet
        	if (systems == null || systems.isEmpty()) {
        		system = new System();
        		pm.makePersistent(system);
        	}
        	else {
    			system = systems.get(0);
    		}
        } 
        finally {
        	pm.close();
        }

        return system;
    }
	
	/**
     * Get all System instances from the datastore.
     * @return All System instances
     * TODO: Make "touching" of systems more efficient
     */
	@SuppressWarnings("unchecked")
	public static List<System> getAllSystems() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(System.class);

        List<System> systems = null;
        try {
        	systems = (List<System>) query.execute();
            // touch all elements
            for (System s : systems)
                s.getSystemTime();
        } 
        finally {
        	pm.close();
            query.closeAll();
        }

        return systems;
    }
	
	/**
     * Get Customer List Version from the system.
     * @return customer list version
     */
	public static int getCustomerListVersion() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        int customerListVersion = 0;
			
        try {
        	List<System> systems = SystemManager.getAllSystems();
    		if (systems != null && !systems.isEmpty()) {
    			customerListVersion = systems.get(0).getCustomerListVersion();
    		}
        } 
        finally {
        	pm.close();
        }

        return customerListVersion;
	}
	
	/**
     * Get ItemCategory List Version from the system.
     * @return ItemCategory list version
     */
	public static int getItemCategoryListVersion() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        int itemCategoryListVersion = 0;
			
        try {
        	List<System> systems = SystemManager.getAllSystems();
    		if (systems != null && !systems.isEmpty()) {
    			itemCategoryListVersion = 
    					systems.get(0).getItemCategoryListVersion();
    		}
        } 
        finally {
        	pm.close();
        }

        return itemCategoryListVersion;
	}
	
	/**
     * Get ItemType List Version from the system.
     * @return ItemType list version
     */
	public static int getItemTypeListVersion() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        int itemTypeListVersion = 0;
			
        try {
        	List<System> systems = SystemManager.getAllSystems();
    		if (systems != null && !systems.isEmpty()) {
    			itemTypeListVersion = 
    					systems.get(0).getItemTypeListVersion();
    		}
        } 
        finally {
        	pm.close();
        }

        return itemTypeListVersion;
	}
	
	/**
     * Get ServiceType List Version from the system.
     * @return ServiceType list version
     */
	public static int getServiceTypeListVersion() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
        int serviceTypeListVersion = 0;
			
        try {
        	List<System> systems = SystemManager.getAllSystems();
    		if (systems != null && !systems.isEmpty()) {
    			serviceTypeListVersion = 
    					systems.get(0).getServiceTypeListVersion();
    		}
        } 
        finally {
        	pm.close();
        }

        return serviceTypeListVersion;
	}
	
	/**
    * Update Customer List Version.
    * Updates the customer list version (add 1)
    */
	public static void updateCustomerListVersion() {	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			List<System> systems = SystemManager.getAllSystems();
			System system = null;
			tx.begin();
			// Create system if it hasn't been created yet
			if (systems == null || systems.isEmpty()) {
				system = new System();
				pm.makePersistent(system);
			}
			else {
				system = pm.getObjectById(System.class, systems.get(0).getKey());
			}
			system.updateCustomerListVersion();
			tx.commit();
			log.info("System \"" + system.getKey() + 
					"\": Customer List Version updated to version " +
					system.getCustomerListVersion() + " in the datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
    * Update ItemCategory List Version.
    * Updates the ItemCategory list version (add 1)
    */
	public static void updateItemCategoryListVersion() {	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			List<System> systems = SystemManager.getAllSystems();
			System system = null;
			tx.begin();
			// Create system if it hasn't been created yet
			if (systems == null || systems.isEmpty()) {
				system = new System();
				pm.makePersistent(system);
			}
			else {
				system = pm.getObjectById(System.class, systems.get(0).getKey());
			}
			system.updateItemCategoryListVersion();
			tx.commit();
			log.info("System \"" + system.getKey() + 
					"\": ItemCategory List Version updated to version " +
					system.getItemCategoryListVersion() + " in the datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
    * Update ItemType List Version.
    * Updates the ItemType list version (add 1)
    */
	public static void updateItemTypeListVersion() {	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			List<System> systems = SystemManager.getAllSystems();
			System system = null;
			tx.begin();
			// Create system if it hasn't been created yet
			if (systems == null || systems.isEmpty()) {
				system = new System();
				pm.makePersistent(system);
			}
			else {
				system = pm.getObjectById(System.class, systems.get(0).getKey());
			}
			system.updateItemTypeListVersion();
			tx.commit();
			log.info("System \"" + system.getKey() + 
					"\": ItemType List Version updated to version " +
					system.getItemTypeListVersion() + " in the datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
    * Update ServiceType List Version.
    * Updates the ServiceType list version (add 1)
    */
	public static void updateServiceTypeListVersion() {	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			List<System> systems = SystemManager.getAllSystems();
			System system = null;
			tx.begin();
			// Create system if it hasn't been created yet
			if (systems == null || systems.isEmpty()) {
				system = new System();
				pm.makePersistent(system);
			}
			else {
				system = pm.getObjectById(System.class, systems.get(0).getKey());
			}
			system.updateServiceTypeListVersion();
			tx.commit();
			log.info("System \"" + system.getKey() + 
					"\": ServiceType List Version updated to version " +
					system.getServiceTypeListVersion() + " in the datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
    * Update System attributes.
    * Update the different System variables in the datastore.
    * @param oldestAppVersionSupported
    * 			: the new oldestAppVersionSupported to be updated
	 * @throws MissingRequiredFieldsException 
    */
	public static void updateSystemAttributes(Integer oldestAppVersionSupported1,
			Integer oldestAppVersionSupported2, Integer oldestAppVersionSupported3) 
			throws MissingRequiredFieldsException {	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			List<System> systems = SystemManager.getAllSystems();
			System system = null;
			tx.begin();
			// Create system if it hasn't been created yet
			if (systems == null || systems.isEmpty()) {
				system = new System();
				pm.makePersistent(system);
			}
			else {
				system = pm.getObjectById(System.class, systems.get(0).getKey());
			}
			system.setOldestAppVersionSupported1(oldestAppVersionSupported1);
			system.setOldestAppVersionSupported2(oldestAppVersionSupported2);
			system.setOldestAppVersionSupported3(oldestAppVersionSupported3);
			tx.commit();
			log.info("System \"" + system.getKey() + "\": oldestAppVersionSupported updated" +
					" in the datastore.");
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
}

