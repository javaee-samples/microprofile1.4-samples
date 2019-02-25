/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.restclient.clientserver;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.RestClientDefinitionException;
import org.eclipse.microprofile14.restclient.clientserver.client.HelloService;
import org.eclipse.microprofile14.restclient.clientserver.config.ApplicationInit;
import org.eclipse.microprofile14.restclient.clientserver.remote.HelloRemoteResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ClientServerAsyncTest {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive archive =
            create(WebArchive.class)
                .addClasses(
                    ApplicationInit.class,
                    HelloRemoteResource.class
                ).addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                ;

        System.out.println("************************************************************");
        System.out.println(archive.toString(true));
        System.out.println("************************************************************");

        return archive;
    }

    @Test
    @RunAsClient
    public void testClientServerAsync() throws IOException, IllegalStateException, RestClientDefinitionException, URISyntaxException, InterruptedException, ExecutionException {

        HelloService remoteApi = RestClientBuilder.newBuilder()
                .baseUrl(base)
                .build(HelloService.class);


        String response =
            remoteApi.helloAsync("Programmer (Async)")
                     .thenApply(String::toUpperCase)
                     .toCompletableFuture()
                     .get();

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Response: " + response);
        System.out.println("-------------------------------------------------------------------------");

        assertTrue(
            response.contains("HELLO PROGRAMMER (ASYNC)!")
        );
    }

}
