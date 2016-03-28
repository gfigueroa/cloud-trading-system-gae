/*
 Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package datastore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;

import exceptions.InexistentObjectException;
import exceptions.InvalidFieldFormatException;
import exceptions.MissingRequiredFieldsException;

/**
 * This class represents the Customer table.
 * It is managed as a JDO to be stored in and retrieved from the GAE datastore.
 * 
 */

@SuppressWarnings("serial")
@PersistenceCapable
public class Customer implements Serializable {
	
	public static enum Status {
		ACTIVE, INACTIVE, UNCONFIRMED, DISABLED
	}
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
    private String confirmationKey;

    @Persistent(dependent = "true", defaultFetchGroup = "true")
    private User user;
    
    @Persistent
    private String customerName;
    
    @Persistent
    private String customerIdentityNumber;
    
    @Persistent
    private String customerNickname;
    
    @Persistent
    private String customerSerialNumber;
    
    @Persistent
    private PhoneNumber customerPhoneNumber;
    
    @Persistent
    private Key village;
    
    @Persistent
    private PostalAddress customerAddress;
    
    @Persistent
    private BlobKey customerLogo;
    
    @Persistent
    private Status customerStatus;
    
    @Persistent
    private String customerComments;
    
    @Persistent
    private Integer itemVersion;
    
    @Persistent
    private Integer serviceVersion;
    
    @Persistent
    private Integer itemRequestVersion;
    
    @Persistent
    private Integer serviceRequestVersion;
    
    @Persistent
    private Date customerCreationDate;
    
    @Persistent
    private Date customerModificationDate;
    
    @Persistent
    @Element(dependent = "true")
    private ArrayList<Item> items;
    
    @Persistent
    @Element(dependent = "true")
    private ArrayList<Service> services;
    
    @Persistent
    @Element(dependent = "true")
    private ArrayList<ItemRequest> itemRequests;
    
    @Persistent
    @Element(dependent = "true")
    private ArrayList<ServiceRequest> serviceRequests;
    
    /**
     * Customer constructor.
     * @param confirmationKey
     * 			: the confirmation key string of this Customer
     * @param user
     * 			: the user for this customer
     * @param customerName
     * 			: customer name
     * @param customerIdentityNumber
     * 			: customer identityNumber
     * @param customerNickname
     * 			: customer nickname
     * @param customerSerialNumber
     * 			: customer serial number0
     * @param customerPhoneNumber
     * 			: customer phone number
     * @param village
     * 			: customer village key
     * @param customerAddress
     * 			: customer address
     * @param customerLogo
     * 			: customer logo blob key
     * @param customerStatus
     * 			: the initial status of this customer
     * @param customerComments
     * 			: customer comments
     * @throws MissingRequiredFieldsException
     * @throws InvalidFieldFormatException 
     */
    public Customer(
    		String confirmationKey,
    		User user, 
    		String customerName,
    		String customerIdentityNumber,
    		String customerNickname,
    		String customerSerialNumber,
    		PhoneNumber customerPhoneNumber,
    		Key village,
    		PostalAddress customerAddress,
    		BlobKey customerLogo,
    		Status customerStatus,
    		String customerComments)
    		throws MissingRequiredFieldsException {
        
    	// Check "required field" constraints
    	if (user == null || customerName == null || customerSerialNumber == null ||
    			village == null) {
    		throw new MissingRequiredFieldsException(
    				this, "One or more required fields are missing.");
    	}
    	if (customerName.trim().isEmpty() || customerSerialNumber.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(
    				this, "One or more required fields are missing.");
    	}
    	
    	this.user = user;
    	
    	// Create key with user email
    	this.key = KeyFactory.createKey(
    			Customer.class.getSimpleName(), user.getUserEmail().getEmail());

    	this.confirmationKey = confirmationKey;
    	this.customerName = customerName;
    	this.customerIdentityNumber = customerIdentityNumber;
    	this.customerNickname = customerNickname;
    	this.customerSerialNumber = customerSerialNumber;
    	this.customerPhoneNumber = customerPhoneNumber;
    	this.village = village;
    	this.customerAddress = customerAddress;
        this.customerLogo = customerLogo;
        this.customerStatus = customerStatus;
        this.customerComments = customerComments;
        
        Date now = new Date();
        this.customerCreationDate = now;
        this.customerModificationDate = now;
        
    	// Create empty lists
    	this.items = new ArrayList<Item>();
    	this.services = new ArrayList<Service>();
    	this.itemRequests = new ArrayList<ItemRequest>();
    	this.serviceRequests = new ArrayList<ServiceRequest>();
    	
    	// Initialize the versions in 0
    	this.itemVersion = 0;
    	this.serviceVersion = 0;
    	this.itemRequestVersion = 0;
    	this.serviceRequestVersion = 0;
    }
    
    /**
     * Get Customer key.
     * @return customer key
     */
    public Key getKey() {
        return key;
    }
    
    /**
     * Get Customer confirmation key string
     * @return customer confirmationKey
     */
    public String getConfirmationKey() {
    	return confirmationKey;
    }
    
    /**
     * Get Customer user.
     * @return customer user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Get Customer name.
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Get Customer identityNumber.
     * @return customer identityNumber
     */
    public String getCustomerIdentityNumber() {
        return customerIdentityNumber;
    }
    
    /**
	 * @return the customerNickname
	 */
	public String getCustomerNickname() {
		return customerNickname;
	}

	/**
	 * @return the customerSerialNumber
	 */
	public String getCustomerSerialNumber() {
		return customerSerialNumber;
	}

	/**
     * Get Customer phone number.
     * @return Customer phone number
     */
    public PhoneNumber getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    
    /**
     * Get Customer village.
     * @return village ID
     */
    public Key getVillage() {
    	return village;
    }
    
    /**
     * Get Customer address.
     * @return customer address
     */
    public PostalAddress getCustomerAddress() {
    	return customerAddress;
    }

    /**
     * Get Customer logo.
     * @return customer logo blobkey
     */
    public BlobKey getCustomerLogo() {
        return customerLogo;
    }
    
	/**
	 * @return the customerStatus
	 */
	public Status getCustomerStatus() {
		return customerStatus;
	}
	
	/**
	 * Get a Status from a String representation
	 * @param customerStatusString: the string representation of the Status
	 * @return Status from string
	 */
	public static Status getCustomerStatusFromString(
			String customerStatusString) {

		if (customerStatusString == null) {
			return null;
		}
		else if (customerStatusString.equalsIgnoreCase("active")) {
			return Status.ACTIVE;
		}
		else if (customerStatusString.equalsIgnoreCase("inactive")) {
			return Status.INACTIVE;
		}
		else if (customerStatusString.equalsIgnoreCase("unconfirmed")) {
			return Status.UNCONFIRMED;
		}
		else if (customerStatusString.equalsIgnoreCase("disabled")) {
			return Status.DISABLED;
		}
		else {
			return null;
		}
	}
    
    /**
     * Get Customer comments.
     * @return customer comments
     */
    public String getCustomerComments() {
    	return customerComments;
    }
    
	/**
	 * @return the itemVersion
	 */
	public Integer getItemVersion() {
		return itemVersion;
	}

	/**
	 * @return the serviceVersion
	 */
	public Integer getServiceVersion() {
		return serviceVersion;
	}
	
	/**
	 * @return the itemRequestVersion
	 */
	public Integer getItemRequestVersion() {
		if (itemRequestVersion == null) {
			itemRequestVersion = 0;
		}
		return itemRequestVersion;
	}

	/**
	 * @return the serviceVersion
	 */
	public Integer getServiceRequestVersion() {
		if (serviceRequestVersion == null) {
			serviceRequestVersion = 0;
		}
		return serviceRequestVersion;
	}

	/**
	 * @return the customerCreationDate
	 */
	public Date getCustomerCreationDate() {
		return customerCreationDate;
	}

	/**
	 * @return the customerModificationDate
	 */
	public Date getCustomerModificationDate() {
		return customerModificationDate;
	}

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * @return the services
	 */
	public ArrayList<Service> getServices() {
		return services;
	}
	
	/**
	 * @return the itemRequests
	 */
	public ArrayList<ItemRequest> getItemRequests() {
		if (itemRequests == null) {
			itemRequests = new ArrayList<ItemRequest>();
		}
		return itemRequests;
	}
	
	/**
	 * @return the serviceRequests
	 */
	public ArrayList<ServiceRequest> getServiceRequests() {
		if (serviceRequests == null) {
			serviceRequests = new ArrayList<ServiceRequest>();
		}
		return serviceRequests;
	}
	
	/**
     * Compare this customer with another Customer
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this Customer, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if ( !(o instanceof Customer ) ) return false;
        Customer c = (Customer) o;
        return KeyFactory.keyToString(this.getKey())
                .equals(KeyFactory.keyToString(c.getKey()));
    }

    /**
     * Set Customer name.
     * @param customerName
     * 			: customer name
     * @throws MissingRequiredFieldsException
     */
    public void setCustomerName(String customerName)
    		throws MissingRequiredFieldsException {
    	if (customerName == null || customerName.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Customer name is missing.");
    	}
    	this.customerName = customerName;
    	this.customerModificationDate = new Date();
    }
    
    /**
     * Set Customer phone number.
     * @param customerPhoneNumber
     * 			: customerPhoneNumber
     * @throws MissingRequiredFieldsException
     */
    public void setCustomerPhoneNumber(PhoneNumber customerPhoneNumber) {
    	this.customerPhoneNumber = customerPhoneNumber;
    	this.customerModificationDate = new Date();
    }
    
    /**
     * Set Customer identityNumber.
     * @param customerIdentityNumber
     * 			: customer identityNumber
     * @throws MissingRequiredFieldsException
     */
    public void setCustomerIdentityNumber(String customerIdentityNumber) {
    	this.customerIdentityNumber = customerIdentityNumber;
    	this.customerModificationDate = new Date();
    }
    

	/**
	 * @param customerNickname the customerNickname to set
	 */
	public void setCustomerNickname(String customerNickname) {
		this.customerNickname = customerNickname;
		this.customerModificationDate = new Date();
	}

	/**
	 * @param customerSerialNumber the customerSerialNumber to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setCustomerSerialNumber(String customerSerialNumber) 
			throws MissingRequiredFieldsException {
		if (customerSerialNumber == null || 
				customerSerialNumber.trim().isEmpty()) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Customer SerialNumber is missing.");
    	}
		this.customerSerialNumber = customerSerialNumber;
		this.customerModificationDate = new Date();
	}
    
    /**
     * Set Customer village
     * @param village
     * 			: village ID
     * @throws MissingRequiredFieldsException 
     */
    public void setVillage(Key village) 
    		throws MissingRequiredFieldsException {
    	
    	if (village == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Customer name is missing.");
    	}
    	this.village = village;
    	this.customerModificationDate = new Date();
    }
    
    /**
     * Set Customer address
     * @param customerAddress
     * 			: customer address
     */
    public void setCustomerAddress(PostalAddress customerAddress) {
    	this.customerAddress = customerAddress;
    	this.customerModificationDate = new Date();
    }

    /**
     * Set Customer logo.
     * @param customerLogo
     * 			: customer logo blob key
     */
    public void setCustomerLogo(BlobKey customerLogo) {
    	this.customerLogo = customerLogo;
    	this.customerModificationDate = new Date();
    }
    
	/**
	 * @param customerStatus the customerStatus to set
	 * @throws MissingRequiredFieldsException 
	 */
	public void setCustomerStatus(Status customerStatus) 
			throws MissingRequiredFieldsException {
		
		if (customerStatus == null) {
    		throw new MissingRequiredFieldsException(this.getClass(), 
    				"Customer Status is missing.");
    	}
		
		this.customerStatus = customerStatus;
		this.customerModificationDate = new Date();
	}
    
    /**
     * Set Customer comments.
     * @param customerComments
     * 			: customer comments
     */
    public void setCustomerComments(String customerComments) {
    	this.customerComments = customerComments;
    	this.customerModificationDate = new Date();
    }
    
    /**
     * Add a new Item to the customer.
     * @param item
     * 			: new Item to be added
     */
    public void addItem(Item item) {
    	this.items.add(item);
    }
    
    /**
     * Add a new Service to the customer.
     * @param service
     * 			: new Service to be added
     */
    public void addService(
    		Service service) {
    	this.services.add(service);
    }
    
    /**
     * Add a new ItemRequest to the customer.
     * @param itemRequest
     * 			: new ItemRequest to be added
     */
    public void addItemRequest(ItemRequest itemRequest) {
		if (itemRequests == null) {
			itemRequests = new ArrayList<ItemRequest>();
		}
    	this.itemRequests.add(itemRequest);
    }
    
    /**
     * Add a new ServiceRequest to the customer.
     * @param serviceRequest
     * 			: new ServiceRequest to be added
     */
    public void addServiceRequest(
    		ServiceRequest serviceRequest) {
		if (serviceRequests == null) {
			serviceRequests = new ArrayList<ServiceRequest>();
		}
    	this.serviceRequests.add(serviceRequest);
    }

    /**
     * Remove a Item from the customer.
     * @param item
     * 			: Item to be removed
     * @throws InexistentObjectException
     */
    public void removeItem(Item item) 
    		throws InexistentObjectException {
    	if (!items.remove(item)) {
    		throw new InexistentObjectException(
    				Item.class, "Item not found!");
    	}
    }
    
    /**
     * Remove a Service from the customer.
     * @param service
     * 			: Service to be removed
     * @throws InexistentObjectException
     */
    public void removeService(
    		Service service) 
    		throws InexistentObjectException {
    	if (!services.remove(service)) {
    		throw new InexistentObjectException(
    				Service.class, 
    				"Service not found!");
    	}
    }
    
    /**
     * Remove a ItemRequest from the customer.
     * @param itemRequest
     * 			: ItemRequest to be removed
     * @throws InexistentObjectException
     */
    public void removeItemRequest(ItemRequest itemRequest) 
    		throws InexistentObjectException {
    	if (!itemRequests.remove(itemRequest)) {
    		throw new InexistentObjectException(
    				ItemRequest.class, "ItemRequest not found!");
    	}
    }
    
    /**
     * Remove a ServiceRequest from the customer.
     * @param serviceRequest
     * 			: ServiceRequest to be removed
     * @throws InexistentObjectException
     */
    public void removeServiceRequest(
    		ServiceRequest serviceRequest) 
    		throws InexistentObjectException {
    	if (!serviceRequests.remove(serviceRequest)) {
    		throw new InexistentObjectException(
    				ServiceRequest.class, 
    				"ServiceRequest not found!");
    	}
    }
    
    /**
     * Update the Item version number by 1.
     */
    public void updateItemVersion() {
    	itemVersion++;
    }
    
    /**
     * Update the Service Version number by 1.
     */
    public void updateServiceVersion() {
    	serviceVersion++;
    }
    
    /**
     * Update the ItemRequest version number by 1.
     */
    public void updateItemRequestVersion() {
		if (itemRequestVersion == null) {
			itemRequestVersion = 0;
		}
    	itemRequestVersion++;
    }
    
    /**
     * Update the ServiceRequest Version number by 1.
     */
    public void updateServiceRequestVersion() {
		if (serviceRequestVersion == null) {
			serviceRequestVersion = 0;
		}
    	serviceRequestVersion++;
    }
}
