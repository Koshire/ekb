package com.ktaksv.ekb.repository;

import com.ktaksv.ekb.model.history.EntityType;
import com.ktaksv.ekb.model.history.HistoryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLogRepository extends BaseRepository<HistoryLog, Long> {

    Page<HistoryLog> findAllByEntityIdAndEntityType(Long id, EntityType type, Pageable pageable);
}
