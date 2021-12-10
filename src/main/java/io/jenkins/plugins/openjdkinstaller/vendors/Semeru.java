package io.jenkins.plugins.openjdkinstaller.vendors;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import io.jenkins.plugins.openjdkinstaller.Messages;
import io.jenkins.plugins.openjdkinstaller.OpenJDKVendor;
import io.jenkins.plugins.openjdkinstaller.OpenJDKVendorDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.List;
import java.util.Objects;

public class Semeru extends OpenJDKVendor {
    @DataBoundConstructor
    public Semeru() {
        super();
    }

    @Extension
    public static class DescriptorImpl extends OpenJDKVendorDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.OpenJDKInstaller_vendors_Semeru_DescriptorImpl_displayName();
        }

        @Override
        public String getDescription() {
            return Messages.OpenJDKInstaller_vendors_Semeru_DescriptorImpl_description();
        }

        @Override
        public List<OpenJDKVersion> getInstallableVersions() {
            return Objects.requireNonNull(OpenJDKVersionListImpl.all().get(OpenJDKVersionListImpl.class)).toList();
        }
    }

    @Extension
    public static final class OpenJDKVersionListImpl extends OpenJDKVersionList {
        public OpenJDKVersionListImpl() {
            super(Semeru.class);
        }
    }
}
