package com.lyml.demo1.common;

import java.io.*;

public class FileUtil {
    public static void saveFile(InputStream is, String fileName) throws IOException {
        File file = new File(fileName);
        if(!file.getParentFile().exists()){
            if(!file.getParentFile().mkdirs()){
                throw new IOException("无法创建文件夹");
            }
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        in = new BufferedInputStream(is);
        out = new BufferedOutputStream(new FileOutputStream(fileName));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }

    public static String getExt(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }
}
