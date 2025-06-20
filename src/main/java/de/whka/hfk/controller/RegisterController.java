package de.whka.hfk.controller;

import de.whka.hfk.dto.RegisterDTO;
import de.whka.hfk.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public ResponseEntity<List<RegisterDTO>> getRegisters(Authentication authentication) {
        return ResponseEntity.ok(registerService.getAccessibleRegisters(authentication));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegisterDTO>> getAllRegisters() {
        return ResponseEntity.ok(registerService.getAllRegisters());
    }

    // Admin area
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RegisterDTO> addRegister(@RequestBody RegisterDTO register) {
        return ResponseEntity.ok(registerService.addRegister(register));
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteRegister(@PathVariable UUID groupId) {
        registerService.deleteRegister(groupId);
        return ResponseEntity.ok().build();
    }
}
