package com.example.demo.util;

import com.example.demo.controller.PetController;
import com.example.demo.entity.Pet;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PetAssembler implements RepresentationModelAssembler<Pet, EntityModel<Pet>> {
    @Override
    public EntityModel<Pet> toModel(Pet pet) {
        return new EntityModel<>(pet,
                linkTo(methodOn(PetController.class).findById(pet.getId())).withSelfRel(),
                linkTo(methodOn(PetController.class).all()).withRel("pets"));
    }
}
