/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.restclient.serverinternal.async.client;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author Ondrej Mihalyi
 */
@Path("/api/hello")
@RequestScoped
@RegisterRestClient  // Required to enable injection of this interface
public interface HelloService {

    @GET
    @Path("{name}")
    @Produces(TEXT_PLAIN)
    @Consumes(TEXT_PLAIN)
    CompletionStage<String> helloAsync(@PathParam("name") String name);

}

