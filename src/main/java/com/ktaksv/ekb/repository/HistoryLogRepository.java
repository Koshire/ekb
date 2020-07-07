package com.ktaksv.ekb.repository;

import com.ktaksv.ekb.model.history.HistoryLog;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLogRepository extends BaseRepository<HistoryLog, Long> {
}
