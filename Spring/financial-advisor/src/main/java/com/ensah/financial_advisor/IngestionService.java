package com.ensah.financial_advisor;


import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class IngestionService implements CommandLineRunner{

    private final VectorStore vectorstore;

    @Value("classpath:/docs/article_thebeatoct2024.pdf")
    private Resource pdfResource;

    @Override
    public void run(String... args) throws Exception {
        ParagraphPdfDocumentReader pdfParagraphReader = new ParagraphPdfDocumentReader(pdfResource);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorstore.accept(textSplitter.split(pdfParagraphReader.read()));
        log.info("Ingested document: {}", pdfResource.getFilename());
        
    }

    


}
