package ink.labrador.mmsmanager.util;

import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtil {
    public static File loadFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
            ClassPathResource resource = new ClassPathResource(path);
            return resource.getFile();
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream loadFileAsInputStream(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return Files.newInputStream(file.toPath());
            }
            return new ClassPathResource(path).getInputStream();
        } catch (Exception e) {
            return null;
        }
    }

    public static InputStream loadFileAsInputStream(File file) {
        try {
            return Files.newInputStream(file.toPath());
        } catch (Exception e) {
            return null;
        }
    }

    public static String loadFileAsString(String path, Charset charsets) {
        InputStream stream = loadFileAsInputStream(path);
        byte[] bytes = readAllBytesAndClose(stream);
        String str = new String(bytes, charsets);
        return str;
    }

    public static String loadFileAsString(String path) {
        return loadFileAsString(path, StandardCharsets.UTF_8);
    }

    public static byte[] loadFileAsBytes(String path) {
        InputStream stream = loadFileAsInputStream(path);
        return readAllBytesAndClose(stream);
    }

    public static String loadFileAsString(File file, Charset charsets) {
        InputStream stream = loadFileAsInputStream(file);
        byte[] bytes = readAllBytesAndClose(stream);
        String str = new String(bytes, charsets);
        return str;
    }

    public static byte[] readAllBytesAndClose(InputStream stream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            stream.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
