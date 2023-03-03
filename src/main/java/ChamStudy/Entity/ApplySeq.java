package ChamStudy.Entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="apply_seq")
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
public class ApplySeq {
	@Id
	private Long nextVal;
}
