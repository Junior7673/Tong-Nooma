package io.tongnooma.Controller;

import io.tongnooma.Dto.NotificationDTO;
import io.tongnooma.Dto.NotificationRequestDTO;
import io.tongnooma.Service.NotificationRealtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationRestController {

        private final NotificationRealtimeService service;

        @PostMapping("/push")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<NotificationDTO> push(@RequestBody NotificationRequestDTO request) {
            NotificationDTO dto = service.createAndPush(request);
            return ResponseEntity.ok(dto);
        }

        @PutMapping("/{id}/lu")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
            NotificationDTO dto = service.markAsRead(id);
            return ResponseEntity.ok(dto);
        }

        @GetMapping("/{id}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<NotificationDTO> getById(@PathVariable Long id) {
            return ResponseEntity.ok(service.getById(id));
        }

        // GET publics selon ton choix
        @GetMapping("/utilisateur/{userId}")
        public ResponseEntity<List<NotificationDTO>> getByUser(@PathVariable Long userId) {
            return ResponseEntity.ok(service.getByUser(userId));
        }

        @GetMapping("/utilisateur/{userId}/nonlues")
        public ResponseEntity<List<NotificationDTO>> getUnreadByUser(@PathVariable Long userId) {
            return ResponseEntity.ok(service.getUnreadByUser(userId));
        }
    }


