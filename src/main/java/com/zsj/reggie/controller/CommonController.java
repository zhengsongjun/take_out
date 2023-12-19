package com.zsj.reggie.controller;

import com.zsj.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.img-path}")
    private String basePath;


    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        name = name + uuid + suffix;
        try {
           file.transferTo(new File(basePath+name));
       }catch(IOException e){
           e.printStackTrace();
       }
       return R.success(name);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while( (len = fileInputStream.read(bytes)) != -1 ){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.write(bytes,0,len);
            outputStream.flush();

        }catch(Exception e){

        }
    }

}
