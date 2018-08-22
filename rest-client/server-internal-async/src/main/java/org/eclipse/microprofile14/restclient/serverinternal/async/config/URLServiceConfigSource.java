/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.restclient.serverinternal.async.config;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile14.restclient.serverinternal.async.client.HelloService;

/**
 * This config source provides the URL MP Rest Client needs to locate the endpoint corresponding
 * to the {@link HelloService} interface.
 *
 * @author Arjan Tijms
 *
 */
public class URLServiceConfigSource implements ConfigSource {

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put(HelloService.class +  "/mp-rest/url", "");
        return properties;
    }

    @Override
    public String getValue(String propertyName) {
        if (propertyName.equals(HelloService.class.getName() +  "/mp-rest/url")) {

            try {
                HttpServletRequest request = CDI.current().select(HttpServletRequest.class).get();

                StringBuffer url = request.getRequestURL();
                String uri = request.getRequestURI();

                return url.substring(0, url.length() - uri.length() + request.getContextPath().length()) + "/";
            } catch (Exception e) {
                // Ignore, thrown when the key is requested ahead of a request
            }
        }

        return null;
    }

    @Override
    public String getName() {
        return "URLServiceConfigSource";
    }

}
