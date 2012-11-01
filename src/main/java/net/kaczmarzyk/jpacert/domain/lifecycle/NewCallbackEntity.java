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
package net.kaczmarzyk.jpacert.domain.lifecycle;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;


@Entity
//@ExcludeSuperclassListeners
//@ExcludeDefaultListeners
@EntityListeners({NewCallbackEntityListener.class})
public class NewCallbackEntity extends CallbackEntity {

	
	@PrePersist
	public void prePersist2() { // callback from sub class wouldn't be called if method was overriden (i.e. named prePersist())
		getCallbackCalls().add("NewCallbackEntity.prePersist2");
	}
}
