package com.mercado_liebre.user_service.config;

import com.mercado_liebre.user_service.model.userRol.UserRolDTO;
import com.mercado_liebre.user_service.service.UserRolServiceImpl;
import com.mercado_liebre.user_service.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
@Configuration
public class AppRunner implements CommandLineRunner {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRolServiceImpl userRolService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        if(!(userService.getAll().size() > 1)) {
            this.loadRoles();
            this.loadUserAdmin();
        }
    }

    private void loadRoles() {
        UserRolDTO rolAdmin = new UserRolDTO();
        UserRolDTO rolBasic = new UserRolDTO();
        rolAdmin.setType("ADMIN");
        rolBasic.setType("BASIC");
        userRolService.createUserRol(rolAdmin);
        userRolService.createUserRol(rolBasic);
    }
    private void loadUserAdmin() {
        //Password is "123456789"
        String sql = "INSERT INTO users(creation_date, email, last_name, name, password, sales_made, id_rol) VALUES(CURDATE(), 'admin@gmail.com', 'admin', 'admin', '$2a$12$.T6/R93EJcd3SL.1S0devumaMpqLcORyRcs/o/4z6eYUrpnd6MgRK', 0, 1)";
        jdbcTemplate.execute(sql);
    }
}
