package io.jenkins.plugins.openjdkinstaller;

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
