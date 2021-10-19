package com.divary.authentication.model;

import com.divary.authentication.audit.AuditEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AuditEntity {

    private static final long serialVersionUID = 2883942956208213204L;

    @Id
    @GeneratedValue(generator = "account_uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "account_uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "boolean default true")
    private boolean firstLogin;
}
