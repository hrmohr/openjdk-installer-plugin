package io.jenkins.plugins.openjdkinstaller.vendors;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import io.jenkins.plugins.openjdkinstaller.Messages;
import io.jenkins.plugins.openjdkinstaller.OpenJDKVendor;
import io.jenkins.plugins.openjdkinstaller.OpenJDKVendorDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.List;

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
            OpenJDKVersionListImpl openJDKVersionList = OpenJDKVersionListImpl.all().get(OpenJDKVersionListImpl.class);
            if (openJDKVersionList != null) {
                return openJDKVersionList.toList();
            }
            return null;
        }
    }

    @Extension
    public static final class OpenJDKVersionListImpl extends OpenJDKVersionList {
        public OpenJDKVersionListImpl() {
            super(Semeru.class);
        }
    }
}
