package org.eclipse.microprofile14.restclient.serverinternal.async;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.eclipse.microprofile14.restclient.serverinternal.async.client.ClientResourceAsync;
import org.eclipse.microprofile14.restclient.serverinternal.async.client.HelloService;
import org.eclipse.microprofile14.restclient.serverinternal.async.config.ApplicationInit;
import org.eclipse.microprofile14.restclient.serverinternal.async.config.URLServiceConfigSource;
import org.eclipse.microprofile14.restclient.serverinternal.async.remote.HelloRemoteResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ServerInternalAsyncTest {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive archive =
            create(WebArchive.class)
                .addClasses(
                    ApplicationInit.class,
                    URLServiceConfigSource.class,
                    HelloRemoteResource.class,
                    ClientResourceAsync.class,
                    HelloService.class
                ).addAsResource(
                    "META-INF/services/org.eclipse.microprofile.config.spi.ConfigSource"
                )
                ;

        System.out.println("************************************************************");
        System.out.println(archive.toString(true));
        System.out.println("************************************************************");

        return archive;
    }

    @Test
    @RunAsClient
    public void testServerInternalAsync() throws IOException {

        String response =
                newClient()
                     .target(
                         URI.create(new URL(base, "api/clientAsync").toExternalForm()))
                     .request(TEXT_PLAIN)
                     .get(String.class);

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Response: " + response);
        System.out.println("-------------------------------------------------------------------------");

        assertTrue(
            response.contains("HELLO WORLD (ASYNC)!")
        );
    }

}
