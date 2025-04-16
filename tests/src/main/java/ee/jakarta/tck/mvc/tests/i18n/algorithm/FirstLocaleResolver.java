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

package ee.jakarta.tck.mvc.tests.i18n.algorithm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.locale.LocaleResolver;
import jakarta.mvc.locale.LocaleResolverContext;
import jakarta.annotation.Priority;
import java.util.Locale;

@Priority(2000)  // highest prio -> executed first
@ApplicationScoped
public class FirstLocaleResolver implements LocaleResolver {

    @Inject
    private ResolverChainLogger resolverChainLogger;

    @Override
    public Locale resolveLocale(LocaleResolverContext context) {

        // log this invocation
        resolverChainLogger.log(FirstLocaleResolver.class);

        // let the next resolver continue
        return null;

    }

}
