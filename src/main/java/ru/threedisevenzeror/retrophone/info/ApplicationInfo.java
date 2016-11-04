package ru.threedisevenzeror.retrophone.info;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * Created by ThreeDISevenZeroR on 02.11.2016.
 * Object that holds information abount j2me application
 */
public class ApplicationInfo implements Closeable {

    private static final String KEY_NAME = "MIDlet-Name";
    private static final String KEY_VERSION = "MIDlet-Version";
    private static final String KEY_VENDOR = "MIDlet-Vendor";
    private static final String KEY_DESCRIPTION = "MIDlet-Description";
    private static final String KEY_INFO_URL = "MIDlet-Info-URL";
    private static final String KEY_DATA_SIZE = "MIDlet-Data-Size";
    private static final String KEY_JAR_SIZE = "MIDlet-Jar-Size";
    private static final String KEY_JAR_URL = "MIDlet-Jar-URL";
    private static final String KEY_ICON = "MIDlet-Icon";
    public static final String KEY_MICRO_EDITION_PROFILE = "MicroEdition-Profile";
    public static final String KEY_MICRO_EDITION_CONFIGURATION = "MicroEdition-Configuration";

    private Manifest manifest;
    private JarFile jar;
    private MidletInfo[] midlets;
    private File jarFile;
    private File jadFile;
    private boolean isClosed;

    private ApplicationInfo() {
    }

    public boolean isJ2meApplication() {
        return midlets.length != 0 &&
                !StringUtils.isBlank(getMainAttribute(KEY_NAME)) &&
                !StringUtils.isBlank(getMainAttribute(KEY_VERSION)) &&
                !StringUtils.isBlank(getMainAttribute(KEY_VENDOR)) &&
                !StringUtils.isBlank(getMainAttribute(KEY_MICRO_EDITION_PROFILE)) &&
                !StringUtils.isBlank(getMainAttribute(KEY_MICRO_EDITION_CONFIGURATION));
    }

    /**
     * The name of the MIDlet suite that identifies the MIDlets to the user.
     */
    public String getName() {
        return getMainAttribute(KEY_NAME);
    }

    /**
     * The version number of the MIDlet suite. The format is major.minor.micro as described in the JDK Product Versioning Specification.
     * It can be used by the application management software for install and upgrade uses, as well as communication with the user.
     */
    public String getVersion() {
        return getMainAttribute(KEY_VERSION);
    }

    /**
     * The organization that provides the MIDlet suite.
     */
    public String getVendor() {
        return getMainAttribute(KEY_VENDOR);
    }

    /**
     * The name of a PNG file within the JAR file used to represent the MIDlet suite.
     * It should be used when the Application Management Software displays an icon to identify the suite.
     */
    public String getIcon() {
        return getMainAttribute(KEY_ICON);
    }

    /**
     * The description of the MIDlet suite.
     */
    public String getDescription() {
        return getMainAttribute(KEY_DESCRIPTION);
    }

    /**
     * A URL for information further describing the MIDlet suite.
     */
    public String getDescriptionURL() {
        return getMainAttribute(KEY_INFO_URL);
    }

    /**
     * The minimum number of bytes of persistent data required by the MIDlet.
     * The device may provide additional storage according to its own policy. The default is zero.
     */
    public long getDataSize() {
        String dataSize = getMainAttribute(KEY_DATA_SIZE);
        if(dataSize != null) {
            return Long.parseLong(dataSize);
        } else {
            return 0;
        }
    }

    public MidletInfo[] getMidlets() {
        return midlets;
    }

    /**
     * Returns jar size in bytes
     */
    public long getJarSize() {
        if(jarFile != null) {
            return jarFile.length();
        } else {
            return Long.parseLong(getMainAttribute(KEY_JAR_SIZE).trim());
        }
    }

    /**
     * Open input stream of application jar file
     * @throws IOException if io exception occured
     */
    public InputStream getJarStream() throws IOException {
        if(jarFile != null) {
            return new FileInputStream(jarFile);
        } else {
            String jarUrl = getMainAttribute(KEY_JAR_URL);
            if(isRelativeUrl(jarUrl)) {
                return new FileInputStream(new File(jadFile.getParent(), jarUrl));
            } else {
                return new URL(jarUrl).openStream();
            }
        }
    }

    /**
     * The J2ME profile required, using the same format and value as the
     * System property microedition.profiles (for example “MIDP-1.0”).
     */
    public String getRequiredProfile() {
        return getMainAttribute(KEY_MICRO_EDITION_PROFILE);
    }

    /**
     * The J2ME Configuration required using the same format and value as the
     * System property microedition.configuration (for example “CLDC-1.0”).
     */
    public String getRequiredConfiguration() {
        return getMainAttribute(KEY_MICRO_EDITION_CONFIGURATION);
    }

    /**
     * TODO: Javadoc
     */
    public boolean hasIcon() {
        return jar != null && manifest.getMainAttributes().containsKey(KEY_ICON);
    }

    /**
     * TODO: Javadoc
     */
    public long getIconSize() {
        if(jar != null) {
            return jar.getEntry(KEY_ICON).getSize();
        } else {
            return 0;
        }
    }

    /**
     * TODO: Javadoc
     */
    public InputStream getIconStream() throws IOException {
        String iconName = getMainAttribute(KEY_ICON);
        if (!StringUtils.isBlank(iconName)) {
            return getEntry(iconName);
        } else {
            return null;
        }
    }

    /**
     * TODO: Javadoc
     */
    public String getMainAttribute(String name) {
        return manifest.getMainAttributes().getValue(name);
    }

    /**
     * TODO: Javadoc
     */
    public long getEntrySize(String file) {
        if(jar != null) {
            return jar.getEntry(file).getSize();
        } else {
            return 0;
        }
    }

    public boolean hasEntry(String file) {
        return jar != null && jar.getEntry(file) != null;
    }

    /**
     * TODO: Javadoc
     */
    public InputStream getEntry(String file) throws IOException {
        if(jar != null) {
            ZipEntry entry = jar.getEntry(file);
            return jar.getInputStream(entry);
        } else {
            throw new IOException("Cannot get entry " + file + " from jad file");
        }
    }

    /**
     * Closes this application info.
     * Releases all allocated file streams
     */
    public void close() {
        if(jar != null) {
            IOUtils.closeQuietly(jar);
        }

        isClosed = true;
    }

    /**
     * Is this application info is closed
     */
    public boolean isClosed() {
        return isClosed;
    }

    public static ApplicationInfo fromJad(String jadFile) throws IOException {
        return fromJad(new File(jadFile));
    }

    public static ApplicationInfo fromJad(File jadFile) throws IOException {

        ApplicationInfo info = new ApplicationInfo();
        info.jadFile = jadFile;
        info.manifest = new Manifest(new FileInputStream(jadFile));
        info.midlets = loadMidletsFromManifest(info, info.manifest);

        return info;
    }

    public static ApplicationInfo fromJar(String jarFile) throws IOException {
        return fromJar(new File(jarFile));
    }

    public static ApplicationInfo fromJar(File jarFile) throws IOException {
        ApplicationInfo info = new ApplicationInfo();

        info.jar = new JarFile(jarFile);
        info.jarFile = jarFile;
        info.manifest = info.jar.getManifest();
        info.midlets = loadMidletsFromManifest(info, info.manifest);

        return info;
    }

    private boolean isRelativeUrl(String url) {
        return !url.startsWith("http");
    }

    private static MidletInfo[] loadMidletsFromManifest(ApplicationInfo info, Manifest manifest) {
        List<MidletInfo> midlets = new ArrayList<MidletInfo>();
        int midletIndex = 1;

        while (true) {
            String midletName = "MIDlet-" + midletIndex;
            String midletInfo = manifest.getMainAttributes().getValue(midletName);

            if(midletInfo != null) {
                String[] infoParts = midletInfo.split(", ");
                String name = infoParts[0].trim();
                String icon = infoParts[1].trim();
                String className = infoParts[2].trim();
                midlets.add(new MidletInfo(info, name, icon, className));
                midletIndex++;
            } else {
                break;
            }
        }

        return midlets.toArray(new MidletInfo[midlets.size()]);
    }
}
