package com.aadi.yummy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileData {
    private String fileName;
    private String filePath;
}
