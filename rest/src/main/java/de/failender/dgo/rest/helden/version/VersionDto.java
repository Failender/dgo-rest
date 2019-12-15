package de.failender.dgo.rest.helden.version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import de.failender.dgo.persistance.held.VersionEntity;

import java.time.LocalDateTime;
import java.util.Date;

public class VersionDto {

    private String letztesAbenteuer;
    private LocalDateTime datum;
    private int version;

    public VersionDto(VersionEntity versionEntity) {
        this.letztesAbenteuer = versionEntity.getLastEvent();
        this.datum = versionEntity.getCreatedDate();
        this.version = versionEntity.getVersion();
    }

    public String getLetztesAbenteuer() {
        return letztesAbenteuer;
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    public LocalDateTime getDatum() {
        return datum;
    }

    public int getVersion() {
        return version;
    }
}
