package br.mendonca.testemaven.services.dto;

import br.mendonca.testemaven.model.entities.User;

public class UserDTO {
	
	private String name;
	private String email;
	private String uuid;

	public String getUuid(){ return uuid; }
	public void setUuid(String uuid){ this.uuid = uuid; }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public static UserDTO userMapper(User user) {
		UserDTO dto = new UserDTO();
		dto.setUuid(user.getUuid().toString());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		
		return dto;
	}
}
