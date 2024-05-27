package ru.mayorov.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mayorov.deal.model.Credit;

public interface CreditRepository extends JpaRepository<Credit, Long> { }
