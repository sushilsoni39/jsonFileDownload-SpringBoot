package com.example.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;


    @RequestMapping(path = "/insertPayload/{times}",
            method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"})
    public ResponseEntity<Resource> insertPayload(@RequestBody String inputPayload, @PathVariable int times) {

        fileService.getPopulatedFile(inputPayload, times);

        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream("object.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=response.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }


    @RequestMapping(path = "/downloadJson/{times}",
            method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"})
    public ResponseEntity<byte[]> downloadPayload(@RequestBody String inputPayload, @PathVariable int times) {

        fileService.getPopulatedFile(inputPayload, times);
        byte[] data = null;
        try {
            Path path = Paths.get("object.json");
            data = Files.readAllBytes(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=response.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }

    @RequestMapping(path = "/downloadPayload/{times}",
            method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"})
    public RequestFile uploadPayload(@RequestBody String inputPayload, @PathVariable int times) {


        fileService.getPopulatedFile(inputPayload, times);

        RequestFile requestFile = new RequestFile();
        requestFile.setFileId("abc123");
        requestFile.setFileName("response.json");
        requestFile.setFileSize(10000);
        requestFile.setContentType(HttpHeaders.CONTENT_DISPOSITION);
        try {
            Path path = Paths.get("object.json");
            byte[] data = Files.readAllBytes(path);
            requestFile.setFileContent(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestFile;

    }
}