package ChamStudy.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ChamStudy.Entity.ClassInfo;

public interface ClassInfoRepositoryCustom {
	Page<ClassInfo> findAll(Pageable pageable);
}
