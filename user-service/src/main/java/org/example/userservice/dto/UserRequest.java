package org.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private List<String> projects;
    private String companyId;
    private String phone;
}
