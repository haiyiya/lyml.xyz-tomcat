package com.lyml.demo1.config;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.baomidou.mybatisplus.toolkit.ArrayUtils;
import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

public class ClassPathTldsLoader {

    /**
     * security.tld文件路径
     */
    private static final String SECURITY_TLD = "/static/security.tld";

    final private List<String> classPathTlds;

    public ClassPathTldsLoader(String... classPathTlds) {
        super();
        if(ArrayUtils.isEmpty(classPathTlds)){
            this.classPathTlds = Arrays.asList(SECURITY_TLD);
        }else{
            this.classPathTlds = Arrays.asList(classPathTlds);
        }
    }
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void loadClassPathTlds() {
        TaglibFactory taglibFactory = freeMarkerConfigurer.getTaglibFactory();
        if(taglibFactory.getObjectWrapper()==null){
            taglibFactory.setObjectWrapper(freeMarkerConfigurer.getConfiguration().getObjectWrapper());
        }
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(classPathTlds);
    }
}