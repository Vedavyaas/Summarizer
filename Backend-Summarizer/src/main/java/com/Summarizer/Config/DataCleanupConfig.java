////Testing purpose
//
//package com.Summarizer.Config;
//
//import com.Summarizer.Repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DataCleanupConfig {
//
//    private final UserRepository userRepository;
//
//    public DataCleanupConfig(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Bean
//    public CommandLineRunner deleteUsersOnStartup() {
//        return args -> {
//            userRepository.deleteAll();  // This deletes all users on app start
//            System.out.println("Deleted all users from DB");
//        };
//    }
//}
