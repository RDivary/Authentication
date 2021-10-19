package com.divary.authentication.audit;

import com.divary.authentication.audit.listener.AuditEntityListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditEntityListener.class})
public class AuditEntity implements Serializable {

    private static final long serialVersionUID = 3408609734822345307L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "created_date"
    )
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "last_modified_date"
    )
    private Date lastModifiedDate;

}
