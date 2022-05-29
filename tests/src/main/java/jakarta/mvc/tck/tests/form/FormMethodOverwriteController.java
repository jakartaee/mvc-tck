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

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("forms")
@Controller
public class FormMethodOverwriteController {

    @Inject
    private Models models;

    @GET
    public String invokeGET(@QueryParam("hidden-field-setting") final String hiddenFieldSetting) {
        if ("none".equals(hiddenFieldSetting)) {
            return "form/form-without-hidden-field.jsp";
        }
        if ("renamed".equals(hiddenFieldSetting)) {
            return "form/form-with-renamed-hidden-field.jsp";
        }
        return "form/form-with-hidden-field.jsp";
    }

    @POST
    public String invokePOST() {
        models.put("method", "POST");
        return "form/result.jsp";
    }

    @PUT
    public String invokePUT() {
        models.put("method", "PUT");
        return "form/result.jsp";
    }

    @PATCH
    public String invokePATCH() {
        models.put("method", "PATCH");
        return "form/result.jsp";
    }

    @DELETE
    public String invokeDELETE() {
        models.put("method", "DELETE");
        return "form/result.jsp";
    }
}
