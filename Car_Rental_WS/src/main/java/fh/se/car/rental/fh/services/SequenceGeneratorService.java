package fh.se.car.rental.fh.services;

import org.springframework.stereotype.Service;

@Service
public class SequenceGeneratorService {

    public long generateSequenceId(DocumentType documentType) {
        if(documentType != null) {
            long time = System.currentTimeMillis();
            long id = documentType.getPrefix() * time; //TODO: find and use a nicer Algorithm
            return id;
        } else {
            return 0;
        }
    }
}
