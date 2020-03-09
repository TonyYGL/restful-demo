package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.exception.PetNotFoundException;
import com.example.demo.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets")
    List<Pet> all() {
        return petRepository.findAll();
    }

    @GetMapping("/pets/{id}")
    Pet findPetById(@PathVariable long id) {
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    /**
     * 請求JSON格式，使用@RequestBody
     * @param pet
     * @return
     */
    @PostMapping("/pet")
    Pet addPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * 請求POST FORM傳參數格式，使用@RequestParam
     */
    @PostMapping("/pet2")
    Pet addPet2(@RequestParam String name) {
        Pet pet = new Pet(name);
        return petRepository.save(pet);
    }
}
