/**
 * This file is part of jpa-cert application.
 *
 * Jpa-cert is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jpa-cert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jpa-cert; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
@EntityListeners({ UpdateListener.class })
public class CallbackEntity {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	private int updateCount;

	private int prePersistCount;
	private int postLoadCount;

	@Transient
	private boolean postPersist;

	@PrePersist
	private void prePersist() {
		prePersistCount++;
	}

	@PostLoad
	private void postLoad() {
		postLoadCount++;
	}

	@PostPersist
	private void postPersist() {
		postPersist = true;
	}

	public Long getId() {
		return id;
	}

	public int getPrePersistCount() {
		return prePersistCount;
	}

	public int getPostLoadCount() {
		return postLoadCount;
	}

	public boolean isPostPersist() {
		return postPersist;
	}

	public void incUpdateCount() {
		updateCount++;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
