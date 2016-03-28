/*
Copyright (c) 2011, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import datastore.City;
import datastore.CityManager;
import datastore.District;
import datastore.DistrictManager;
import datastore.ItemCategory;
import datastore.ItemCategoryManager;
import datastore.ItemType;
import datastore.ItemTypeManager;
import datastore.ServiceType;
import datastore.ServiceTypeManager;
import datastore.SystemManager;
import datastore.User;
import datastore.Village;
import datastore.VillageManager;
import exceptions.MissingRequiredFieldsException;

/**
 * This servlet class is used to add, delete and update
 * global objects in the system.
 * 
 */

@SuppressWarnings("serial")
public class ManageGlobalObjectsServlet extends HttpServlet {

    private static final Logger log = 
        Logger.getLogger(ManageGlobalObjectsServlet.class.getName());
    
    // JSP file locations
    private static final String addItemCategoryJSP = "/admin/addCategory.jsp";
    private static final String editItemCategoryJSP = "/admin/editCategory.jsp";
    private static final String listItemCategoryJSP = "/admin/listCategory.jsp";
    
    private static final String addItemTypeJSP = "/admin/addType.jsp";
    private static final String editItemTypeJSP = "/admin/editType.jsp";
    private static final String listItemTypeJSP = "/admin/listType.jsp";
    
    private static final String addServiceTypeJSP = "/admin/addServiceType.jsp";
    private static final String editServiceTypeJSP = "/admin/editServiceType.jsp";
    private static final String listServiceTypeJSP = "/admin/listServiceType.jsp";
    
    private static final String addCityJSP = "/admin/addCity.jsp";
    private static final String editCityJSP = "/admin/editCity.jsp";
    private static final String listCityJSP = "/admin/listCity.jsp";
    
    private static final String addDistrictJSP = "/admin/addDistrict.jsp";
    private static final String editDistrictJSP = "/admin/editDistrict.jsp";
    private static final String listDistrictJSP = "/admin/listDistrict.jsp";
    
    private static final String addVillageJSP = "/admin/addVillage.jsp";
    private static final String editVillageJSP = "/admin/editVillage.jsp";
    private static final String listVillageJSP = "/admin/listVillage.jsp";

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        
    	HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        
        // Check that an administrator is carrying out the action
	    if (user == null || user.getUserType() != User.UserType.ADMINISTRATOR) {
	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }
    	
    	// Lets check the action required by the jsp
        String action = req.getParameter("action");
        
        // Common parameters
        String successURL = "";

        // DELETE
        if (action.equals("delete")) {
            // Retrieve the key     
        	String keyString = req.getParameter("k");

        	// Retrieve the object type to delete
        	String type = req.getParameter("type");

        	// Delete ItemCategory
        	if (type.equalsIgnoreCase("itemCategory")) {
            	successURL = listItemCategoryJSP;
        		
        		Long key = Long.parseLong(keyString);
        		ItemCategoryManager.deleteItemCategory(key);
            	SystemManager.updateItemCategoryListVersion();
        	}
        	// Delete ItemType
        	else if (type.equalsIgnoreCase("itemType")) {
            	successURL = listItemTypeJSP;
        		
            	Long key = Long.parseLong(keyString);
        		ItemTypeManager.deleteItemType(key);
            	SystemManager.updateItemTypeListVersion();
        	}
        	// Delete ServiceType
        	else if (type.equalsIgnoreCase("serviceType")) {
            	successURL = listServiceTypeJSP;
        		
            	Long key = Long.parseLong(keyString);
        		ServiceTypeManager.deleteServiceType(key);
            	SystemManager.updateServiceTypeListVersion();
        	}
        	// Delete City
        	else if (type.equalsIgnoreCase("city")) {
            	successURL = listCityJSP;
        		
            	Key key = KeyFactory.stringToKey(keyString);
        		CityManager.deleteCity(key);
        	}
        	// Delete District
        	else if (type.equalsIgnoreCase("district")) {
            	successURL = listDistrictJSP;
        		
            	Key key = KeyFactory.stringToKey(keyString);
            	DistrictManager.deleteDistrict(key);
        	}
        	// Delete Village
        	else if (type.equalsIgnoreCase("village")) {
        		successURL = listVillageJSP;
        		
        		Key key = KeyFactory.stringToKey(keyString);
        		VillageManager.deleteVillage(key);
        	}
        	// No more choices
      		else {
      			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
      			return;
      		}

        	// Success URL
        	resp.sendRedirect(successURL + "?msg=success&action=" + action);
        }
        // No more choices
  		else {
  			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
  			return;
  		}
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
                throws IOException {
    	
    	HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        
        // Check that an administrator is carrying out the action
	    if (user == null || user.getUserType() != User.UserType.ADMINISTRATOR) {
	    	resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        return;
	    }
    	
        // Lets check the action required by the jsp
        String action = req.getParameter("action");
        
        // Common parameters
        String type = req.getParameter("type");
        String successURL = "";
        String failURL = "";
        
        // ADD
        if (action.equals("add")) {
            try {
	            
            	// Add ItemCategory
                if (type.equalsIgnoreCase("itemCategory")) {
	                successURL = listItemCategoryJSP;
	                failURL = addItemCategoryJSP;
                	
                	addOrUpdateItemCategory(req, null, true);
	            }
                // Add ItemType
                else if (type.equalsIgnoreCase("itemType")) {
	                successURL = listItemTypeJSP;
	                failURL = addItemTypeJSP;
                	
                	addOrUpdateItemType(req, null, true);
	            }
                // Add ServiceType
                else if (type.equalsIgnoreCase("serviceType")) {
	                successURL = listServiceTypeJSP;
	                failURL = addServiceTypeJSP;
                	
                	addOrUpdateServiceType(req, null, true);
	            }
                // Add City
                else if (type.equalsIgnoreCase("city")) {
	                successURL = listCityJSP;
	                failURL = addCityJSP;
                	
                	addOrUpdateCity(req, null, true); 
	            }
                // Add District
                else if (type.equalsIgnoreCase("district")) {
	                successURL = listDistrictJSP;
	                failURL = addDistrictJSP;
                	
                	addOrUpdateDistrict(req, null, true); 
	            }
	            // Add Village
                else if (type.equalsIgnoreCase("village")) {
                	successURL = listVillageJSP;
                	failURL = addVillageJSP;
                	
                	addOrUpdateVillage(req, null, true);
                }
                // No more choices
          		else {
          			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
          			return;
          		}
	        	
	        	// Success URL
	        	resp.sendRedirect(successURL + "?msg=success&action=" + action);
            }
            catch (MissingRequiredFieldsException mrfe) {
                resp.sendRedirect(failURL + "?etype=MissingInfo");
                return;
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, ex.toString());
                ex.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
	    }
        // UPDATE
	    else if (action.equals("update")) {
	    	String keyString = req.getParameter("k");
	    	try {
	    		
	    		// Update ItemCategory
            	if (type.equalsIgnoreCase("itemCategory")) {
                	successURL = editItemCategoryJSP;
                	failURL = editItemCategoryJSP;
            		
            		Long key = Long.parseLong(keyString);
            		addOrUpdateItemCategory(req, key, false);
            	}
            	// Update ItemType
            	else if (type.equalsIgnoreCase("itemType")) {
                	successURL = editItemTypeJSP;
                	failURL = editItemTypeJSP;
            		
                	Long key = Long.parseLong(keyString);
            		addOrUpdateItemType(req, key, false);
            	}
            	// Update ServiceType
            	else if (type.equalsIgnoreCase("serviceType")) {
                	successURL = editServiceTypeJSP;
                	failURL = editServiceTypeJSP;
            		
                	Long key = Long.parseLong(keyString);
            		addOrUpdateServiceType(req, key, false);
            	}
            	// Update City
            	else if (type.equalsIgnoreCase("city")) {
                	successURL = editCityJSP;
                	failURL = editCityJSP;
            		
                	Key key = KeyFactory.stringToKey(keyString);
            		addOrUpdateCity(req, key, false);
            	}
            	// Update District
            	else if (type.equalsIgnoreCase("district")) {
                	successURL = editDistrictJSP;
                	failURL = editDistrictJSP;
            		
                	Key key = KeyFactory.stringToKey(keyString);
            		addOrUpdateDistrict(req, key, false);
            	}
	            // Update Village
	    		else if (type.equalsIgnoreCase("village")) {
                	successURL = editVillageJSP;
                	failURL = editVillageJSP;
	    			
	    			Key key = KeyFactory.stringToKey(keyString);
	    			addOrUpdateVillage(req, key, false);
            	}
            	// No more choices
          		else {
          			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
          			return;
          		}
            	
    	    	// If success
                resp.sendRedirect(successURL + "?k=" + keyString + 
                		"&msg=success&action=" + action);
            }
            catch (MissingRequiredFieldsException mrfe) {
                resp.sendRedirect(failURL + "?etype=MissingInfo&k="
                        + keyString);
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
     * Add or Update ItemCategory
     * @param req: the HTTPServletRequest
     * @param key: the ItemCategory key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateItemCategory(HttpServletRequest req, 
    		Long key, boolean add) 
    		throws MissingRequiredFieldsException {
        
    	String itemCategoryName = 
        		req.getParameter("itemCategoryName");
        String itemCategoryDescription = 
        		req.getParameter("itemCategoryDescription");
        String itemCategoryComments =
        		req.getParameter("itemCategoryComments");
        
        if (add) {
	        ItemCategory itemCategory = 
	        		new ItemCategory(
	        				itemCategoryName, 
	        				itemCategoryDescription,
	        				itemCategoryComments);
	        ItemCategoryManager.putItemCategory(itemCategory);
        }
        else {
        	ItemCategoryManager.updateItemCategoryAttributes(
            		key, 
            		itemCategoryName, 
            		itemCategoryDescription, 
            		itemCategoryComments);
        }
        
        // Update itemCategoryListVersion
        SystemManager.updateItemCategoryListVersion();
    }
    
    /**
     * Add or Update ItemType
     * @param req: the HTTPServletRequest
     * @param key: the ItemType key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateItemType(HttpServletRequest req, 
    		Long key, boolean add) 
    		throws MissingRequiredFieldsException {
        
    	// ItemType data
    	
    	String itemCategoryKeyString =
    			req.getParameter("itemCategoryId");
    	Long itemCategoryKey = null;
    	if (!itemCategoryKeyString.isEmpty()) {
    		itemCategoryKey = Long.parseLong(itemCategoryKeyString);
    	}
    	
    	String itemTypeName = 
        		req.getParameter("itemTypeName");
        String itemTypeDescription = 
        		req.getParameter("itemTypeDescription");
        String itemTypeComments =
        		req.getParameter("itemTypeComments");
        
        if (add) {
	        ItemType itemType = 
	        		new ItemType(itemCategoryKey,
	        	    		itemTypeName, 
	        	    		itemTypeDescription,
	        	    		itemTypeComments
	        	    		);
	        ItemTypeManager.putItemType(itemType);
        }
        else {
            ItemTypeManager.updateItemTypeAttributes(
            		key, 
            		itemCategoryKey, 
            		itemTypeName, 
            		itemTypeDescription, 
            		itemTypeComments);
        }
        
        // Update itemCategoryListVersion
        SystemManager.updateItemTypeListVersion();
    }
    
    /**
     * Add or Update ServiceType
     * @param req: the HTTPServletRequest
     * @param key: the ServiceType key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateServiceType(HttpServletRequest req, 
    		Long key, boolean add) 
    		throws MissingRequiredFieldsException {
        
    	// ServiceType data
    	String serviceTypeName = 
        		req.getParameter("serviceTypeName");
        String serviceTypeDescription = 
        		req.getParameter("serviceTypeDescription");
        String serviceTypeComments =
        		req.getParameter("serviceTypeComments");
        
        if (add) {
	        ServiceType serviceType = 
	        		new ServiceType(
	        				serviceTypeName, 
	        				serviceTypeDescription,
	        				serviceTypeComments
	        	    		);
	        ServiceTypeManager.putServiceType(serviceType);
        }
        else {
        	ServiceTypeManager.updateServiceTypeAttributes(
            		key, 
            		serviceTypeName, 
            		serviceTypeDescription, 
            		serviceTypeComments);
        }
        
        // Update itemCategoryListVersion
        SystemManager.updateItemTypeListVersion();
    }
    
    /**
     * Add or Update City
     * @param req: the HTTPServletRequest
     * @param key: the City key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateCity(HttpServletRequest req, 
    		Key key, boolean add) 
    		throws MissingRequiredFieldsException {
        
    	String cityName = req.getParameter("cityName");
        String cityComments = req.getParameter("cityComments");
        
        if (add) {
	        City city = new City(
	        		cityName,
	        		cityComments);
	        CityManager.putCity(city);
        }
        else {
            CityManager.updateCityAttributes(
            		key, 
            		cityName, 
            		cityComments);
        }
    }
    
    /**
     * Add or Update District
     * @param req: the HTTPServletRequest
     * @param key: the District key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateDistrict(HttpServletRequest req, 
    		Key key, boolean add) 
    		throws MissingRequiredFieldsException {
        
    	String cityKeyString = req.getParameter("cityId");
    	Key cityKey = null;
    	if (cityKeyString != null && !cityKeyString.isEmpty()) {
    		cityKey = KeyFactory.stringToKey(cityKeyString);
    	}
    	
    	String districtName = req.getParameter("districtName");
        String districtComments = req.getParameter("districtComments");
        
        if (add) {
        	District district = new District(
        			districtName,
        			districtComments);
        	DistrictManager.putDistrict(cityKey, district);
        }
        else {
        	DistrictManager.updateDistrictAttributes(
            		key, 
            		districtName, 
            		districtComments);
        }
    }
    
    /**
     * Add or Update Village
     * @param req: the HTTPServletRequest
     * @param key: the Village key (null if Add)
     * @param add: whether to Add object or not
     * @throws MissingRequiredFieldsException 
     */
    private void addOrUpdateVillage(HttpServletRequest req, 
    		Key key, boolean add) 
    		throws MissingRequiredFieldsException {
    	
    	String districtKeyString = req.getParameter("districtId");
    	Key districtKey = null;
    	if (districtKeyString != null && !districtKeyString.isEmpty()) {
    		districtKey = KeyFactory.stringToKey(districtKeyString);
    	}
        
    	String villageName = req.getParameter("villageName");
        String villageComments = req.getParameter("villageComments");
        
        if (add) {
	        Village village = new Village(
	        		villageName,
	        		villageComments);
	        VillageManager.putVillage(districtKey, village);
        }
        else {
            VillageManager.updateVillageAttributes(
            		key, 
            		villageName,
            		villageComments);
        }
    }

}
