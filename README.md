# mvc-tck

Technology Compatibility Kit for Jakarta MVC

## Build

Run the Maven build for the TCK:

    cd <TCK_HOME>
    mvn clean install

## Run Sample Runner

This is an example for quickly running the TCK against Krazo on GlassFish. For other configurations, refer to the TCK Documentation and Usage Guide.

Download and unpack the latest Glassfish Promoted Build:

    https://eclipse-ee4j.github.io/glassfish/download
    
Start Glassfish:

    cd <GLASSFISH_HOME>/glassfish/bin/
    ./startserv

Now execute the sample runner to run the tests against Eclipse Krazo deployed to Glassfish:

    cd <TCK_HOME>/sample/
    mvn verify
    
Of course all the tests should pass. :-)

## File challenges

As stated by the [TCK-process](https://jakarta.ee/committees/specification/tckprocess/), <cite>Specifications are the sole source of truth and considered overruling to the TCK in all senses</cite>. Therefore, during the implementation of a specification it can happen, that the implementing party is considering a test of this TCK as non-conform to the specification. To get this problem resolved, the implementing party can create a _challenge_ for the test.

To file a challenge, please create an issue in the [MVC TCK issue tracker](https://github.com/eclipse-ee4j/mvc-tck/issues/) as described in the [TCK process - Filing a challenge](https://jakarta.ee/committees/specification/tckprocess#_filing_a_challenge).