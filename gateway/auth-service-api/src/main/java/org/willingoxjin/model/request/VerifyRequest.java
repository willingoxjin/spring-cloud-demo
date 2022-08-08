package org.willingoxjin.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jin.Nie
 * @date 2022-03-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {

    private Long userId;

    private String token;

}
