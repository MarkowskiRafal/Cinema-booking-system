package pl.markowski.kinoteatr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.markowski.kinoteatr.model.Token;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    Token findByValue(String value);

}