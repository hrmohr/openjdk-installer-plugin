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

import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;
import hudson.model.DownloadService;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class OpenJDKVendor extends AbstractDescribableImpl<OpenJDKVendor> implements ExtensionPoint {
    private String version;
    private String release;

    protected OpenJDKVendor() {
        super();
    }

    public String getVersion() {
        return version;
    }

    @DataBoundSetter
    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelease() {
        return release;
    }

    @DataBoundSetter
    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public OpenJDKVendorDescriptor getDescriptor() {
        return (OpenJDKVendorDescriptor) super.getDescriptor();
    }

    public static class OpenJDKVersionList extends DownloadService.Downloadable {
        public List<OpenJDKVersion> versions;

        public OpenJDKVersionList() {
            super();
        }

        public OpenJDKVersionList(Class<? extends OpenJDKVendor> clazz) {
            super(clazz);
        }

        public List<OpenJDKVersion> toList() {
            JSONObject root;
            try {
                root = getData();
                if (root == null) {
                    // JSON file has not yet been downloaded by Jenkins
                    return null;
                }
            } catch (IOException e) {
                // JSON parsing exception occurred
                return null;
            }

            Map<String, Class<?>> classMap = new HashMap<>();
            classMap.put("versions", OpenJDKVersion.class);
            classMap.put("releases", OpenJDKRelease.class);
            classMap.put("binaries", OpenJDKBinary.class);
            return ((OpenJDKVersionList) JSONObject.toBean(root, OpenJDKVersionList.class, classMap)).versions;
        }
    }

    public static class OpenJDKVersion {
        public String id;
        public String name;
        public List<OpenJDKRelease> releases;
    }

    public static class OpenJDKRelease {
        public String id;
        public String name;
        public List<OpenJDKBinary> binaries;
    }

    public static class OpenJDKBinary {
        public String name;
        public String url;
    }
}
