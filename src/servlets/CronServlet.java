/*
Copyright (c) 2014, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet class is used for serving Cron requests.
 * 
 */

@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {

    private static final Logger log = 
        Logger.getLogger(CronServlet.class.getName());
    
    // JSP file locations
    private static final String thisServlet = "/cron";
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	
	    // Lets check the action and parameters returned
	    String action = req.getParameter("action");
	    
	    try {
		    // Testing
		    if (action.equalsIgnoreCase("testing")) {
		    	log.log(Level.INFO, "Cron test - every 24 hours");
		    }
		    // No more choices
	  		else {
	  			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
	  			return;
	  		}
	    }
        catch (Exception ex) {
            log.log(Level.SEVERE, ex.toString());
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            		"Internal server error.");
            return;
        }
	}
}