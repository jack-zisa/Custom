package creoii.custom.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import net.minecraft.data.Main;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public abstract class AbstractDataManager<T extends CustomObject> {
    private static final Logger LOGGER = LogManager.getLogger();
    public Map<Identifier, T> values = ImmutableMap.of();

    public AbstractDataManager(String name, Gson gson) {
        try {
            String path = "/data/custom/" + name;
            if (Main.class.getResource(path) == null) return;
            File data = new File(Main.class.getResource(path).toURI());
            ImmutableMap.Builder<Identifier, T> builder = ImmutableMap.builder();

            if (!data.exists()) data.mkdirs();

            for (File file : data.listFiles()) {
                if (file.getName().endsWith(".json")) {
                    String fileName = file.getName();
                    try {
                        Reader reader = Files.newBufferedReader(Paths.get(data + "/".concat(fileName)));
                        T obj = createCustomObject(reader, gson);
                        builder.put(obj.getIdentifier(), obj);
                    } catch (Exception block) {
                        LOGGER.error("Couldn't parse block {}", fileName, block);
                    }
                }
            }
            values = builder.build();
        } catch (URISyntaxException e) {
            LOGGER.error(e);
        }
    }

    abstract T createCustomObject(Reader reader, Gson gson);
}
