package team.devot.budgetapp.mapper;

/**
 * A generic mapping interface for converting between entity and DTO objects.
 *
 * @param <Entity> The type of the entity object.
 * @param <Dto> The type of the DTO (Data Transfer Object).
 */
public interface Mapper<Entity, Dto> {

    /**
     * Maps an entity object to a DTO object.
     *
     * @param entity The entity object to be mapped.
     * @return The corresponding DTO object.
     */
    Dto mapTo(Entity entity);

    /**
     * Maps a DTO object to an entity object.
     *
     * @param dto The DTO object to be mapped.
     * @return The corresponding entity object.
     */
    Entity mapFrom(Dto dto);
}
