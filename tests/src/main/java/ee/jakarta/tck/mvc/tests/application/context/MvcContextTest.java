/*
 * Copyright (c) 2019 Christian Kaltepoth
 * Copyright (c) 2019, 2025 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.mvc.tests.application.context;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ee.jakarta.tck.mvc.Sections;
import ee.jakarta.tck.mvc.util.Archives;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "1.0")
public class MvcContextTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(MvcContextController.class)
                .addView("application/context/result.jsp")
                .build();
    }

    @Before
    public void before() {
        webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(false);
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "injection"),
    })
    public void testMvcContextInjected() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext injected = [true]"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "injection"),
    })
    public void testMvcContextAccessInformation() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(), allOf(
                containsString("CSRF accessible = [true]"),
                containsString("Path accessible = [true]"),
                containsString("Config accessible = [true]"),
                containsString("Encoders accessible = [true]")
        ));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "request-scope"),
    })
    public void testMvcContextScope() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext scope = [jakarta.enterprise.context.RequestScoped]"));

    }


    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.APPLICATION_MVC_CONTEXT, id = "el-access"),
    })
    public void testMvcContextAccessViaEl() throws IOException {

        Page page = webClient.getPage(baseUrl.toString() + "mvc/application/context");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(page.getWebResponse().getContentAsString(),
                containsString("MvcContext via EL = [true]"));

    }

}
