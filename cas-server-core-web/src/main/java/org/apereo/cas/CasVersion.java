package org.apereo.cas;

import com.google.common.base.Throwables;
import org.apache.commons.io.IOUtils;
import org.apereo.cas.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.VfsResource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Properties;

/**
 * Class that exposes the CAS version. Fetches the "Implementation-Version"
 * manifest attribute from the jar file.
 *
 * @author Dmitriy Kopylenko
 * @since 3.0.0
 */
public class CasVersion {
    private static Logger LOGGER = LoggerFactory.getLogger(CasVersion.class);

    private static final String VERSION;
    private static final ZonedDateTime DATE_TIME;

    static {
        try {
            final Class clazz = CasVersion.class;
            final URL resource = clazz.getResource(clazz.getSimpleName() + ".class");
            final Properties properties = new Properties();
            InputStream inputStream = null;
            try {
                final String[] split = clazz.getName().split("\\.");
                final StringBuilder relativeUrl = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    relativeUrl.append("../");
                }
                relativeUrl.append("META-INF/MANIFEST.MF");
                final URL url = new URL(resource, relativeUrl.toString());
                inputStream = url.openStream();
                properties.load(inputStream);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (inputStream != null) {
                    IOUtils.closeQuietly(inputStream);
                }
            }
            String change = properties.getProperty("Change", null);
            VERSION = CasVersion.class.getPackage().getImplementationVersion() + (change == null ? "" : ("_" + change));

            long lastModified = 0;
            if ("file".equals(resource.getProtocol())) {
                lastModified = new File(resource.toURI()).lastModified();
            } else if ("jar".equals(resource.getProtocol())) {
                final String path = resource.getPath();
                final File file = new File(path.substring(5, path.indexOf('!')));
                lastModified = file.lastModified();
            } else if ("vfs".equals(resource.getProtocol())) {
                final File file = new VfsResource(resource.openConnection().getContent()).getFile();
                lastModified = file.lastModified();
            }
            if (lastModified != 0) {
                DATE_TIME = DateTimeUtils.zonedDateTimeOf(lastModified);
            } else {
                final String property = properties.getProperty("Implementation-Date", null);
                DATE_TIME = property == null ? DateTimeUtils.zonedDateTimeOf(0) : DateTimeUtils.zonedDateTimeOf(property);
            }
        } catch (final Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * Private constructor for CasVersion. You should not be able to instantiate
     * this class.
     */
    protected CasVersion() {
        // this class is not instantiable
    }

    /**
     * @return Return the full CAS version string.
     * @see java.lang.Package#getImplementationVersion
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Gets last modified date/time for the module.
     *
     * @return the date/time
     */
    public static ZonedDateTime getDateTime() {
        return DATE_TIME;
    }
}
