/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.mvc.tests.binding.nullability;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import ee.jakarta.tck.mvc.Sections;
import ee.jakarta.tck.mvc.util.Archives;
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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "3.1")
public class BindingNullabilityTest {

    @ArquillianResource
    private URL baseUrl;

    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(BindingNullabilityController.class)
                .addClass(BindingNullabilityForm.class)
                .addView("binding/nullability/result.jsp")
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
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-non-null"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-unknown-param")
    })
    public void noErrorsReturnsEmptyCollections() throws IOException {

        HtmlPage page = submit("20");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(value(page, "allMessages"), equalTo("0"));
        assertThat(value(page, "allErrors"), equalTo("0"));
        assertThat(value(page, "ageErrors"), equalTo("0"));
        assertThat(value(page, "nameErrors"), equalTo("0"));
        assertThat(value(page, "unknownErrors"), equalTo("0"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-non-null"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-unknown-param"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "param-error-non-null")
    })
    public void validationErrorLeavesOtherParamsEmpty() throws IOException {

        HtmlPage page = submit("12");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(value(page, "allMessages"), equalTo("1"));
        assertThat(value(page, "allErrors"), equalTo("1"));
        assertThat(value(page, "ageErrors"), equalTo("1"));
        assertThat(value(page, "nameErrors"), equalTo("0"));
        assertThat(value(page, "unknownErrors"), equalTo("0"));
        assertThat(value(page, "errorNullValues"), equalTo("0"));

    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-non-null"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "binding-result-unknown-param"),
            @SpecAssertion(section = Sections.BINDING_ERROR_HANDLING, id = "param-error-non-null")
    })
    public void bindingErrorLeavesOtherParamsEmpty() throws IOException {

        HtmlPage page = submit("foobar");

        assertThat(page.getWebResponse().getStatusCode(), equalTo(200));
        assertThat(value(page, "allMessages"), equalTo("1"));
        assertThat(value(page, "allErrors"), equalTo("1"));
        assertThat(value(page, "ageErrors"), equalTo("1"));
        assertThat(value(page, "nameErrors"), equalTo("0"));
        assertThat(value(page, "unknownErrors"), equalTo("0"));
        assertThat(value(page, "errorNullValues"), equalTo("0"));

    }

    private HtmlPage submit(String age) throws IOException {

        WebRequest request = new WebRequest(new URL(baseUrl.toString() + "mvc/binding/nullability"), HttpMethod.POST);
        request.setRequestParameters(Arrays.asList(
                new NameValuePair("age", age),
                new NameValuePair("name", "Duke")
        ));

        return webClient.getPage(request);

    }

    private static String value(HtmlPage page, String id) {
        return page.getElementById(id).getTextContent().trim();
    }

}
