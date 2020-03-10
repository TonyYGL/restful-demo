package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.exception.PetNotFoundException;
import com.example.demo.repository.PetRepository;
import com.example.demo.util.PetAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetAssembler petAssembler;

    @GetMapping("/pets")
    public CollectionModel<EntityModel<Pet>> all() {
        List<EntityModel<Pet>> pets = petRepository.findAll().stream()
                .map(petAssembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(pets,
                linkTo(methodOn(PetController.class).all()).withSelfRel());
    }

    @GetMapping("/pets/{id}")
    public EntityModel<Pet> findById(@PathVariable long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        return petAssembler.toModel(pet);
    }

    /**
     * 請求JSON格式，使用@RequestBody
     *
     * @param pet
     * @return
     */
    @PostMapping("/pet")
    Pet add(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * 請求POST FORM傳參數格式，使用@RequestParam
     */
    @PostMapping("/pet2")
    Pet add2(@RequestParam String name) {
        Pet pet = new Pet(name);
        return petRepository.save(pet);
    }

    @DeleteMapping("/pet/{id}")
    void deleteById(@PathVariable Long id) {
        petRepository.deleteById(id);
    }

    /**
     * 更新
     *
     * @param newPet
     * @param id
     * @return
     */
    @PutMapping("/pet/{id}")
    public ResponseEntity<?> updateById(@RequestBody Pet newPet, @PathVariable Long id) {
        Pet updatedPet = petRepository.findById(id).map(pet -> {
            pet.setName(newPet.getName());
            return petRepository.save(pet);
        }).orElseGet(() -> {
            newPet.setId(id);
            return petRepository.save(newPet);
        });

        EntityModel<Pet> entityModel = petAssembler.toModel(updatedPet);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
