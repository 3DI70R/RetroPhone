package ru.threedisevenzeror.retrophone.info;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ThreeDISevenZeroR on 02.11.2016.
 */
public final class MidletInfo {

    final ApplicationInfo info;
    final String name;
    final String icon;
    final String className;

    MidletInfo(ApplicationInfo info, String name, String icon, String className) {
        this.info = info;
        this.name = name;
        this.icon = icon;
        this.className = className;
    }

    public ApplicationInfo getApplicationInfo() {
        return info;
    }

    /**
     * Name is used to identify this MIDlet to the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Name of the class extending the MIDlet class. The class MUST have a public no-args constructor.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Name of an image (PNG) within the JAR for the icon of the MIDlet.
     */
    public String getIcon() {
        return icon;
    }

    public boolean hasIcon() {
        return !StringUtils.isBlank(getIcon());
    }

    public long iconSize() {
        if(hasIcon()) {
            return getApplicationInfo().getEntrySize(getIcon());
        } else {
            return 0;
        }
    }

    public InputStream getIconStream() throws IOException {
        if(hasIcon()) {
            return getApplicationInfo().getEntry(getIcon());
        } else {
            return null;
        }
    }
}
