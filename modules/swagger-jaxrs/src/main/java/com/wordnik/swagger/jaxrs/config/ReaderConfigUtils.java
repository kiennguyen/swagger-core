package com.wordnik.swagger.jaxrs.config;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The <code>ReaderConfigUtils</code> class defines helper methods for handling
 * configuration settings for JAX-RS annotations reader.
 */
public class ReaderConfigUtils {

  private ReaderConfigUtils() {
  }

  public static void initReaderConfig(ServletConfig config) {
    if("true".equals(config.getInitParameter("scan.all.resources")) || config.getInitParameter("ignore.routes") != null) {
      final DefaultReaderConfig rc = new DefaultReaderConfig();
      rc.setScanAllResources("true".equals(config.getInitParameter("scan.all.resources")));
      final Set<String> ignoredRoutes = new LinkedHashSet<String>();
      for (String item : StringUtils.trimToEmpty(config.getInitParameter("ignore.routes")).split(",")) {
        final String route = StringUtils.trimToNull(item);
        if (route != null) {
          ignoredRoutes.add(route);
        }
      }
      rc.setIgnoredRoutes(ignoredRoutes);
      config.getServletContext().setAttribute(getAttributeName(), rc);
    }
  }

  public static ReaderConfig getReaderConfig(ServletContext context) {
    final Object attr = context.getAttribute(getAttributeName());
    if (attr instanceof ReaderConfig) {
      return (ReaderConfig) attr;
    }
    return null;
  }

  private static String getAttributeName() {
    return ReaderConfig.class.getName();
  }
}
