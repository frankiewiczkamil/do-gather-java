package com.bytd.dogatherbackend.core.tasklist.infra.db.h2;

import com.bytd.dogatherbackend.core.tasklist.app.TaskListRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepositoryH2Impl
    extends JpaRepository<TaskListDbDtoH2Impl, UUID>, TaskListRepository<TaskListDbDtoH2Impl> {

  Optional<TaskListDbDtoH2Impl> findById(UUID id);

  TaskListDbDtoH2Impl save(TaskListDbDtoH2Impl taskListDbDto);
}
