package guru.springframework.spring7webapp.services.i18n;

import guru.springframework.spring7webapp.services.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"MY", "default"})
@Service("i18NService")
public class MalayGreetingServiceImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello dari MalayGreetingServiceImpl";
    }
}
