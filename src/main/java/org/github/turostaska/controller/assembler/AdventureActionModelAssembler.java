package org.github.turostaska.controller.assembler;

import org.github.turostaska.controller.ActionController;
import org.github.turostaska.domain.AdventureAction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AdventureActionModelAssembler  implements RepresentationModelAssembler<AdventureAction, EntityModel<AdventureAction>> {
    @Override
    public EntityModel<AdventureAction> toModel(AdventureAction entity) {
        return EntityModel.of(entity,
                WebMvcLinkBuilder.linkTo(methodOn(ActionController.class).getAdventureAction(entity.getId())).withSelfRel(),
                linkTo(methodOn(ActionController.class).allAdventureActions()).withRel("adventure_actions"));
    }
}