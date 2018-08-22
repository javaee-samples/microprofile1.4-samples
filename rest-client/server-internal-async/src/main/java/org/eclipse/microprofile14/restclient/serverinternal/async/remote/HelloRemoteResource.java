/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.restclient.serverinternal.async.remote;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hello")
@Produces(TEXT_PLAIN)
@Consumes(TEXT_PLAIN)
public class HelloRemoteResource {

    @GET
    @Path("{name}")
    public String hello(@PathParam("name") String name) {
        return "Hello " + name + "!";
    }

}
