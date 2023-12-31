package com.roberdev.gestionturismo.converter;

public interface Converter<Entity, DTO> {

    DTO convertToDTO(Entity entity);

    Entity convertToEntity(DTO dto);


}
