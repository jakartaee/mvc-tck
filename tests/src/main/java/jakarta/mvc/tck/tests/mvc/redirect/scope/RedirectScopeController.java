/*
 * Copyright © 2018 Christian Kaltepoth
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
package jakarta.mvc.tck.tests.mvc.redirect.scope;

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Controller
@Path("scope")
public class RedirectScopeController {

    @Inject
    private RequestScopeBean requestScopedBean;

    @Inject
    private SessionScopeBean sessionScopeBean;

    @Inject
    private RedirectScopeBean redirectScopeBean;

    @GET
    @Path("write-redirect-read")
    public String start() {

        requestScopedBean.setValue("foo");
        sessionScopeBean.setValue("bar");
        redirectScopeBean.setValue("foobar");

        return "redirect:scope/read";

    }

    @GET
    @Path("read")
    public String target() {
        return "read.jsp";
    }

}
