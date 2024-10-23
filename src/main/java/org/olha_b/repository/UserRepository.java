package org.olha_b.repository;

import org.olha_b.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User car);

    List<User> getAll();

    Optional<User> getById(Long id);

    User update(User car);

    boolean deleteById(Long id);
}
