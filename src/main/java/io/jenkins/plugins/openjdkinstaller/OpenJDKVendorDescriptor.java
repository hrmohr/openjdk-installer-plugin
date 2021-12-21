package io.jenkins.plugins.openjdkinstaller;

/*-
 * #%L
 * OpenJDK Installer Plugin
 * %%
 * Copyright (C) 2021 Mads Mohr Christensen
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import org.kohsuke.stapler.QueryParameter;

import java.util.List;

public abstract class OpenJDKVendorDescriptor extends Descriptor<OpenJDKVendor> {

    public abstract String getDescription();

    public abstract List<OpenJDKVendor.OpenJDKVersion> getInstallableVersions();

    public ListBoxModel doFillVersionItems() {
        ListBoxModel m = new ListBoxModel();
        List<OpenJDKVendor.OpenJDKVersion> installableVersions = getInstallableVersions();
        if (installableVersions != null) {
            installableVersions.forEach(v -> m.add(v.name, v.id));
        }
        return m;
    }

    public ListBoxModel doFillReleaseItems(@QueryParameter String version) {
        ListBoxModel m = new ListBoxModel();
        if (version != null) {
            List<OpenJDKVendor.OpenJDKVersion> installableVersions = getInstallableVersions();
            if (installableVersions != null) {
                installableVersions.stream().filter(v -> version.equals(v.id)).findFirst().ifPresent(openJDKVersion ->
                        openJDKVersion.releases.forEach(r -> m.add(r.name, r.id))
                );
            }
        }
        m.add(Messages.OpenJDKVendorDescriptor_latestRelease(), "latest");
        return m;
    }

    public String getDefaultVersion() {
        List<OpenJDKVendor.OpenJDKVersion> installableVersions = getInstallableVersions();
        if (installableVersions != null) {
            return installableVersions.stream().findFirst().map(
                    openJDKVersion -> openJDKVersion.id
            ).orElse(null);
        }
        return null;
    }
}
