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
package ee.jakarta.tck.mvc.tests.application.inheritance;

import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

public interface InheritanceBaseInterface {

    @GET
    @Path("annotations-only-on-interface-method")
    @View("application/inheritance/interface.jsp")
    void annotationsOnlyOnInterfaceMethod();

    @GET
    @Path("annotations-on-controller-and-interface-method")
    @View("application/inheritance/interface.jsp")
    void annotationsOnControllerAndInterfaceMethod();

    @GET
    @Path("annotations-on-superclass-and-interface-method")
    @View("application/inheritance/interface.jsp")
    void annotationsOnSuperClassAndInterfaceMethod();

}
