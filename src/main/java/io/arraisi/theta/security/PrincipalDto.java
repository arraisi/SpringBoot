package io.arraisi.theta.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDto {

	private Long id;
	private String name;
	private String email;
	private String password;

}
