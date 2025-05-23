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

import jakarta.mvc.form.FormMethodOverwriter;
import jakarta.mvc.security.Csrf;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationPath("mvc")
public class DisabledFormMethodOverwriteCustomApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put(FormMethodOverwriter.FORM_METHOD_OVERWRITE, FormMethodOverwriter.Options.DISABLED);
        properties.put(Csrf.CSRF_PROTECTION, Csrf.CsrfOptions.OFF);
        return properties;
    }
}
