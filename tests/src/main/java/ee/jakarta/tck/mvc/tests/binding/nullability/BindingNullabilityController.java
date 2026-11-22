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

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.mvc.binding.ValidationError;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.Collection;
import java.util.Set;

/**
 * Inspects the values returned by {@link BindingResult} and exposes the outcome to the view.
 * A <code>null</code> collection is reported as size <code>-1</code>, so the test fails with
 * a clear assertion instead of an exception in the controller.
 */
@Controller
@Path("binding/nullability")
public class BindingNullabilityController {

    static final String UNKNOWN_PARAM = "this-parameter-does-not-exist";

    @Inject
    private Models models;

    @Inject
    private BindingResult bindingResult;

    @POST
    public String submit(@BeanParam @Valid BindingNullabilityForm form) {

        Set<ParamError> allErrors = bindingResult.getAllErrors();
        Set<ParamError> ageErrors = bindingResult.getErrors("age");
        Set<ParamError> nameErrors = bindingResult.getErrors("name");
        Set<ParamError> unknownErrors = bindingResult.getErrors(UNKNOWN_PARAM);

        models.put("allMessages", size(bindingResult.getAllMessages()));
        models.put("allErrors", size(allErrors));
        models.put("ageErrors", size(ageErrors));
        models.put("nameErrors", size(nameErrors));
        models.put("unknownErrors", size(unknownErrors));

        // the non-null requirement applies to every error provided by BindingResult,
        // so check the errors returned by each method, not only getAllErrors()
        models.put("errorNullValues", countNullValues(allErrors) + countNullValues(ageErrors)
                + countNullValues(nameErrors) + countNullValues(unknownErrors));

        return "binding/nullability/result.jsp";

    }

    private static int size(Collection<?> collection) {
        return collection == null ? -1 : collection.size();
    }

    private static int countNullValues(Collection<ParamError> errors) {
        int nullValues = 0;
        if (errors != null) {
            for (ParamError error : errors) {
                if (error.getMessage() == null) {
                    nullValues++;
                }
                if (error.getParamName() == null) {
                    nullValues++;
                }
                if (error instanceof ValidationError && ((ValidationError) error).getViolation() == null) {
                    nullValues++;
                }
            }
        }
        return nullValues;
    }

}
