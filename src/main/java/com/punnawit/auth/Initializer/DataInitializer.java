package com.punnawit.auth.Initializer;

import com.punnawit.auth.entity.Role;
import com.punnawit.auth.entity.Roles;
import com.punnawit.auth.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            for (Roles role : Roles.values()) {
                roleRepository.save(new Role(role));
                log.info("Saved role: {}", role.name());
            }
        }

    }
}
