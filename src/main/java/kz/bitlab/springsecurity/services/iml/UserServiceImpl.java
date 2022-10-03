package kz.bitlab.springsecurity.services.iml;

import kz.bitlab.springsecurity.dto.UserDto;
import kz.bitlab.springsecurity.model.Permission;
import kz.bitlab.springsecurity.model.User;
import kz.bitlab.springsecurity.repository.PermissionRepository;
import kz.bitlab.springsecurity.repository.UserRepository;
import kz.bitlab.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    @Override
    public boolean registerUser(UserDto userDto) {
        if (userDto.getPassword().equals(userDto.getRetypePassword())) {
            User chekUser = userRepository.findByEmail(userDto.getEmail());
            if (chekUser == null) {
                Permission permission = permissionRepository.findByName("ROLE_USER");
                User user = new User();
                user.setEmail(userDto.getEmail());
                user.setFullName(userDto.getFullName());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                List<Permission> permissions = new ArrayList<>();
                permissions.add(permission);
                user.setPermission(permissions);
                User newUser = userRepository.save(user);
                return newUser.getId() != null;
            }
        }
        return false;
    }

    @Override
    public void updatePassword(User user, String oldPassword, String newPassword, String retypePassword) {

    }
}
