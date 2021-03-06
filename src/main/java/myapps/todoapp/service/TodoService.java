package myapps.todoapp.service;

import myapps.todoapp.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface TodoService {

    public Page<Todo> list(int page, int size);

    Todo save(Todo todo);

    void delete(Long id);

    void deleteAll(Collection<Long> ids);

}
