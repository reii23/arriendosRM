package com.api.crud.repository;
import com.api.crud.models.PublicacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicacionRepository extends JpaRepository<PublicacionModel, Long> {
}
