package ru.job4j.generic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RoleStoreTest {

    @Test
    void whenAddAndFindThenRoleNameIsAdmin() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        Role result = roleStore.findById("1");
        assertThat(result.getRoleName()).isEqualTo("Admin");
    }

    @Test
    void whenAddAndFindThenRoleIsNull() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        Role result = roleStore.findById("2");
        assertThat(result).isNull();
    }

    @Test
    void whenAddDuplicateAndFindThenRoleNameIsAdmin() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        roleStore.add(new Role("1", "User"));
        Role result = roleStore.findById("1");
        assertThat(result.getRoleName()).isEqualTo("Admin");
    }

    @Test
    void whenReplaceThenRoleNameIsUser() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        roleStore.replace("1", new Role("1", "User"));
        Role result = roleStore.findById("1");
        assertThat(result.getRoleName()).isEqualTo("User");
    }

    @Test
    void whenNoReplaceThenRoleNameIsAdmin() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        roleStore.replace("2", new Role("1", "User"));
        Role result = roleStore.findById("1");
        assertThat(result.getRoleName()).isEqualTo("Admin");
    }

    @Test
    void whenReplaceSuccessThenTrue() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        boolean result = roleStore.replace("1", new Role("1", "User"));
        assertThat(result).isTrue();
    }

    @Test
    void whenReplaceNonSuccessThenFalse() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        boolean result = roleStore.replace("2", new Role("2", "User"));
        assertThat(result).isFalse();
    }

    @Test
    void whenDeleteRoleNameThenNull() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        roleStore.delete("1");
        Role result = roleStore.findById("1");
        assertThat(result).isNull();
    }

    @Test
    void whenNoDeleteRoleNameThenRoleNameIsAdmin() {
        RoleStore roleStore = new RoleStore();
        roleStore.add(new Role("1", "Admin"));
        roleStore.delete("2");
        Role result = roleStore.findById("1");
        assertThat(result.getRoleName()).isEqualTo("Admin");
    }
}