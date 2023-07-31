package com.example.restaurant.repository;

import com.example.restaurant.repository.model.TableEntity;
import com.example.restaurant.repository.model.TableLocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {

    @Transactional(readOnly = true, noRollbackFor = Exception.class)
    @Query("SELECT distinct t FROM #{#entityName} t WHERE t.locationType = :locationType")
    List<TableEntity> findByTableLocationType(TableLocationType locationType);
}
