/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the ItemType table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@PersistenceCapable
public class ItemType {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;
    
    @Persistent
    private Long itemCategory;

    @Persistent
    private String itemTypeName;
    
    @Persistent
    private String itemTypeDescription;

    @Persistent
    private String itemTypeComments;
    
    @Persistent
    private Date itemTypeCreationDate;
    
    @Persistent
    private Date itemTypeModificationDate;

    /**
     * ItemType constructor.
     * @param itemCategory
     * 			: itemCategory
     * @param itemTypeName
     * 			: ItemType name
     * @param itemTypeDescription
     * 			: ItemType description
     * @param itemTypeComments
     * 			: ItemType comments
     * @throws MissingRequiredFieldsException
     */
    public ItemType(Long itemCategory,
    		String itemTypeName, 
    		String itemTypeDescription,
    		String itemTypeComments
    		) 
    		throws MissingRequiredFieldsException {
    	
    	// Check "required field" constraints
    	if (itemCategory == null || itemTypeName == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	if (itemTypeName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

    	this.itemCategory = itemCategory;
        this.itemTypeName = itemTypeName;
        this.itemTypeDescription = itemTypeDescription;
        this.itemTypeComments = itemTypeComments;
        
        Date now = new Date();
        this.itemTypeCreationDate = now;
        this.itemTypeModificationDate = now;
    }

    /**
     * Get ItemType key.
     * @return ItemType key
     */
    public Long getKey() {
        return key;
    }
    
    /**
	 * @return the itemCategory
	 */
	public Long getItemCategory() {
		return itemCategory;
	}
    
    /**
     * Get ItemType name.
     * @return ItemType name
     */
    public String getItemTypeName() {
        return itemTypeName;
    }

    /**
     * Get ItemType description.
     * @return ItemType description
     */
    public String getItemTypeDescription() {
    	return itemTypeDescription;
    }

	/**
     * Get ItemType comments.
     * @return ItemType comments
     */
    public String getItemTypeComments() {
    	return itemTypeComments;
    }

	/**
     * Get ItemType creation date.
     * @return ItemType creation date
     */
    public Date getItemTypeCreationDate() {
    	return itemTypeCreationDate;
    }
    
    /**
     * Get ItemType modification date.
     * @return ItemType modification date
     */
    public Date getItemTypeModificationDate() {
    	return itemTypeModificationDate;
    }
    
	/**
	 * @param itemCategory the itemCategory to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemCategory(Long itemCategory) 
			throws MissingRequiredFieldsException {
		
		if (itemCategory == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"ItemCategory is missing.");
    	}
		this.itemCategory = itemCategory;
		this.itemTypeModificationDate = new Date();
	}
    
    /**
     * Set ItemType name.
     * @param itemTypeName
     * 			: ItemType name
     * @throws MissingRequiredFieldsException
     */
    public void setItemTypeName(
    		String itemTypeName)
    		throws MissingRequiredFieldsException {
    	if (itemTypeName == null || 
    			itemTypeName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"ItemType name is missing.");
    	}
    	this.itemTypeName = itemTypeName;
    	this.itemTypeModificationDate = new Date();
    }
    
    /**
     * Set ItemType description.
     * @param itemTypeDescription
     * 			: ItemType description
     */
    public void setItemTypeDescription(
    		String itemTypeDescription) {
    	this.itemTypeDescription = 
    			itemTypeDescription;
    	this.itemTypeModificationDate = new Date();
    }
    
    /**
     * Set ItemType comments.
     * @param itemTypeComments
     * 			: ItemType comments
     */
    public void setItemTypeComments(
    		String itemTypeComments) {
    	this.itemTypeComments = itemTypeComments;
    	this.itemTypeModificationDate = new Date();
    }
}