/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.openapi.basichello;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Operation;

/**
 * A filter to make final configuration changes to the produced OpenAPI document.
 */
public class OperationHyphenFilter implements OASFilter {

    /**
     * Replaces all spaces in each operation id with a hyphen.
     */
    @Override
    public Operation filterOperation(Operation operation) {
        if (operation.getOperationId().contains((" "))) {
            operation.setOperationId(operation.getOperationId().replace(" ", "-"));
        }

        return operation;
    }

}