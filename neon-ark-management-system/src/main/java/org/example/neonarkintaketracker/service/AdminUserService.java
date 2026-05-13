package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.dto.AdminUserResponse;
import org.example.neonarkintaketracker.entity.SystemUser;
import org.example.neonarkintaketracker.exception.AccessForbiddenException;
import org.example.neonarkintaketracker.repository.SystemUserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminUserService {
    // Store user repo
    private final SystemUserRepository systemUserRepository;

    // Constructor
    public AdminUserService(SystemUserRepository systemUserRepository) {
        this.systemUserRepository = systemUserRepository;
    }

    // get all users list
    public List<AdminUserResponse> getAllUsers(String roleCheck) {
        // ensure admin with if statement.
        if (roleCheck == null || !roleCheck.equalsIgnoreCase("ADMIN")){
            throw new AccessForbiddenException("Access Denied: Admin access needed.");
        }

        //  return all users.
        return systemUserRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    // create map function for AdminUserResponse
    private AdminUserResponse mapToResponse(SystemUser user) {
        return new AdminUserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}
