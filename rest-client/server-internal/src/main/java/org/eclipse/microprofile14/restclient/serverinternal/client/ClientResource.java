/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.restclient.serverinternal.client;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("client")
@RequestScoped
public class ClientResource {

    @Inject
    @RestClient
    private HelloService helloService;

    @GET
    @Produces(TEXT_PLAIN)
    public String helloWorld() {
        return helloService.hello("World");
    }

}
