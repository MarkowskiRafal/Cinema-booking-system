package pl.markowski.kinoteatr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

    User findByEmail(final String email);
}