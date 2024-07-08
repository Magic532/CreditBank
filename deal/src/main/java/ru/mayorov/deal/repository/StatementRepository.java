package ru.mayorov.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mayorov.deal.model.Statement;


@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
}
