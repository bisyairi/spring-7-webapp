package guru.springframework.spring7webapp.controllers;
import guru.springframework.spring7webapp.services.GreetingService;
import guru.springframework.spring7webapp.services.environment.EnvironmentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

@Controller
public class EnvironmentController {

    private final EnvironmentService environmentService;
    private final GreetingService greetingService;
    private final Environment environment;

    public EnvironmentController(
        EnvironmentService environmentService,
        @Qualifier("i18NService") GreetingService greetingService,
        Environment environment) {
        this.environmentService = environmentService;
        this.greetingService = greetingService;
        this.environment = environment;
    }

    public String index() {
        boolean isMalayProfile = java.util.Arrays.asList(environment.getActiveProfiles()).contains("MY");
        String envText = isMalayProfile
            ? environmentService.getEnvironmentMalay()
            : environmentService.getEnvironment();
        return greetingService.sayGreeting() + " - " + envText;
    }
}
