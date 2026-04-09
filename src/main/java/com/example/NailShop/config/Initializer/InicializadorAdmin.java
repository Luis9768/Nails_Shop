package com.example.NailShop.config.Initializer;

import com.example.NailShop.entity.Perfil;
import com.example.NailShop.entity.Usuario;
import com.example.NailShop.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

public class InicializadorAdmin {
    @Bean
    public CommandLineRunner inicializacaoAdmin(UsuarioRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            if (repository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setEmail("admin@email.com");
                admin.setSenha(passwordEncoder.encode("123456"));
                admin.setPerfil(Perfil.ADMIN);
                repository.save(admin);

                System.out.println("==================================================");
                System.out.println("🚀 USUÁRIO MESTRE CRIADO COM SUCESSO!");
                System.out.println("📧 Login: admin@email.com");
                System.out.println("🔑 Senha: 123456");
                System.out.println("==================================================");
            }
        };
    }
}
