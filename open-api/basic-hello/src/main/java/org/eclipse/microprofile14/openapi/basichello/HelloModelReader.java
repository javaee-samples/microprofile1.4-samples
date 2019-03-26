/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.openapi.basichello;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASModelReader;
import org.eclipse.microprofile.openapi.models.OpenAPI;

/**
 * Generates a base model to be used by the OpenAPI.
 */
public class HelloModelReader implements OASModelReader {

    @Override
    public OpenAPI buildModel() {
        return OASFactory.createObject(OpenAPI.class);
    }

}