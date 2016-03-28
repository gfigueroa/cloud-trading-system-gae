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

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the Item table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class Item implements Serializable {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long itemType;
	
	@Persistent
	private Key customerKey;
    
    @Persistent
    private String itemName;
    
    @Persistent
    private String itemDescription;
    
    @Persistent
    private Double itemPrice;

	@Persistent
    private Boolean itemIsForDonation;
    
    @Persistent
    private Boolean itemIsForExchange;
    
    @Persistent
    private BlobKey itemImage1;
    
    @Persistent
    private Date itemManufacturingTime;
    
    @Persistent
    private Date itemAcquisitionTime;
    
    @Persistent
    private Date itemExpirationTime;
    
    @Persistent
    private Boolean isAvailable;
    
    @Persistent
    private String itemComments;
    
    @Persistent
    private Date itemCreationDate;
    
    @Persistent
    private Date  itemModificationDate;

    /**
     * Item constructor.
     * @param itemType
     * 			: ItemType key
     * @param customerKey
     * 			: Customer key
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
    public Item(
    		Long itemType,
    		Key customerKey,
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
        
    	// Check "required field" constraints
    	if (itemName == null || itemType == null ||
    			customerKey == null ||itemIsForDonation == null || 
    			itemIsForExchange == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}
    	
    	if (itemName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"One or more required fields are missing.");
    	}

    	this.itemType = itemType;
    	this.customerKey = customerKey;
    	this.itemName = itemName;
    	this.itemDescription = itemDescription;
    	this.itemPrice = itemPrice;
    	this.itemIsForExchange = itemIsForExchange;
    	this.itemIsForDonation = itemIsForDonation;
    	this.itemImage1 = itemImage1;
    	this.itemManufacturingTime = itemManufacturingTime;
    	this.itemAcquisitionTime = itemAcquisitionTime;
    	this.itemExpirationTime = itemExpirationTime;
    	this.isAvailable = true;
    	this.itemComments = itemComments;
    	
    	Date now = new Date();
    	this.itemCreationDate = now;
    	this.itemModificationDate = now;
    }

	/**
     * Get Item key.
     * @return item key
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
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
    
    /**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @return the itemPrice
	 */
	public Double getItemPrice() {
		return itemPrice;
	}

	/**
	 * @return the itemIsForDonation
	 */
	public Boolean getItemIsForDonation() {
		return itemIsForDonation;
	}

	/**
	 * @return the itemIsForExchange
	 */
	public Boolean getItemIsForExchange() {
		return itemIsForExchange;
	}

	/**
	 * @return the itemImage1
	 */
	public BlobKey getItemImage1() {
		return itemImage1;
	}

	/**
	 * @return the itemManufacturingTime
	 */
	public Date getItemManufacturingTime() {
		return itemManufacturingTime;
	}

	/**
	 * @return the itemAcquisitionTime
	 */
	public Date getItemAcquisitionTime() {
		return itemAcquisitionTime;
	}

	/**
	 * @return the itemExpirationTime
	 */
	public Date getItemExpirationTime() {
		return itemExpirationTime;
	}

	/**
	 * @return the isAvailable
	 */
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	/**
	 * @return the itemComments
	 */
	public String getItemComments() {
		return itemComments;
	}

    /**
     * Get item creation date.
     * @return the time this item was created
     */
    public Date getItemCreationDate() {
        return itemCreationDate;
    }

    /**
     * Get item modification date.
     * @return the time this item was last modified
     */
    public Date getItemModificationDate() {
        return itemModificationDate;
    }
    
    /**
     * Compare this item with another item
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this Item, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof Item ) ) return false;
        Item item = (Item) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(item.getKey()));
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
		this.itemModificationDate = new Date();
	}
    
	/**
	 * @param itemName the itemName to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemName(
			String itemName) 
			throws MissingRequiredFieldsException {
		if (itemName == null || itemName.isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Item name is missing.");
    	}
		this.itemName = itemName;
		this.itemModificationDate = new Date();
	}
    
    /**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
		this.itemModificationDate = new Date();
	}
	
	/**
	 * @param itemPrice the itemPrice to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
		this.itemModificationDate = new Date();
	}
	
	/**
	 * @param itemIsForDonation the itemIsForDonation to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemIsForDonation(Boolean itemIsForDonation) 
			throws MissingRequiredFieldsException {
		if (itemIsForDonation == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Item isForDonation is missing.");
    	}
		this.itemIsForDonation = itemIsForDonation;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemIsForExchange the itemIsForExchange to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setItemIsForExchange(Boolean itemIsForExchange) 
			throws MissingRequiredFieldsException {
		if (itemIsForExchange == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Item isForExchange is missing.");
    	}
		this.itemIsForExchange = itemIsForExchange;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemImage1 the itemImage1 to set
	 */
	public void setItemImage1(BlobKey itemImage1) {
		this.itemImage1 = itemImage1;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemManufacturingTime the itemManufacturingTime to set
	 */
	public void setItemManufacturingTime(Date itemManufacturingTime) {
		this.itemManufacturingTime = itemManufacturingTime;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemAcquisitionTime the itemAcquisitionTime to set
	 */
	public void setItemAcquisitionTime(Date itemAcquisitionTime) {
		this.itemAcquisitionTime = itemAcquisitionTime;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemExpirationTime the itemExpirationTime to set
	 */
	public void setItemExpirationTime(Date itemExpirationTime) {
		this.itemExpirationTime = itemExpirationTime;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param isAvailable the isAvailable to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setIsAvailable(Boolean isAvailable) 
			throws MissingRequiredFieldsException {
		if (isAvailable == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Item isAvailable is missing.");
    	}
		this.isAvailable = isAvailable;
		this.itemModificationDate = new Date();
	}

	/**
	 * @param itemComments the itemComments to set
	 */
	public void setItemComments(String itemComments) {
		this.itemComments = itemComments;
		this.itemModificationDate = new Date();
	}
}