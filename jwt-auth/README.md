# Eclipse MicroProfile 1.2 Samples - JWT Auth 1.1

 - [Wiki project page](https://wiki.eclipse.org/MicroProfile/JWT_Auth)
 - [Spec, API, TCK GitHub repo](https://github.com/eclipse/microprofile-jwt-auth)

## Samples ##

 - **basic-authentication** The test sends a very basic signed JWT token to a protected servlet. The MP-JWT Auth implementation
   checks if the token is valid and sets the authenticated identity from the `upn` field and `groups` field. Has a variant where the required public key is in the archive, and one where the key is obtained externally (from outside the archive).
   **jaxrs ** Just like basic-authentication, but uses a JAX-RS endpoint. Specifically demonstrates the support of @RolesAllowed to secure and endpoint.
   


## Implementation config ##

New in JWT Auth 1.1 is that implementation specific config is not longer mandated. Note that WildFly still needs implementation specific config, but this is not mandated by the spec.
     
     
## TCK ##

The public/private keys are taken from the MP-Auth TCK.
See the following URLs:

 - [MP-Auth TCK](https://github.com/eclipse/microprofile-jwt-auth/tree/master/tck)
 - [Payara TCK Ext](https://github.com/payara/Payara/tree/Payara-5/appserver/payara-appserver-modules/microprofile/jwt-auth-tck)
 - [Liberty TCK](https://github.com/OpenLiberty/open-liberty/tree/master/dev/com.ibm.ws.security.mp.jwt_fat_tck)
 - [WildFly TCK Ext](https://github.com/MicroProfileJWT/wfswarm-jwt-auth-tck)

