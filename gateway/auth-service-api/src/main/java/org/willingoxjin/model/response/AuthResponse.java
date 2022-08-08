package org.willingoxjin.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.willingoxjin.model.AuthUser;

/**
 * @author Jin.Nie
 * @date 2022-03-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private AuthUser authUser;

    private Long code;

}
