package com.funlearn.server.api;

import com.funlearn.server.api.model.ModelQuestDTO;
import com.funlearn.server.service.ModelQuestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/quests/")
public class ModelQuestController {
    private final ModelQuestService modelQuestService;

    public ModelQuestController(ModelQuestService modelQuestService) {
        this.modelQuestService = modelQuestService;
    }

    @GetMapping("/user/{userid}")
    public List<ModelQuestDTO> getModelQuestsByUserId(@PathVariable UUID userid) {
        return modelQuestService.getModelQuestsByUserId(userid);
    }
    @GetMapping("/{id}")
    public ModelQuestDTO getModelQuestId(@PathVariable UUID id) {
        return modelQuestService.getModelQuestByQuestId(id);
    }
    @PostMapping
    public void save(@RequestBody ModelQuestDTO modelQuest) {
        modelQuestService.save(modelQuest);
    }

    @PutMapping()
    public void update(@RequestBody ModelQuestDTO modelQuest) {
        modelQuestService.update(modelQuest);
    }
}
