package guru.springframework.spring7webapp.services.environment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"DEV", "default"})
@Service
public class EnvironmentServiceDevImpl implements EnvironmentService {
    @Override
    public String getEnvironment() {
        return "Development";
    }

    @Override
    public String getEnvironmentMalay() {
        return "Pembangunan";
    }
}
