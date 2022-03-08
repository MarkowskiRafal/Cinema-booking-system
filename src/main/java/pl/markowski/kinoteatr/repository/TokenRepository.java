package pl.markowski.kinoteatr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByValue(final String value);
}