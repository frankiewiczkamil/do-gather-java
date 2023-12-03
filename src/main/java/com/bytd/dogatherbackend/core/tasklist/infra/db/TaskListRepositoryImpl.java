package com.bytd.dogatherbackend.core.tasklist.infra.db;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepositoryImpl
    extends JpaRepository<TaskListDbDtoImpl, UUID>, TaskListRepository<TaskListDbDtoImpl> {

  Optional<TaskListDbDtoImpl> findById(UUID id);

  TaskListDbDtoImpl save(TaskListDbDtoImpl taskListDbDto);
}
