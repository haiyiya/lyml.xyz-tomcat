package com.lyml.demo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyml.demo1.common.BaseController;
import com.lyml.demo1.common.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.UUID;

@RestController
@RequestMapping("")
public class UploadController extends BaseController {

    @Value("${path.upload}")
    private String path;

    @RequestMapping(value = "/upload")
    @ResponseBody
    public Object upload(HttpServletResponse response, HttpServletRequest request) {
        JSONObject src = new JSONObject();
        InputStream fileStream = null;
        try {
            StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
            Iterator<String> fileNames = multipartRequest.getFileNames();
            MultipartFile file = null;
            while (fileNames.hasNext()) {
                file = multipartRequest.getFiles(fileNames.next()).get(0);
                fileStream = file.getInputStream();
            }

            if (fileStream == null || file.getOriginalFilename() == null) {
                return renderError("文件不存在!");
            }

            String ext = FileUtil.getExt(file.getOriginalFilename());
            String relaPath = (ext.length() > 1 ? ext.substring(1) : "other") + "/" + UUID.randomUUID() + ext;
            String savePath = (path + relaPath).replace("//", "/");

            FileUtil.saveFile(fileStream, savePath);
            src.put("src", "/upload/" + relaPath);
            src.put("title", file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));// 文件名称
            src.put("size", file.getSize() / (1024));// 文件大小KB

        } catch (Exception e) {
            e.printStackTrace();
            return renderError("文件上传发送错误!");
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return renderSuccess(src);
    }
}
