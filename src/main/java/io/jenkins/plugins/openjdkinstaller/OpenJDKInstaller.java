package io.jenkins.plugins.openjdkinstaller;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.model.JDK;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolInstaller;
import hudson.tools.ToolInstallerDescriptor;
import io.jenkins.plugins.openjdkinstaller.vendors.Temurin;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

public class OpenJDKInstaller extends ToolInstaller {

    private final OpenJDKVendor vendor;

    @DataBoundConstructor
    public OpenJDKInstaller(OpenJDKVendor vendor) {
        super(null);
        this.vendor = vendor;
    }

    public OpenJDKVendor getVendor() {
        return vendor;
    }

    @Override
    public FilePath performInstallation(ToolInstallation tool, Node node, TaskListener log) throws IOException, InterruptedException {
        //FilePath expected = preferredLocation(tool, node);
        //getVendor().getDescriptor().getInstallableVersions();
        return null;
    }

    @Extension
    @Symbol("openJDK")
    public static class DescriptorImpl extends ToolInstallerDescriptor<OpenJDKInstaller> {
        @Override
        public boolean isApplicable(Class<? extends ToolInstallation> toolType) {
            return toolType == JDK.class;
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.OpenJDKInstaller_DescriptorImpl_displayName();
        }

        public OpenJDKVendor getDefaultVendor() {
            return new Temurin();
        }
    }
}
