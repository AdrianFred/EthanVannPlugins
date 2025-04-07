package com.example;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {

    public static void main(String[] args) throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String installDir;
        String configFile;

        if (osName.contains("mac")) {
            installDir = "/Applications/RuneLite.app/Contents/Resources/";
        } else if (osName.contains("win")) {
            installDir = System.getProperty("user.home") + "\\AppData\\Local\\RuneLite\\";
        } else if (osName.contains("linux")) {
            installDir = System.getProperty("user.home") + "/Desktop/runelite/";
        } else {
            System.err.println("Unsupported OS: " + osName);
            return;
        }

        String jarFile = installDir + "EthanVannInstaller.jar";
        configFile = installDir + "config.json";

        try {
            // Download the JAR file
            URL url =
                    new URL(
                            "https://github.com/Ethan-Vann/Installer/releases/download/1.0/RuneLiteHijack.jar");
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(jarFile);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();

            // Modify the config.json file
            InputStream inputStream = new FileInputStream(configFile);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject object = new JSONObject(tokener);
            inputStream.close();

            object.remove("mainClass");
            object.put("mainClass", "ca.arnah.runelite.LauncherHijack");

            if (object.has("classPath")) {
                object.remove("classPath"); //remove if it exists to reset the array
            }
            object.append("classPath", "EthanVannInstaller.jar");
            object.append("classPath", "RuneLite.jar");

            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(object.toString(2)); // Use toString(2) for pretty printing
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Installation successful!");

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
