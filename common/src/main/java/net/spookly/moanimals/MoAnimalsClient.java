package net.spookly.moanimals;

import net.spookly.moanimals.core.ClientPlatformHelper;
import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.client.renderer.DuckRenderer;

import static net.spookly.moanimals.Moanimals.LOGGER;

public class MoAnimalsClient {
    public static void init() {
        LOGGER.info("Initializing client");

        LOGGER.info("Registering client renderers");
        ClientPlatformHelper.registerEntityRenderers(MoAnimalEntityTypes.DUCK, DuckRenderer::new);
    }
}
