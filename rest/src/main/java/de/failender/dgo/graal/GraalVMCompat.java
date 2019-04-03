package de.failender.dgo.graal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.util.log.Log;
import org.graalvm.nativeimage.Feature;
import org.graalvm.nativeimage.RuntimeReflection;
import org.slf4j.impl.StaticLoggerBinder;

public class GraalVMCompat implements Feature {

    public void beforeAnalysis(BeforeAnalysisAccess access) {

        RuntimeReflection.register(ServletMapping[].class);
        RuntimeReflection.register(StaticLoggerBinder.class);
        RuntimeReflection.register(ObjectMapper.class);
    }
}
