package net.spookly.moanimals;

import static net.spookly.moanimals.Moanimals.LOGGER;

import net.spookly.moanimals.client.renderer.CrocodileRenderer;
import net.spookly.moanimals.client.renderer.DuckRenderer;
import net.spookly.moanimals.client.renderer.RacoonRenderer;
import net.spookly.moanimals.core.ClientPlatformHelper;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;


public class MoAnimalsClient {
    public static void init() {
        LOGGER.info("Initializing client");

        LOGGER.info("Registering client renderers");
        ClientPlatformHelper.registerEntityRenderers(MoAnimalEntityTypes.DUCK, DuckRenderer::new);
        ClientPlatformHelper.registerEntityRenderers(MoAnimalEntityTypes.CROCODILE, CrocodileRenderer::new);
        ClientPlatformHelper.registerEntityRenderers(MoAnimalEntityTypes.RACOON, RacoonRenderer::new);
    }
}
