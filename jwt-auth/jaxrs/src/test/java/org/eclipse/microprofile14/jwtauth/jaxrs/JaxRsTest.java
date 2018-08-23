/** Copyright Payara Services Limited **/
package org.eclipse.microprofile14.jwtauth.jaxrs;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static org.eclipse.microprofile14.JwtTokenGenerator.generateJWTString;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.Response;

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
public class JaxRsTest {

    @ArquillianResource
    private URL base;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive archive =
                create(WebArchive.class)
                    .addClasses(
                        ApplicationInit.class,
                        Resource.class
                    ).addAsResource(
                        // Main Properties file configuring that "org.eclipse.microprofile12" is the valid issuer
                        // and META-INF/public-key.pem is the public key
                        "META-INF/microprofile-config.properties"
                    ).addAsResource(
                        // Public key to verify the incoming signed JWT's signature
                        "META-INF/public-key.pem"
                    ).addAsResource(
                        // WildFly file configuring that "org.eclipse.microprofile12" is the valid issuer and setting up
                        // the security system (domain) such that all artifacts to support MP-Auth JWT are installed
                        "project-defaults.yml")
                    .addAsWebInfResource(
                        // Make CDI look at ApplicationInit and discover LoginConfig
                        INSTANCE, "beans.xml")
                    ;

        System.out.println("************************************************************");
        System.out.println(archive.toString(true));
        System.out.println("************************************************************");

        return archive;

    }

    @Test
    @RunAsClient
    public void testProtectedResourceNotLoggedin() throws IOException {

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Base URL: " + base);
        System.out.println("-------------------------------------------------------------------------");

        Response response =
                newClient()
                     .target(
                         URI.create(new URL(base, "resource/protected").toExternalForm()))
                     .request(TEXT_PLAIN)
                     .get();

        // Not logged-in thus should not be accessible.
        assertFalse(
            "Not authenticated, so should not have been able to access protected resource",
            response.readEntity(String.class).contains("This is a protected resource")
        );
    }

    @Test
    @RunAsClient
    public void testPublicResourceNotLoggedin() throws IOException {

        Response response =
                newClient()
                     .target(
                         URI.create(new URL(base, "resource/public").toExternalForm()))
                     .request(TEXT_PLAIN)
                     .get();

        // Public resource, no log-in needed
        assertTrue(
            "Public resource is not constrained (protected) so should be accessible without sending the JWT token",
            response.readEntity(String.class).contains("This is a public resource")
        );
    }

    @Test
    @RunAsClient
    public void testProtectedResourceLoggedin() throws Exception {

        String response =
                newClient()
                     .target(
                         URI.create(new URL(base, "resource/protected").toExternalForm()))
                     .request(TEXT_PLAIN)
                     .header(AUTHORIZATION, "Bearer " + generateJWTString("jwt-token.json"))
                     .get(String.class);


        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Response: " + response);
        System.out.println("-------------------------------------------------------------------------");

        // Now has to be logged-in so page is accessible
        assertTrue(
            "Should have been authenticated, but could not access protected resource",
            response.contains("This is a protected resource")
        );

        // Not only does the resource needs to be accessible, the caller should have
        // the correct name

        // Being able to access a page protected by a role but then seeing the un-authenticated
        // (anonymous) user would normally be impossible, but could happen if the authorization
        // system checks roles on the authenticated subject, but does not correctly expose
        // or propagate these to the injected Principal
        assertFalse(
            "Protected resource could be accessed, but the user appears to be the unauthenticated user. " +
            "This should not be possible",
            response.contains("web username: null")
        );

        // An authenticated user should have the exact name "test" and nothing else.
        assertTrue(
            "Protected resource could be accessed, but the username is not correct.",
            response.contains("web username: test")
        );

    }

}
