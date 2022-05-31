/*
 * Copyright (c) 2022 Eclipse Krazo committers and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package jakarta.mvc.tck.tests.form;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import jakarta.mvc.tck.Sections;
import jakarta.mvc.tck.util.Archives;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@SpecVersion(spec = "mvc", version = "2.1")
public class DefaultFormMethodOverwriteTest extends AbstractFormMethodOverwriteTest {

    @ArquillianResource
    protected URL baseUrl;

    protected WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Archives.getMvcArchive()
                .addClass(FormMethodOverwriteController.class)
                .addView("form/form-with-hidden-field.jsp")
                .addView("form/form-without-hidden-field.jsp")
                .addView("form/result.jsp")
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
            @SpecAssertion(section = Sections.FORM_METHOD_OVERWRITE_ALGORITHM, id = "form-overwriter-algorithm")
    })
    public void processPOSTWithoutHiddenFormMethod() throws IOException {

        final HtmlPage formPage = getFormPageWithParam(webClient, baseUrl, "none");
        final HtmlForm form = (HtmlForm) formPage.getElementById("form");

        final HtmlPage okPage = submit(form);

        assertEquals("POST", okPage.getElementById("invoked-method").getTextContent());
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.FORM_METHOD_OVERWRITE_ALGORITHM, id = "form-overwriter-algorithm")
    })
    public void processPUTWhenHiddenFormMethodIsPUT() throws IOException {
        final HtmlPage page = getDefaultFormPage(webClient, baseUrl);

        final HtmlForm form = setHiddenMethodInForm(page, "PUT");

        final HtmlPage resultPage = submit(form);

        assertEquals("PUT", resultPage.getElementById("invoked-method").getTextContent());
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.FORM_METHOD_OVERWRITE_ALGORITHM, id = "form-overwriter-algorithm")
    })
    public void processPATCHWhenHiddenFormMethodIsPATCH() throws IOException {
        final HtmlPage page = getDefaultFormPage(webClient, baseUrl);

        final HtmlForm form = setHiddenMethodInForm(page, "PATCH");

        final HtmlPage resultPage = submit(form);

        assertEquals("PATCH", resultPage.getElementById("invoked-method").getTextContent());
    }

    @Test
    @SpecAssertions({
            @SpecAssertion(section = Sections.FORM_METHOD_OVERWRITE_ALGORITHM, id = "form-overwriter-algorithm")
    })
    public void processDELETEWhenHiddenFormMethodIsDelete() throws IOException {
        final HtmlPage page = getDefaultFormPage(webClient, baseUrl);

        final HtmlForm form = setHiddenMethodInForm(page, "DELETE");

        final HtmlPage resultPage = submit(form);

        assertEquals("DELETE", resultPage.getElementById("invoked-method").getTextContent());
    }
}
