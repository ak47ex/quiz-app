package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.Shortcut;

public interface ShortcutRepository extends CrudRepository<Shortcut, String> {
}
