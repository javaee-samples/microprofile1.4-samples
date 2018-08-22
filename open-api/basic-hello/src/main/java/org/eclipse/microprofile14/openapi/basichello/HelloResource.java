/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.openapi.basichello;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/hello")
public class HelloResource {

    @GET
    @Operation(
        operationId = "hello world",
        description = "This is a well know Hello World service. It will output a variant of the expected 'Hello, world!' phrase.")
    @Produces(TEXT_PLAIN)
    public String helloWorld() {
        return "Hello World!";
    }

}
