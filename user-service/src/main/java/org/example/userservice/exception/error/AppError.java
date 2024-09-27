package org.example.userservice.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppError {
    private String message;
}
