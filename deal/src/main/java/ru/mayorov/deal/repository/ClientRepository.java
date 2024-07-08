package ru.mayorov.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mayorov.deal.model.Client;

@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {

}
