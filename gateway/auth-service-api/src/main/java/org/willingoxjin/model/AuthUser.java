package org.willingoxjin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jin.Nie
 * @date 2022-03-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements Serializable {

    private Long userId;

    private String token;

    private String refreshToken;

}
