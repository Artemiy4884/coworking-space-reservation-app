package utils;

import java.io.*;

public class PluginClassLoader extends ClassLoader {
    private final String pluginDir;

    public PluginClassLoader(String pluginDir) {
        this.pluginDir = pluginDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = pluginDir + "/" + name.replace('.', '/') + ".class";
        try {
            byte[] classBytes = loadClassBytes(filePath);
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class: " + name, e);
        }
    }

    private byte[] loadClassBytes(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data;
            while ((data = is.read()) != -1) {
                buffer.write(data);
            }
            return buffer.toByteArray();
        }
    }
}
