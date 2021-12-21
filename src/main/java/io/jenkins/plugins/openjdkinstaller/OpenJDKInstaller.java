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
