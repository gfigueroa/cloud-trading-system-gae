/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the ItemCategory table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@PersistenceCapable
public class ItemCategory {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;

    @Persistent
    private String itemCategoryName;
    
    @Persistent
    private String itemCategoryDescription;

    @Persistent
    private String itemCategoryComments;
    
    @Persistent
    private Date itemCategoryCreationDate;
    
    @Persistent
    private Date itemCategoryModificationDate;

    /**
     * ItemCategory constructor.
     * @param itemCategoryName
     * 			: ItemCategory name
     * @param itemCategoryDescription
     * 			: ItemCategory description
     * @param itemCategoryComments
     * 			: ItemCategory comments
     * @throws MissingRequiredFieldsException
     */
    public ItemCategory(String itemCategoryName, 
    		String itemCategoryDescription,
    		String itemCategoryComments) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (itemCategoryName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (itemCategoryName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

        this.itemCategoryName = itemCategoryName;
        this.itemCategoryDescription = itemCategoryDescription;
        this.itemCategoryComments = itemCategoryComments;
        
        Date now = new Date();
        this.itemCategoryCreationDate = now;
        this.itemCategoryModificationDate = now;
    }

    /**
     * Get ItemCategory key.
     * @return ItemCategory key
     */
    public Long getKey() {
        return key;
    }
    
    /**
     * Get ItemCategory name.
     * @return ItemCategory name
     */
    public String getItemCategoryName() {
        return itemCategoryName;
    }

    /**
     * Get ItemCategory description.
     * @return ItemCategory description
     */
    public String getItemCategoryDescription() {
    	return itemCategoryDescription;
    }
    
    /**
     * Get ItemCategory comments.
     * @return ItemCategory comments
     */
    public String getItemCategoryComments() {
    	return itemCategoryComments;
    }
    
    /**
     * Get ItemCategory creation date.
     * @return ItemCategory creation date
     */
    public Date getItemCategoryCreationDate() {
    	return itemCategoryCreationDate;
    }
    
    /**
     * Get ItemCategory modification date.
     * @return ItemCategory modification date
     */
    public Date getItemCategoryModificationDate() {
    	return itemCategoryModificationDate;
    }
    
    /**
     * Set ItemCategory name.
     * @param itemCategoryName
     * 			: ItemCategory name
     * @throws MissingRequiredFieldsException
     */
    public void setItemCategoryName(String itemCategoryName)
    		throws MissingRequiredFieldsException {
    	if (itemCategoryName == null || itemCategoryName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"DeviceService type name is missing.");
    	}
    	this.itemCategoryName = itemCategoryName;
    	this.itemCategoryModificationDate = new Date();
    }
    
    /**
     * Set ItemCategory description.
     * @param itemCategoryDescription
     * 			: ItemCategory description
     */
    public void setItemCategoryDescription(String itemCategoryDescription) {
    	this.itemCategoryDescription = itemCategoryDescription;
    	this.itemCategoryModificationDate = new Date();
    }
    
    /**
     * Set ItemCategory comments.
     * @param itemCategoryComments
     * 			: ItemCategory comments
     */
    public void setItemCategoryComments(String itemCategoryComments) {
    	this.itemCategoryComments = itemCategoryComments;
    	this.itemCategoryModificationDate = new Date();
    }
}