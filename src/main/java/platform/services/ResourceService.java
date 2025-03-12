package platform.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceLoader resourceLoader;

    private Resource getResource(String file){
        return resourceLoader.getResource("classpath:" + file);
    }
//    public File getResourceFile(String file) throws IOException {
//        return getResource(file).getFile();
//    }
    public String readFile(String file) throws IOException {
        try(InputStream is = getResource(file).getInputStream()){
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
//        ClassPathResource resource = new ClassPathResource(getResourceFolder);
//        try (InputStream inputStream = resource..getInputStream()) {
//            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//        }
    }

}
