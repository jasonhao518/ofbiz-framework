/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.apache.ofbiz.webapp.control;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@WebListener
public class WebAppServletContextListener extends ContextLoaderListener implements ServletContextListener {
//	private Map<String,String> contextPackageMap = new HashMap<>();
//	public WebAppServletContextListener() {
//		contextPackageMap.put("/ecommerce", "org.apache.ofbiz.ecommerce");
//		contextPackageMap.put("/storefront", "org.apache.ofbiz.storefront");
//	}
	protected Class<?> determineContextClass(ServletContext servletContext) {
		return AnnotationConfigWebApplicationContext.class;
	}
	@Override
	protected void customizeContext(ServletContext sc, ConfigurableWebApplicationContext wac) {
		((AnnotationConfigWebApplicationContext)wac).scan("org.apache.ofbiz.base.config");
		if(sc.getContextPath().startsWith("/")) {
			String pkg = "org.apache.ofbiz." + sc.getContextPath().substring(1);//contextPackageMap.get(sc.getContextPath());
			if(pkg!=null) {
				((AnnotationConfigWebApplicationContext)wac).scan(pkg);
			}
		}

	}

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	super.contextInitialized(sce);
//        ServletContext servletContext = sce.getServletContext();
//        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
//        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
//        sessionCookieConfig.setHttpOnly(true);
//        sessionCookieConfig.setSecure(true);
//        sessionCookieConfig.setComment("Created by Apache OFBiz WebAppServletContextListener");
//        String cookieDomain = UtilProperties.getPropertyValue("url", "cookie.domain", "");
//        if (cookieDomain.length() > 0) sessionCookieConfig.setDomain(cookieDomain);
//        sessionCookieConfig.setMaxAge(60 * 60 * 24 * 365);
//        sessionCookieConfig.setPath("/");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO For now we don't need anything here
    	super.contextDestroyed(sce);
    }

}
