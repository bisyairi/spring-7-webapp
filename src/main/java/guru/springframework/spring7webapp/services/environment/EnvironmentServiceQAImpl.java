package guru.springframework.spring7webapp.services.environment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("QA")
@Service
public class EnvironmentServiceQAImpl implements EnvironmentService {
    @Override
    public String getEnvironment() {
        return "Quality Assurance";
    }

    @Override
    public String getEnvironmentMalay() {
        return "Jaminan Kualiti";
    }
}
