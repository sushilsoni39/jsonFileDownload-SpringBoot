package com.example.record;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {

    public void getPopulatedFile(String inputPayload, int times) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("object.json"));

            ArrayList<Object> list = new ArrayList<>();
            // write object to file
            for(int i=0; i<times;i++)
                list.add(inputPayload);

            writer.write(list.toString());
            // close writer
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}