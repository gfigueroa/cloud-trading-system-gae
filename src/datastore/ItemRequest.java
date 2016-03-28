/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the ItemRequest table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class ItemRequest implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long itemType;
	
	@Persistent
	private Key customerKey;
    
    @Persistent
    private String itemRequestDescription;
    
    @Persistent
    private String itemRequestComments;
    
    @Persistent
    private Date itemRequestCreationDate;
    
    @Persistent
    private Date  itemRequestModificationDate;

    /**
     * ItemRequest constructor.
     * @param itemRequestType
     * 			: ItemRequestType key
     * @param customerKey
     * 			: Customer key
     * @param itemRequestDescription
     * 			: itemRequest description
     * @param itemRequestComments
     * 			: itemRequest comments
     * @throws MissingRequiredFieldsException
     */
    public ItemRequest(
    		Long itemType,
    		Key customerKey,
    		String itemRequestDescription, 
    		String itemRequestComments) 
		throws MissingRequiredFieldsException {
        
    	// Check "required field" constraints
    	if (itemType == null ||
    			customerKey == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
    	if (itemRequestDescription.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

    	this.itemType = itemType;
    	this.customerKey = customerKey;
    	this.itemRequestDescription = itemRequestDescription;
    	this.itemRequestComments = itemRequestComments;
    	
    	Date now = new Date();
    	this.itemRequestCreationDate = now;
    	this.itemRequestModificationDate = now;
    }

	/**
     * Get ItemRequest key.
     * @return itemRequest key
     */
    public Key getKey() {
        return key;
    }
    
	/**
	 * @return the itemType
	 */
	public Long getItemType() {
		return itemType;
	}
    
    /**
     * Get Customer key
     * @return customerKey
     */
    public Key getCustomerKey() {
    	return customerKey;
    }
    
    /**
	 * @return the itemRequestDescription
	 */
	public String getItemRequestDescription() {
		return itemRequestDescription;
	}

	/**
	 * @return the itemRequestComments
	 */
	public String getItemRequestComments() {
		return itemRequestComments;
	}

    /**
     * Get itemRequest creation date.
     * @return the time this itemRequest was created
     */
    public Date getItemRequestCreationDate() {
        return itemRequestCreationDate;
    }

    /**
     * Get itemRequest modification date.
     * @return the time this itemRequest was last modified
     */
    public Date getItemRequestModificationDate() {
        return itemRequestModificationDate;
    }
    
    /**
     * Compare this itemRequest with another itemRequest
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this ItemRequest, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof ItemRequest ) ) return false;
        ItemRequest itemRequest = (ItemRequest) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(itemRequest.getKey()));
    }
    
	/**
	 * @param itemType the itemType to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemType(Long itemType) 
			throws MissingRequiredFieldsException {
		if (itemType == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Item type is missing.");
    	}
		this.itemType = itemType;
		this.itemRequestModificationDate = new Date();
	}
    
    /**
	 * @param itemRequestDescription the itemRequestDescription to set
     * @throws MissingRequiredFieldsException 
	 */
	public void setItemRequestDescription(String itemRequestDescription) 
			throws MissingRequiredFieldsException {
		if (itemRequestDescription == null || itemRequestDescription.isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"ItemRequest Description is missing.");
    	}
		this.itemRequestDescription = itemRequestDescription;
		this.itemRequestModificationDate = new Date();
	}

	/**
	 * @param itemRequestComments the itemRequestComments to set
	 */
	public void setItemRequestComments(String itemRequestComments) {
		this.itemRequestComments = itemRequestComments;
		this.itemRequestModificationDate = new Date();
	}
}