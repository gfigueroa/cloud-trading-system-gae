/*
Copyright (c) 2011, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DateManager;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import datastore.Customer;
import datastore.CustomerManager;
import datastore.Item;
import datastore.ItemManager;
import datastore.ItemRequest;
import datastore.ItemRequestManager;
import datastore.Service;
import datastore.ServiceManager;
import datastore.ServiceRequest;
import datastore.ServiceRequestManager;
import datastore.User;
import datastore.User.UserType;
import exceptions.MissingRequiredFieldsException;

/**
 * This servlet class is used to add, delete and update
 * Customer-owned objects in the system.
 * 
 */

@SuppressWarnings("serial")
public class ManageCustomerServlet extends HttpServlet {

    private static final Logger log = 
        Logger.getLogger(ManageCustomerServlet.class.getName());
    
    private static final String addItemJSP = 
    		"/admin/addItem.jsp";
    private static final String editItemJSP = 
    		"/admin/editItem.jsp";
    private static final String listItemJSP = 
    		"/admin/listItem.jsp";
    
    private static final String addServiceJSP = 
    		"/admin/addService.jsp";
    private static final String editServiceJSP = 
    		"/admin/editService.jsp";
    private static final String listServiceJSP = 
    		"/admin/listService.jsp";
    
    private static final String addItemRequestJSP = 
    		"/admin/addItem.jsp";
    private static final String editItemRequestJSP = 
    		"/admin/editItemRequest.jsp";
    private static final String listItemRequestJSP = 
    		"/admin/listItem.jsp";
    
    private static final String addServiceRequestJSP = 
    		"/admin/addService.jsp";
    private static final String editServiceRequestJSP = 
    		"/admin/editServiceRequest.jsp";
    private static final String listServiceRequestJSP = 
    		"/admin/listService.jsp";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        
    	HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        
        // Check that a User is carrying out the action
	    if (user == null) {
	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }
    	
    	// Lets check the action required by the jsp
        String action = req.getParameter("action");
        
        // Common parameters
        String successURL = "";
        String failURL = "";
        String additionalParameters = "";

        try {
	        // DELETE
	        if (action.equals("delete")) {
	            // Retrieve the key     
	        	String keyString = req.getParameter("k");
	
	        	// Retrieve the object type to delete
	        	String type = req.getParameter("type");
	
	        	// Delete Item
	        	if (type.equalsIgnoreCase("item")) {
	        		
	                // Check that a Customer or Admin is carrying out the action
	        	    if (user.getUserType() != User.UserType.CUSTOMER &&
	        	    		user.getUserType() != User.UserType.ADMINISTRATOR) {
	        	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        	        return;
	        	    }
	        	    
	            	successURL = listItemJSP;
	            	failURL = successURL;
	        		
	        		Key key = KeyFactory.stringToKey(keyString);
	        		ItemManager.deleteItem(key);
	        	}
	        	// Delete Service
	        	else if (type.equalsIgnoreCase("service")) {
	        		
	                // Check that a Customer or Admin is carrying out the action
	        	    if (user.getUserType() != User.UserType.CUSTOMER &&
	        	    		user.getUserType() != User.UserType.ADMINISTRATOR) {
	        	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        	        return;
	        	    }
	        	    
	            	successURL = listServiceJSP;
	            	failURL = successURL;
	        		
	        		Key key = KeyFactory.stringToKey(keyString);
	        		ServiceManager.deleteService(key);
	        	}
	        	// Delete ItemRequest
	        	else if (type.equalsIgnoreCase("itemRequest")) {
	        		
	                // Check that a Customer or Admin is carrying out the action
	        	    if (user.getUserType() != User.UserType.CUSTOMER &&
	        	    		user.getUserType() != User.UserType.ADMINISTRATOR) {
	        	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        	        return;
	        	    }
	        	    
	            	successURL = listItemRequestJSP;
	            	failURL = successURL;
	        		
	        		Key key = KeyFactory.stringToKey(keyString);
	        		ItemRequestManager.deleteItemRequest(key);
	        	}
	        	// Delete Service
	        	else if (type.equalsIgnoreCase("serviceRequest")) {
	        		
	                // Check that a Customer or Admin is carrying out the action
	        	    if (user.getUserType() != User.UserType.CUSTOMER &&
	        	    		user.getUserType() != User.UserType.ADMINISTRATOR) {
	        	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        	        return;
	        	    }
	        	    
	            	successURL = listServiceRequestJSP;
	            	failURL = successURL;
	        		
	        		Key key = KeyFactory.stringToKey(keyString);
	        		ServiceRequestManager.deleteServiceRequest(key);
	        	}
	        	// No more choices
	      		else {
	      			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
	      			return;
	      		}
	
	        	// Success URL
	        	resp.sendRedirect(successURL + "?msg=success&action=" + action +
	        			additionalParameters);
	        }
	        // No more choices
	  		else {
	  			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
	  			return;
	  		}
        }
//        catch (InexistentObjectException ioe) {
//        	resp.sendRedirect(failURL + "?etype=InexistentObject" + additionalParameters);
//            return;
//        }
        catch (Exception ex) {
            log.log(Level.SEVERE, ex.toString());
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
                throws IOException {
    	
    	HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        
        // Check that a User is carrying out the action
	    if (user == null) {
	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }
	    
	    // Check the Customer involved
	    Customer customer = null;
	    if (user.getUserType() == UserType.CUSTOMER) {
	    	customer = CustomerManager.getCustomer(user);
	    }
	    else if (user.getUserType() == UserType.ADMINISTRATOR) {
	    	String customerKeyString = req.getParameter("customerId");
	    	if (customerKeyString != null && !customerKeyString.isEmpty()) {
	    		Key customerKey = KeyFactory.stringToKey(customerKeyString);
		    	customer = CustomerManager.getCustomer(customerKey);
	    	}
	    }
    	
        // Lets check the action required by the jsp
        String action = req.getParameter("action");
        
        // Common parameters
        String type = req.getParameter("type");
        String successURL = "";
        String failURL = "";
        String additionalParameters = "";
        
        // ADD
        if (action.equals("add")) {
            try {

                // Add Item
                if (type.equalsIgnoreCase("item")) {
                	
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
                	
	                successURL = listItemJSP;
	                failURL = addItemJSP;
            	    
                	addOrUpdateItem(req, null, true, customer);
	            }
                // Add Service
                else if (type.equalsIgnoreCase("service")) {
                	
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
                	
	                successURL = listServiceJSP;
	                failURL = addServiceJSP;
            	    
                	addOrUpdateService(req, null, true, customer);
	            }
                // Add ItemRequest
                else if (type.equalsIgnoreCase("itemRequest")) {
                	
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
                	
	                successURL = listItemRequestJSP;
	                failURL = addItemRequestJSP;
            	    
                	addOrUpdateItemRequest(req, null, true, customer);
	            }
                // Add ServiceRequest
                else if (type.equalsIgnoreCase("serviceRequest")) {
                	
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
                	
	                successURL = listServiceRequestJSP;
	                failURL = addServiceRequestJSP;
            	    
                	addOrUpdateServiceRequest(req, null, true, customer);
	            }
                // No more choices
          		else {
          			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
          			return;
          		}
	        	
	        	// Success URL
	        	resp.sendRedirect(successURL + "?msg=success&action=" + action +
	        			additionalParameters);
            }
            catch (MissingRequiredFieldsException mrfe) {
                resp.sendRedirect(failURL + "?etype=MissingInfo" + additionalParameters);
                return;
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, ex.toString());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
	    }
        // UPDATE
	    else if (action.equals("update")) {
	    	
	    	// Common parameters
	    	String keyString = req.getParameter("k");
	    	Key key = null;
	    	if (keyString != null) {
	    		key = KeyFactory.stringToKey(keyString);
	    	}
	    	
	    	try {
	    		
            	// Update Item
            	if (type.equalsIgnoreCase("item")) {
            		
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
            		
                	successURL = editItemJSP;
                	failURL = editItemJSP;
            		
            		addOrUpdateItem(req, key, false, null);
            	}
            	// Update Service
            	else if (type.equalsIgnoreCase("service")) {
            		
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
            		
                	successURL = editServiceJSP;
                	failURL = editServiceJSP;
            		
            		addOrUpdateService(req, key, false, null);
            	}
            	// Update ItemRequest
            	else if (type.equalsIgnoreCase("itemRequest")) {
            		
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
            		
                	successURL = editItemRequestJSP;
                	failURL = editItemRequestJSP;
            		
            		addOrUpdateItemRequest(req, key, false, null);
            	}
            	// Update Service
            	else if (type.equalsIgnoreCase("serviceRequest")) {
            		
                    // Check that an Administrator or Customer is carrying out the action
            	    if (user.getUserType() != User.UserType.ADMINISTRATOR &&
            	    		user.getUserType() != User.UserType.CUSTOMER) {
            	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            	        return;
            	    }
            		
                	successURL = editServiceRequestJSP;
                	failURL = editServiceRequestJSP;
            		
            		addOrUpdateServiceRequest(req, key, false, null);
            	}
            	// No more choices
          		else {
          			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
          			return;
          		}
            	
    	    	// If success
                resp.sendRedirect(successURL + "?k=" + keyString + 
                		"&msg=success&action=" + action +
                		additionalParameters);
            }
            catch (MissingRequiredFieldsException mrfe) {
                resp.sendRedirect(failURL + "?etype=MissingInfo&k="
                        + keyString + additionalParameters);
                return;
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, ex.toString());
                ex.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        // No more choices
  		else {
  			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
  			return;
  		}
    }

    /**
     * Add or Update Item
     * @param req: the HTTPServletRequest
     * @param key: the Item key (null if Add)
     * @param add: whether to Add object or not
     * @param customer: the Customer involved (null if Edit)
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateItem(HttpServletRequest req, 
    		Key key, boolean add, Customer customer) 
    		throws MissingRequiredFieldsException {

    	String itemTypeString = req.getParameter("itemTypeId");
    	Long itemType = null;
    	if (!itemTypeString.isEmpty()) {
    		itemType = Long.parseLong(itemTypeString);
    	}
    	
    	String itemName = 
        		req.getParameter("itemName");
        String itemDescription = 
        		req.getParameter("itemDescription");
        
        String itemPriceString = 
        		req.getParameter("itemPrice");
        Double itemPrice = null;
        if (!itemPriceString.isEmpty()) {
        	itemPrice = Double.parseDouble(itemPriceString);
        }
        
        String itemIsForWhat =
        		req.getParameter("itemIsForWhat");
        Boolean itemIsForDonation = false;
        Boolean itemIsForExchange = false;
        if (itemIsForWhat.equalsIgnoreCase("donation")) {
        	itemIsForDonation = true;
        }
        else {
        	itemIsForExchange = true;
        }
        
        //BlobKey itemImage1Key = BlobUtils.assignBlobKey(req, "itemImage1", 
        //		blobstoreService);
        BlobKey itemImage1Key = null; //TODO: Fix image upload
        
        String itemManufacturingTimeString = req.getParameter("itemManufacturingTime");
        Date itemManufacturingTime = null;
        if (itemManufacturingTimeString != null) {
        	itemManufacturingTime = DateManager.getDateValue(itemManufacturingTimeString);
        }

        String itemAcquisitionTimeString = req.getParameter("itemAcquisitionTime");
        Date itemAcquisitionTime = null;
        if (itemAcquisitionTimeString != null) {
        	itemAcquisitionTime = DateManager.getDateValue(itemAcquisitionTimeString);
        }
        
        String itemExpirationTimeString = req.getParameter("itemExpirationTime");
        Date itemExpirationTime = null;
        if (!itemExpirationTimeString.isEmpty()) {
        	int itemExpirationDays = Integer.parseInt(itemExpirationTimeString);
        	itemExpirationTime = 
        			DateManager.subtractDaysFromDate(new Date(), -itemExpirationDays);
        }

        String itemComments =
        		req.getParameter("itemComments");
        
        if (add) {
	        Item item = 
	        		new Item(itemType,
	        	    		customer.getKey(),
	        	    		itemName, 
	        	    		itemDescription, 
	        	    		itemPrice,
	        	    		itemIsForDonation,
	        	    		itemIsForExchange,
	        	    		itemImage1Key,
	        	    		itemManufacturingTime,
	        	    		itemAcquisitionTime,
	        	    		itemExpirationTime,
	        	    		itemComments) ;
	        ItemManager.putItem(customer.getKey(), item);
        }
        else {
            ItemManager.updateItemAttributes(
            		key,
        			itemType,
            	    itemName, 
            		itemDescription, 
            		itemPrice,
            		itemIsForDonation,
            		itemIsForExchange,
            		itemImage1Key,
            		itemManufacturingTime,
            		itemAcquisitionTime,
            		itemExpirationTime,
            		itemComments) ;
        }
    }
    
    /**
     * Add or Update Service
     * @param req: the HTTPServletRequest
     * @param key: the Item key (null if Add)
     * @param add: whether to Add object or not
     * @param customer: the Customer involved (null if Edit)
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateService(HttpServletRequest req, 
    		Key key, boolean add, Customer customer) 
    		throws MissingRequiredFieldsException {

    	String serviceTypeString = req.getParameter("serviceTypeId");
    	Long serviceType = null;
    	if (!serviceTypeString.isEmpty()) {
    		serviceType = Long.parseLong(serviceTypeString);
    	}

        String serviceDescription = 
        		req.getParameter("serviceDescription");

        String serviceComments =
        		req.getParameter("serviceComments");
        
        if (add) {
        	Service service = 
	        		new Service(serviceType,
	        	    		customer.getKey(),
	        	    		serviceDescription, 
	        	    		serviceComments);
        	ServiceManager.putService(customer.getKey(), service);
        }
        else {
        	ServiceManager.updateServiceAttributes(
            		key,
            		serviceType,
            	    serviceDescription, 
            	    serviceComments);
        }
    }
    
    /**
     * Add or Update ItemRequest
     * @param req: the HTTPServletRequest
     * @param key: the Item key (null if Add)
     * @param add: whether to Add object or not
     * @param customer: the Customer involved (null if Edit)
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateItemRequest(HttpServletRequest req, 
    		Key key, boolean add, Customer customer) 
    		throws MissingRequiredFieldsException {

    	String itemTypeString = req.getParameter("itemTypeId");
    	Long itemType = null;
    	if (!itemTypeString.isEmpty()) {
    		itemType = Long.parseLong(itemTypeString);
    	}

        String itemRequestDescription = 
        		req.getParameter("itemDescription");

        String itemRequestComments =
        		req.getParameter("itemComments");
        
        if (add) {
	        ItemRequest itemRequest = 
	        		new ItemRequest(itemType,
	        	    		customer.getKey(),
	        	    		itemRequestDescription, 
	        	    		itemRequestComments) ;
	        ItemRequestManager.putItemRequest(customer.getKey(), itemRequest);
        }
        else {
            ItemRequestManager.updateItemRequestAttributes(
            		key,
        			itemType,
            		itemRequestDescription, 
            		itemRequestComments) ;
        }
    }
    
    /**
     * Add or Update ServiceRequest
     * @param req: the HTTPServletRequest
     * @param key: the Item key (null if Add)
     * @param add: whether to Add object or not
     * @param customer: the Customer involved (null if Edit)
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateServiceRequest(HttpServletRequest req, 
    		Key key, boolean add, Customer customer) 
    		throws MissingRequiredFieldsException {

    	String serviceTypeString = req.getParameter("serviceTypeId");
    	Long serviceType = null;
    	if (!serviceTypeString.isEmpty()) {
    		serviceType = Long.parseLong(serviceTypeString);
    	}

        String serviceRequestDescription = 
        		req.getParameter("serviceDescription");

        String serviceRequestComments =
        		req.getParameter("serviceComments");
        
        if (add) {
        	ServiceRequest serviceRequest = 
	        		new ServiceRequest(serviceType,
	        	    		customer.getKey(),
	        	    		serviceRequestDescription, 
	        	    		serviceRequestComments);
        	ServiceRequestManager.putServiceRequest(customer.getKey(), serviceRequest);
        }
        else {
        	ServiceRequestManager.updateServiceRequestAttributes(
            		key,
            		serviceType,
            	    serviceRequestDescription, 
            	    serviceRequestComments);
        }
    }
    
}