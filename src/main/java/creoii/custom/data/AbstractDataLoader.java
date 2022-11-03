package creoii.custom.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import creoii.custom.Custom;
import creoii.custom.util.GeneralUtil;
import net.minecraft.data.Main;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataLoader<T extends Identifiable> {
    private static final boolean useMinecraftDir = true;

    private Map<Identifier, T> values;
    private final String name;
    private final String prettyName;
    private final List<String> failedLoadNames;

    public AbstractDataLoader(String name, Gson gson) {
        this.name = name;
        prettyName = GeneralUtil.capitalizeAfterAll(StringUtils.replaceChars(StringUtils.replaceChars(StringUtils.removeEnd(name, "s"), '/', ' '), '_', ' '), ' ');
        failedLoadNames = new ArrayList<>();
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
                        failedLoadNames.add(fileName);
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
                InputStream stream = Main.class.getResourceAsStream(dataPath);
                if (stream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    while ((resource = reader.readLine()) != null) {
                        if (resource.endsWith(".json")) {
                            try {
                                T obj = createCustomObject(Files.newBufferedReader(new File(Main.class.getResource(dataPath).getPath() + "/".concat(resource)).toPath()), gson);
                                builder.put(obj.getIdentifier(), obj);
                            } catch (Exception block) {
                                failedLoadNames.add(resource);
                                Custom.LOGGER.error("Couldn't parse {}", resource, block);
                            }
                        }
                    }
                    reader.close();
                    values = builder.build();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<Identifier, T> getValues() {
        return values;
    }

    public String getName() {
        return name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public List<String> getFailedLoads() {
        return failedLoadNames;
    }

    public void printLoads() {
        if (getFailedLoads().size() > 0) {
            Custom.LOGGER.error("Failed " + getPrettyName() + " Loads: " + getFailedLoads().size());
            for (String name : getFailedLoads()) {
                Custom.LOGGER.error("   at '" + name + "'");
            }
        }

        if (values != null) {
            if (values.size() > 0) {
                Custom.LOGGER.info("Successful " + getPrettyName() + " Loads: " + (values.size() - getFailedLoads().size()));
            }
        }
    }

    abstract T createCustomObject(Reader reader, Gson gson);
}
