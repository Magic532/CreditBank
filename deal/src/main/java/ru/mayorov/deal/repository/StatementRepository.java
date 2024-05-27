package ru.mayorov.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mayorov.deal.model.Statement;

public interface StatementRepository extends JpaRepository<Statement, String> {
}
