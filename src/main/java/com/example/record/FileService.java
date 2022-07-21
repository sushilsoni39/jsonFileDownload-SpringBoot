package com.example.record;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileService {

    public void getPopulatedFile(Object inputPayload, int times) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("object.json"));

            // write object to file
            writer.write("[");
            for(int i=0; i<times-1;i++)
                writer.write(inputPayload.toString()+",");
            writer.write(inputPayload.toString()+"]");
            // close writer
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}