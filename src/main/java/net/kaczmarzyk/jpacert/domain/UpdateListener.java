package net.kaczmarzyk.jpacert.domain;

import javax.persistence.PreUpdate;


public class UpdateListener {

	@PreUpdate
	public void preUpdate(CallbackEntity entity) {
		entity.incUpdateCount();
	}
}
