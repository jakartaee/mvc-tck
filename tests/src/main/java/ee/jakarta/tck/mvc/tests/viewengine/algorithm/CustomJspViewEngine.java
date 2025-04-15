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
package ee.jakarta.tck.mvc.tests.viewengine.algorithm;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mvc.engine.ViewEngine;
import jakarta.mvc.engine.ViewEngineContext;
import jakarta.mvc.engine.ViewEngineException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Priority(ViewEngine.PRIORITY_APPLICATION)
public class CustomJspViewEngine implements ViewEngine {

    @Override
    public boolean supports(String view) {
        return view.endsWith(".jsp");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        try {

            context.getResponseHeaders().putSingle("Content-Type", "text/html; charset=UTF-8");
            context.getOutputStream().write(this.getClass().getSimpleName().getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new ViewEngineException(e);
        }

    }
}
