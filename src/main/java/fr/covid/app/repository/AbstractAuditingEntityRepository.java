package fr.covid.app.repository;

import fr.covid.app.domain.AbstractAuditingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface AbstractAuditingEntityRepository <T extends AbstractAuditingEntity> extends JpaRepository<T,Integer> {

}
