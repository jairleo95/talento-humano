package com.app.identity.web;

import com.app.identity.domain.Privilege;
import com.app.identity.domain.Role;
import com.app.identity.domain.RolePrivilege;
import com.app.identity.persistence.PrivilegeRepository;
import com.app.identity.persistence.RolePrivilegeRepository;
import com.app.identity.persistence.RoleRepository;
import com.app.identity.web.dto.PrivilegeRequest;
import com.app.identity.web.dto.RoleRequest;
import com.app.identity.web.dto.RoleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/privileges")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeRepository privilegeRepo;
    private final RoleRepository roleRepo;
    private final RolePrivilegeRepository rolePrivilegeRepo;

    @GetMapping
    public Flux<Privilege> list() {
        return privilegeRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Privilege> create(@RequestBody @Valid PrivilegeRequest request) {
        Privilege privilege = new Privilege();
        privilege.setId(UUID.randomUUID());
        privilege.setCode(request.code());
        privilege.setDescription(request.description());
        privilege.setLinkUrl(request.linkUrl());
        privilege.setIcon(request.icon());
        privilege.setModuleName(request.moduleName());
        privilege.setSortOrder(request.sortOrder());
        return privilegeRepo.save(privilege);
    }

    @GetMapping("/roles/{roleId}")
    public Flux<Privilege> listByRole(@PathVariable UUID roleId) {
        return rolePrivilegeRepo.findByRoleIdAndIsActiveTrue(roleId)
                .map(RolePrivilege::getPrivilegeId)
                .collectList()
                .flatMapMany(privilegeRepo::findAllById);
    }
}

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
class RoleManagementController {

    private final RoleRepository roleRepo;
    private final RolePrivilegeRepository rolePrivilegeRepo;
    private final PrivilegeRepository privilegeRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Role> create(@RequestBody @Valid RoleRequest request) {
        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName(request.name());
        role.setDescription(request.description());
        return roleRepo.save(role);
    }

    @GetMapping("/{roleId}/privileges")
    public Flux<Privilege> listPrivileges(@PathVariable UUID roleId) {
        return rolePrivilegeRepo.findByRoleIdAndIsActiveTrue(roleId)
                .map(RolePrivilege::getPrivilegeId)
                .collectList()
                .flatMapMany(privilegeRepo::findAllById);
    }

    @PatchMapping("/{roleId}/privileges")
    public Mono<Void> updatePrivileges(@PathVariable UUID roleId, @RequestBody Map<String, List<UUID>> body) {
        List<UUID> privilegeIds = body.getOrDefault("privilegeIds", List.of());
        return rolePrivilegeRepo.findByRoleIdAndIsActiveTrue(roleId)
                .flatMap(rp -> rolePrivilegeRepo.deleteById(rp.getId()))
                .thenMany(Flux.fromIterable(privilegeIds))
                .flatMap(privId -> {
                    RolePrivilege rp = new RolePrivilege();
                    rp.setId(UUID.randomUUID());
                    rp.setRoleId(roleId);
                    rp.setPrivilegeId(privId);
                    rp.setSortOrder(0);
                    rp.setIsActive(true);
                    return rolePrivilegeRepo.save(rp);
                })
                .then();
    }
}
