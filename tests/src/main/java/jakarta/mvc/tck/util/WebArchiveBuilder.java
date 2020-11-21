/*
 * Copyright © 2017, 2019 Christian Kaltepoth
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
package jakarta.mvc.tck.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor;
import org.jboss.shrinkwrap.descriptor.api.facesconfig22.WebFacesConfigDescriptor;
import org.jboss.shrinkwrap.descriptor.api.webapp31.WebAppDescriptor;

public class WebArchiveBuilder {

    private WebArchive archive;

    public WebArchiveBuilder() {
        this(ShrinkWrap.create(WebArchive.class));
    }

    public WebArchiveBuilder(WebArchive archive) {
        this.archive = archive;
    }

    public WebArchiveBuilder addClass(Class<?> clazz) {
        archive.addClass(clazz);
        return this;
    }

    public WebArchiveBuilder addClasses(Class<?>... classes) {
        archive.addClasses(classes);
        return this;
    }


    public WebArchiveBuilder addView(Asset asset, String name) {
        archive.addAsWebInfResource(asset, "views/" + name);
        return this;
    }

    public WebArchiveBuilder addView(String value, String name) {
        return this.addView(new StringAsset(value), name);
    }

    public WebArchiveBuilder addView(String path) {
        return this.addView(
                new ClassLoaderAsset("views/" + path),
                path
        );
    }

    public WebArchiveBuilder addBeansXml(String discoveryMode) {
        return addBeansXml(
                Descriptors.create(BeansDescriptor.class)
                        .addDefaultNamespaces()
                        .beanDiscoveryMode(discoveryMode)
        );
    }

    public WebArchiveBuilder addBeansXml(BeansDescriptor descriptor) {

        final String beans30Xml = descriptor.exportAsString()
                .replace("http://xmlns.jcp.org/xml/ns/javaee", "https://jakarta.ee/xml/ns/jakartaee")
                .replace("/beans_1_1.xsd", "/beans_3_0.xsd");

        archive.addAsWebInfResource(new StringAsset(descriptor.exportAsString()), "beans.xml");
        return this;
    }

    public WebArchiveBuilder withDefaultFaceConfig() {

        // empty JSF 2.2 descriptor
        WebFacesConfigDescriptor descriptor = Descriptors.create(WebFacesConfigDescriptor.class)
                .addDefaultNamespaces()
                .version("3.0");

        final String faces30Version = descriptor.exportAsString()
                .replace("http://xmlns.jcp.org/xml/ns/javaee", "https://jakarta.ee/xml/ns/jakartaee")
                .replace("/web-facesconfig_2_2.xsd", "/web-facesconfig_3_0.xsd");

        archive.addAsWebInfResource(new StringAsset(faces30Version), "faces-config.xml");
        return this;

    }

    public WebArchiveBuilder withDefaultWebXml() {

        // Servlet descriptor with FacesServlet registered
        WebAppDescriptor descriptor = Descriptors.create(WebAppDescriptor.class)
                .addDefaultNamespaces()
                .version("5.0")
                .createServlet()
                .servletName("FacesServlet")
                .servletClass("jakarta.faces.webapp.FacesServlet")
                .up()
                .createServletMapping()
                .servletName("FacesServlet")
                .urlPattern("*.xhtml")
                .up();

        return withWebXml(descriptor);

    }

    public WebArchiveBuilder withWebXml(WebAppDescriptor descriptor) {

        final String web50Version = descriptor.exportAsString()
                .replace("http://xmlns.jcp.org/xml/ns/javaee", "https://jakarta.ee/xml/ns/jakartaee")
                .replace("/web-app_3_1.xsd", "/web-app_5_0.xsd");

        archive.addAsWebInfResource(new StringAsset(web50Version), "web.xml");
        return this;
    }

    public WebArchive build() {
        return archive;
    }
}
