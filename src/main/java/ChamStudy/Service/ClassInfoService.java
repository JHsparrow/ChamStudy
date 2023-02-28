package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ChamStudy.Dto.ClassInfoDto;
import ChamStudy.Dto.ClassInfoListDto;
import ChamStudy.Entity.ClassInfo;
import ChamStudy.Entity.ContentInfo;
import ChamStudy.Repository.ClassInfoRepository;
import ChamStudy.Repository.OnContentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassInfoService {
	
	private final ClassInfoRepository classInfoRepository;
	private final OnContentRepository onContentRepository;
	
	//강의 등록
	public Long saveClass(ClassInfoDto adminClassDto) throws Exception {
		
		try {
			
			//dto객체에서 엔티티객체를 생성
			ClassInfo classInfo = adminClassDto.createClassInfo();
					
			//DB에 저장
			classInfo = classInfoRepository.save(classInfo);
			
			//contentId를 classInfo에도 저장한다.
//			classInfo.setContentInfo(classInfo);
			
			return classInfo.getId();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//강의리스트 전체조회
	@Transactional(readOnly = true)
	public List<ClassInfo> getAllClass() {
		List<ClassInfo> classInfo = classInfoRepository.findAll();
		
		return classInfo;
	}
	
	//강의리스트 전체조회(페이징)
	@Transactional(readOnly = true)
	public Page<ClassInfoListDto> getAllClassPage(Pageable pageable) {
		Page<ClassInfoListDto> classInfo = classInfoRepository.joinContent(pageable);
		
		return classInfo;
	}
	
	//강의리스트 검색
	@Transactional(readOnly = true)
	public List<ClassInfo> getSearch(ClassInfoDto adminClassDto) {
		
		List<ClassInfo> classInfo = classInfoRepository.findByNameByNative(adminClassDto.getName());
		
		return classInfo;
	}
	
	
	//콘텐츠 전체조회
	@Transactional(readOnly = true)
	public List<ContentInfo> getAllContents() {
		List<ContentInfo> contentInfo = onContentRepository.findAll();
		return contentInfo;
	}
	
	//강의 삭제
    public void deleteClass(Long classId) {

    	Optional<ClassInfo> classInfo = classInfoRepository.findById(classId);
    	
    	if (classInfo != null) {
    		classInfoRepository.deleteById(classId);
    	}
    }
    
    //강의아이디로 조회
    public ClassInfoDto getId(Long classId) {
    	ClassInfo classInfo = classInfoRepository.findById(classId).orElseThrow(EntityNotFoundException::new);

    	ClassInfoDto adminClassDto = ClassInfoDto.of(classInfo);
    	
    	return adminClassDto;
    }
    
    //강의 수정
    public Long updateClass(ClassInfoDto adminClassDto) throws Exception {
        //상품 수정
    	ClassInfo classInfo = classInfoRepository.findById(adminClassDto.getId())
                .orElseThrow(EntityNotFoundException::new);
    	classInfo.updateClass(adminClassDto);

        return classInfo.getId();
    }

	
}
