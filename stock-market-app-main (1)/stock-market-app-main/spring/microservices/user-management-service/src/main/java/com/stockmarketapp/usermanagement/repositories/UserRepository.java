package com.stockmarketapp.usermanagement.repositories;

import com.stockmarketapp.usermanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
//    public boolean validateUser(String id,String Password);
}
