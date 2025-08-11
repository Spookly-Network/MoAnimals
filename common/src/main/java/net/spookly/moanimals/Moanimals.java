package net.spookly.moanimals;

import net.spookly.moanimals.entity.MoAnimalEntityTypes;
import net.spookly.moanimals.item.MoAnimalItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Moanimals {
    public static final String MOD_ID = "moanimals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.
        MoAnimalItems.init();
        MoAnimalEntityTypes.init();
    }
}
