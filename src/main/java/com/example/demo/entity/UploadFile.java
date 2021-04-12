package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String fileName;
    @Column(nullable = false)
    @NotBlank
    private String fileDownloadUri;
    @Column(nullable = false)
    @NotBlank
    private String fileType;
    @Column(nullable = false)
    @NotBlank
    private Long size;
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
}
