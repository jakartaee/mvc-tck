/*
 * Copyright © 2019 Christian Kaltepoth
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

package jakarta.mvc.tck.tests.viewengine.base;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.engine.ViewEngine;
import jakarta.mvc.engine.ViewEngineContext;
import jakarta.mvc.engine.ViewEngineException;
import jakarta.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ApplicationScoped
public class CustomViewEngine implements ViewEngine {

    @Inject
    private ServletContext servletContext;

    @Override
    public boolean supports(String view) {
        return view.endsWith(".custom");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        try {

            String result = loadTemplateByName(context.getView());

            for (String key : context.getModels()) {
                result = result.replace("%" + key + "%", Objects.toString(context.getModels().get(key)));
            }

            context.getResponseHeaders().putSingle("Content-Type", "text/html; charset=UTF-8");
            context.getOutputStream().write(result.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new ViewEngineException("Failed to render view", e);
        }

    }


    private String loadTemplateByName(String view) throws IOException {

        String viewPath = "/WEB-INF/views/" + view;

        InputStream inputStream = servletContext.getResourceAsStream(viewPath);
        if (inputStream == null) {
            throw new IOException("Cannot find view: " + viewPath);
        }

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return new String(result.toByteArray(), StandardCharsets.UTF_8);

    }

}
