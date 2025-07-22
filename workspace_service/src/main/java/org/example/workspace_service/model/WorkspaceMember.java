package org.example.workspace_service.model;

// WorkspaceMember.java

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WorkspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // From user-service

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Workspace workspace;

    // getters and setters
}
