package org.example.workspace_service.repository;

// WorkspaceRepository.java

import org.example.workspace_service.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {}

