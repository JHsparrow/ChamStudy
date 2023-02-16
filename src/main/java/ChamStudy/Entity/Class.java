package ChamStudy.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@SequenceGenerator(
        name="CLASS_GEN_GEN", //기수제 시퀸스 
        sequenceName="CLASS_SEQ", //시퀀스 이름
        initialValue=1000 //시작값
        )
@Table(name="class") 
@Getter
@Setter
@ToString
public class Class {
	@Id
	@Column(name="class_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;	
	
	
}
