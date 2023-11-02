package kz.job4j.todo.service.impl;

import kz.job4j.todo.model.entity.Priority;
import kz.job4j.todo.repository.PriorityRepository;
import kz.job4j.todo.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepository priorityRepository;

    @Override
    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    @Override
    public Optional<Priority> findById(Integer id) {
        return priorityRepository.findById(id);
    }
}
