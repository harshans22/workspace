package org.example.workspace_service.service;

import org.example.workspace_service.model.Role;
import org.example.workspace_service.model.Workspace;
import org.example.workspace_service.model.WorkspaceMember;
import org.example.workspace_service.repository.WorkspaceMemberRepository;
import org.example.workspace_service.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceService {
    @Autowired
    WorkspaceRepository workspaceRepo;
    @Autowired
    WorkspaceMemberRepository memberRepo;

    public Workspace createWorkspace(String name, String description, Long userId) {
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setDescription(description);
        ws = workspaceRepo.save(ws);

        WorkspaceMember owner = new WorkspaceMember();
        owner.setRole(Role.OWNER);
        owner.setWorkspace(ws);
        owner.setUserId(userId);
        memberRepo.save(owner);

        ws.getMembers().add(owner);
        return workspaceRepo.save(ws);
    }

    public List<Workspace> getAllForUser(Long userId) {
        List<WorkspaceMember> memberships = memberRepo.findByUserId(userId);
        return memberships.stream()
            .map(WorkspaceMember::getWorkspace)
            .toList();
    }

    public void addMember(Long workspaceId, Long userId, Role role) {
        Workspace ws = workspaceRepo.findById(workspaceId)
            .orElseThrow(() -> new RuntimeException("Workspace not found"));
        WorkspaceMember member = new WorkspaceMember();
        member.setUserId(userId);
        member.setRole(role);
        member.setWorkspace(ws);
        memberRepo.save(member);
    }

    public void removeMember(Long workspaceId, Long userId) {
        List<WorkspaceMember> members = memberRepo.findByUserId(userId);
        for (WorkspaceMember m : members) {
            if (m.getWorkspace().getId().equals(workspaceId)) {
                memberRepo.delete(m);
            }
        }
    }
}
