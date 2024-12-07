package edu.northwestu.intc3283.ApiExampleApplication.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.lang.classfile.Attributes;

@Controller
@RequestMapping("/docs")
class DocumentationController {

    //private final String content;

    DocumentationController() throws IOException {
 /*       Attributes attributes = AttributesBuilder.attributes()
                .tableOfContents2(Placement.LEFT)
                .sectionNumbers(true)
                .setAnchors(true)
                .get();

        Options options = OptionsBuilder.options()
                .attributes(attributes)
                .toFile(false)
                .headerFooter(true)
                .safe(SafeMode.SAFE)
                .get();

        Asciidoctor asciidoctor = Asciidoctor.Factory.create();
        content = String.join("\n", asciidoctor.convertDirectory(
                new AsciiDocDirectoryWalker(new ClassPathResource("/path/to/documentation")
                        .getURI()
                        .getPath()
                        .replaceFirst("/", "")),
                options));
        asciidoctor.close();*/
    }

    @ResponseBody
    @GetMapping("")
    String documentation() {
        return "api/getTasks.http";
    }
}