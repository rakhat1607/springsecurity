package kz.bitlab.springsecurity.services;

import kz.bitlab.springsecurity.dto.UserDto;
import kz.bitlab.springsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean registerUser(UserDto userDto);
    void updatePassword(User user , String oldPassword, String newPassword , String retypePassword);
}
