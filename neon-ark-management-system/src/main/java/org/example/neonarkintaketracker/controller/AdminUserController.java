package org.example.neonarkintaketracker.controller;
import org.example.neonarkintaketracker.dto.AdminUserResponse;
import org.example.neonarkintaketracker.service.AdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users") // endpoint for users
public class AdminUserController {

    // Store user service.
    private final AdminUserService adminUserService;

    // Constructor
    public AdminUserController(AdminUserService adminUserService){
        this.adminUserService = adminUserService;
    }

    // Map to request
    @GetMapping
    public ResponseEntity<List<AdminUserResponse>> getAllUsers(
            // read the role header for check
            @RequestHeader(value = "Role", required = false) String roleCheck)
    {
        // return response 200 when admin
        return ResponseEntity.ok(adminUserService.getAllUsers(roleCheck));
    }
}
