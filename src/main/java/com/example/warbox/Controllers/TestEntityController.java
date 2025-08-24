package com.example.warbox.Controllers;

import com.example.warbox.Repositorys.TestEntityRepo;
import com.example.warbox.models.TestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestEntityController {
    private final TestEntityRepo repo;

    public TestEntityController(TestEntityRepo repo) {
        this.repo = repo;
    }

    @PostMapping
    public TestEntity add(@RequestParam String name) {
        TestEntity e = new TestEntity();
        e.setName(name);
        return repo.save(e);
    }

    @GetMapping
    public List<TestEntity> all() {
        return repo.findAll();
    }
}
