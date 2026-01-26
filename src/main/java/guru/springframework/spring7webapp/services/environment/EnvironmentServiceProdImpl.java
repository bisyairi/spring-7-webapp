package guru.springframework.spring7webapp.services.environment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("PROD")
@Service
public class EnvironmentServiceProdImpl implements EnvironmentService {
    @Override
    public String getEnvironment() {
        return "Production";
    }

    @Override
    public String getEnvironmentMalay() {
        return "Produksi";
    }
}
