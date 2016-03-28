/*
Copyright (c) 2011, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package webservices;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import webservices.external_resources.CustomerProfileResource;
import webservices.external_resources.SystemResource;

/**
 * This class represents an instance of the Restlet Application
 */

public class ExternalApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls
     * @return A router instance
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a
        // new instance of each Resource
        Router router = new Router(getContext());
        
        // Define route to 1.1 SystemResource
        router.attach("/system", SystemResource.class);
        
        // Define route to CustomerProfileResource
        router.attach("/customerProfile/{queryinfo}", 
        		CustomerProfileResource.class);
        
        return router;
    }

}
