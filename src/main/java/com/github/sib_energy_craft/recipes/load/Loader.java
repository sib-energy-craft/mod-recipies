package com.github.sib_energy_craft.recipes.load;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;

import java.util.Set;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@Slf4j
public class Loader implements ModInitializer {

    @Override
    @SneakyThrows
    public void onInitialize() {
        log.debug("Load: {}", Loader.class.getName());
        for (var type : Set.of(RecipeSerializers.class, RecipeTypes.class)) {
            log.debug("Loaded: {}", Class.forName(type.getName()));
        }
        log.debug("Loaded: {}", Loader.class.getName());
    }
}
