package jakarta.mvc.tck.tests.form;

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
