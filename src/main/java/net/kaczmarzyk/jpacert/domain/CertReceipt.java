package net.kaczmarzyk.jpacert.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class CertReceipt {

	@Id
	private Long certId;
	
	@MapsId
	@OneToOne
	private Certificate cert;
	
	CertReceipt() {
	}
	
	public CertReceipt(Certificate cert) {
		this.cert = cert;
	}
	
	public Certificate getCert() {
		return cert;
	}
	
	public Long getCertId() {
		return certId;
	}
}
