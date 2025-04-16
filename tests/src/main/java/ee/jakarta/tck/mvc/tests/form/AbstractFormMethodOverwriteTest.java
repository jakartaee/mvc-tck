/*
 * Copyright (c) 2022 Eclipse Krazo committers and contributors
 * Copyright (c) 2025 Contributors to the Eclipse Foundation

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
package ee.jakarta.tck.mvc.tests.form;

import java.io.IOException;
import java.net.URL;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class AbstractFormMethodOverwriteTest {
    protected final HtmlPage getDefaultFormPage(final WebClient webClient, final URL baseUrl) throws java.io.IOException {
        return getFormPageWithParam(webClient, baseUrl, "");
    }

    protected final HtmlPage getFormPageWithParam(final WebClient webClient, final URL baseUrl, String param) throws IOException {
        if (param != null && !param.trim().isEmpty()) {
            return webClient.getPage(baseUrl + "mvc/forms?hidden-field-setting=" + param);
        }
        return webClient.getPage(baseUrl + "mvc/forms");
    }

    protected final HtmlForm setHiddenMethodInForm(final HtmlPage page, final String method) {
        final HtmlForm form = (HtmlForm) page.getElementById("form");
        final HtmlHiddenInput hiddenMethod = (HtmlHiddenInput) page
                .getElementById("hidden-method");
        hiddenMethod.setValueAttribute(method);
        return form;
    }

    protected final HtmlPage submit(final HtmlForm form) throws java.io.IOException {
        return form.getInputByName("submit").click();
    }
}
