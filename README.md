# Liberty EJB 2 Remote Example with EJB JAR, EJB Client JAR, WAR, EAR and App Client

This repository shows an EJB 2 remote example running on WebSphere Liberty/Open Liberty. The sample code includes an EJB JAR, EJB Client JAR, WAR, EAR and App Client:

* [hello-world-ejb](hello-world-ejb/) - has the EJB JAR.
* [hello-world-web](hello-world-web/) - has the WAR.
* [hello-world-ejb-client](hello-world-ejb-client/) - has the EJB Client artifacts shared by the EJB JAR, WAR and App Client such as the EJB 2 home and remote intefaces.
* [hello-world-client](hello-world-client/) - has the App Client.
* [hello-world](hello-world/) - has the contents of the EAR.

The sample shows several patterns of binding remote EJB names to JNDI including using `java:global`, `java:app`, `corbaname:`, and `java:comp/env` as well as injection using `@EJB`.

Maven is used as the build system and the top level directory contains a [parent POM](pom.xml) that has the EJB JAR, EJB Client JAR, WAR, EAR and App Client as modules. You can use Java SE 8+ (including Java SE 11) to run the build to generate all the artifacts. Just run the Maven build at the top level directory:

   ```
   mvn clean install
   ```

After the artifacts are built, you can deploy the EAR to Liberty (typically by copying it to the `apps/` directory of your Liberty server installation). You should also copy over the supplied `server.xml` in the sample. Once you perform these actions, you can run the application and test the web application. The web application context path is `/hello-world`.

You can then run the App Client following the instructions found [here](https://www.ibm.com/docs/en/was-liberty/core?topic=liberty-preparing-running-application-client). Be sure to copy the EAR to the `apps/` directory of your Liberty client and also copy over the supplied `client.xml` in the sample. The App Client sample does not use any command line arguments.

You may use the default unsecured `server.xml` and `client.xml` files for this example. However, you can also opt to use the secure versions of configuration. If you do use the secure configuration, the client and server SSL configurations must match. The easiest way to ensure this is having the Liberty server generate them and copying over the configuration files to the client.

Just run the server successfully first with the deployed EAR. Then copy over the `key.p12` and `ltpa.keys` files from the server's `resources/security` directory to the client's `resources/security` directory before running the client.
