package creoii.custom.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import creoii.custom.Custom;
import net.minecraft.data.Main;
import net.minecraft.util.Identifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public abstract class AbstractDataManager<T extends CustomObject> {
    public Map<Identifier, T> values;
    private static final boolean useMinecraftDir = true;

    public AbstractDataManager(String name, Gson gson) {
        if (useMinecraftDir) {
            File data = new File("./data/custom/" + name);
            if (!data.exists()) return;
            ImmutableMap.Builder<Identifier, T> builder = ImmutableMap.builder();
            for (File file : data.listFiles()) {
                if (file.getName().endsWith(".json")) {
                    String fileName = file.getName();
                    try {
                        T obj = createCustomObject(Files.newBufferedReader(Paths.get(data + "/" + fileName)), gson);
                        builder.put(obj.getIdentifier(), obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            values = builder.build();
        } else {
            try {
                String dataPath = "/data/custom/" + name;
                if (Main.class.getResource(dataPath) == null) return;
                ImmutableMap.Builder<Identifier, T> builder = ImmutableMap.builder();
                String resource;
                BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(dataPath)));
                while ((resource = reader.readLine()) != null) {
                    if (resource.endsWith(".json")) {
                        System.out.println(resource);
                        try {
                            T obj = createCustomObject(Files.newBufferedReader(new File(Main.class.getResource(dataPath).getPath() + "/".concat(resource)).toPath()), gson);
                            builder.put(obj.getIdentifier(), obj);
                        } catch (Exception block) {
                            Custom.LOGGER.error("Couldn't parse {}", resource, block);
                        }
                    }
                }
                values = builder.build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    abstract T createCustomObject(Reader reader, Gson gson);
}
