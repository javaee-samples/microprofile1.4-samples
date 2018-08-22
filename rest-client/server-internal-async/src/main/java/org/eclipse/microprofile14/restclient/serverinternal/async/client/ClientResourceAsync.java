package org.eclipse.microprofile14.restclient.serverinternal.async.client;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("clientAsync")
@RequestScoped
public class ClientResourceAsync {

    @Inject
    @RestClient
    private HelloService helloService;

    @GET
    @Produces(TEXT_PLAIN)
    public CompletionStage<String> helloWorld() {
        return helloService.helloAsync("World (Async)").thenApply(String::toUpperCase);
    }

}