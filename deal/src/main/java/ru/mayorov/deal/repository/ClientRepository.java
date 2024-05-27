package ru.mayorov.deal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mayorov.deal.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
