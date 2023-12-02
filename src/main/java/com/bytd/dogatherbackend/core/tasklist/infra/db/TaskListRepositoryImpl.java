package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import com.bytd.dogatherbackend.core.tasklist.domain.dto.TaskListDbDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepositoryImpl
    extends JpaRepository<TaskListDbDtoImpl, UUID>, TaskListRepository {

  Optional<TaskListDbDtoImpl> findById(UUID id);

  void save(TaskListDbDto taskListDbDto);
}
