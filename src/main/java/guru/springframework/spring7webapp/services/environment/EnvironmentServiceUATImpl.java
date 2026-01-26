package guru.springframework.spring7webapp.services.environment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("UAT")
@Service
public class EnvironmentServiceUATImpl implements EnvironmentService {
    @Override
    public String getEnvironment() {
        return "User Acceptance Testing";
    }

    @Override
    public String getEnvironmentMalay() {
        return "Ujian Penerimaan Pengguna";
    }
}
