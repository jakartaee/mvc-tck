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
package ee.jakarta.tck.mvc.tests.events;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/trace")
public class TraceServlet extends HttpServlet {

    private static final long serialVersionUID = 4075821198643825653L;

    @Inject
    private TraceManager traceManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("tid");
        if (id == null) {
            resp.sendError(400);
            return;
        }

        TraceManager.TracedRequest tracedRequest = traceManager.getTracedRequest(id);
        if (tracedRequest == null) {
            resp.sendError(400);
            return;
        }

        resp.getWriter().write(String.join(",", tracedRequest.getEvents()));
        resp.flushBuffer();

    }


}
