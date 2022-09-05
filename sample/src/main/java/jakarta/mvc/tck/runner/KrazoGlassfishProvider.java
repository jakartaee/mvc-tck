/*
 * Copyright © 2017 Ivar Grimstad (ivar.grimstad@gmail.com)
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
package ee.jakarta.tck.mvc.runner;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import ee.jakarta.tck.mvc.api.BaseArchiveProvider;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepositories;
import org.jboss.shrinkwrap.resolver.api.maven.repository.MavenRemoteRepository;

import java.io.File;

public class KrazoGlassfishProvider implements BaseArchiveProvider {

    @Override
    public WebArchive getBaseArchive() {

//MavenRemoteRepository stagingRepo = MavenRemoteRepositories.createRemoteRepository(
//                "jakarta-staging",
//                "https://jakarta.oss.sonatype.org/content/groups/staging",
//                "default"
//        );


        File[] dependencies = //Maven.resolver()
                Maven.configureResolver()
//                        .withRemoteRepo(stagingRepo)
                        .withMavenCentralRepo(false)
                        .resolve(
                                "jakarta.mvc:jakarta.mvc-api:2.1.0",
                                "org.eclipse.krazo:krazo-core:3.0.0",
                                "org.eclipse.krazo:krazo-jersey:3.0.0")
                        .withoutTransitivity()
                        .asFile();

        return ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(dependencies);

    }

}
