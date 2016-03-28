/*
Copyright (c) 2011, 智慧人科技服務股份有限公司 (Smart Personalized Service Technology, Inc.) 
All rights reserved.
*/

package webservices.datastore_simple;

import java.io.Serializable;

/**
 * This class represents a simple version of the System table.
 * It is kept simple to return only some information to mobile apps.
 * 
 */

@SuppressWarnings("serial")
public class SystemSimple implements Serializable {

	public Long key;
	public Integer customerListVersion;
	public Integer itemCategoryListVersion;
	public Integer itemTypeListVersion;
	public Integer serviceTypeListVersion;
	public String  oldestAppVersionSupported;
	public String  systemTime;
    /**
     * MenuItemSimple constructor.
     * @param key:
     * 			: system key
     * @param customerListVersion
     * 			: customer list version
     * @param itemCategoryListVersion
     * 			: ItemCategory list version
     * @param itemTypeListVersion
     * 			: ItemType list version
     * @param serviceTypeListVersion
     * 			: ServiceType list version
     * @param oldestAppVersionSupported
     * 			: oldest app version supported by this server version
     * @param systemTime
     * 			: system time
     */
    public SystemSimple(
    		Long key, 
    		Integer customerListVersion, 
    		Integer itemCategoryListVersion, 
    		Integer itemTypeListVersion,
    		Integer serviceTypeListVersion, 
    		String oldestAppVersionSupported,
    		String systemTime){
    	this.key = key;
    	this.customerListVersion = customerListVersion;
    	this.itemCategoryListVersion = itemCategoryListVersion;
    	this.itemTypeListVersion = itemTypeListVersion;
    	this.serviceTypeListVersion = serviceTypeListVersion;
    	this.oldestAppVersionSupported = oldestAppVersionSupported;
    	this.systemTime = systemTime;
    }
    /**
     * Compare this system with another system
     * @param o
     * 			: the object to compare
     * @returns true if the object to compare is equal to this System, 
	 *			false otherwise
     */
    @Override
    public boolean equals(Object o){
        if ( this == o ) return true;
        if (!(o instanceof SystemSimple ) ) return false;
        SystemSimple system = (SystemSimple) o;
        return key.equals(system.key);
    }

}