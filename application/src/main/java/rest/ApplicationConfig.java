package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cors.CorsFilter.class);
        resources.add(errorhandling.mappers.API_ExceptionMapper.class);
        resources.add(errorhandling.mappers.AuthenticationExceptionMapper.class);
        resources.add(errorhandling.mappers.FetchExceptionMapper.class);
        resources.add(errorhandling.mappers.GenericExceptionMapper.class);
        resources.add(errorhandling.mappers.NoShipmentsfoundExceptionMapper.class);
        resources.add(errorhandling.mappers.NotAuthorizedExceptionMapper.class);
        resources.add(errorhandling.mappers.UnsupportedCourierExpcetionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(rest.TrackingResource.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.RolesAllowedFilter.class);
    }

}
