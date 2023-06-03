package movie.compression.moviecompression.Tools;

import java.io.*;
import java.util.List;

public class Serializer {
    public static void serializationObject(Object IFrames, String filePath) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(filePath);
            ObjectOutputStream serialization = new ObjectOutputStream(fileOutput);
            serialization.writeObject(IFrames);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public static Object deserializationObject(Object objectDeserialization, String filePath) {
        try {
            FileInputStream fileInput = new FileInputStream(filePath);
            ObjectInputStream deserialization = new ObjectInputStream(fileInput);
            objectDeserialization = deserialization.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return objectDeserialization;
    }
}
