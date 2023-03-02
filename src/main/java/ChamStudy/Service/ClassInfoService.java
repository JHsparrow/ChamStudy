package ChamStudy.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
	
	//강의상세페이지 조회
	@Transactional(readOnly = true)
	public ClassInfoListDto getClassInfo(ClassInfoDto adminClassDto) {
		System.err.println(adminClassDto);
		
		ClassInfoListDto classDetail = classInfoRepository.findByClassDetail(adminClassDto);
		
		return classDetail;
	}
	
	//강의리스트 검색
	@Transactional(readOnly = true)
	public Page<ClassInfoListDto> getSearch(ClassInfoDto adminClassDto, Pageable pageable) {
		
		//List<ClassInfo> classInfo = classInfoRepository.findByNameByNative(adminClassDto.getName());
		Page<ClassInfoListDto> classInfo = classInfoRepository.findByClassList(adminClassDto, pageable);
		
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
    	//classInfo.updateClass(adminClassDto);
    	long updCnt = classInfoRepository.updateByClassDetail(adminClassDto);
    	
    	if (updCnt > 0) {
    		System.out.println("업데이트 성공 (classId=" + classInfo.getId() + ")");
    	} else {
    		System.out.println("업데이트 실패 (classId=" + classInfo.getId() + ")");
    	}

        return classInfo.getId();
    }
    
    //나의 강의실 넘겨주기
    public Long orderCartItem(ClassInfoDto classInfoDto, Long classId){
    	
    	ClassInfo classInfo = classInfoRepository.findById(classId).orElseThrow(EntityNotFoundException::new);
    	/*
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);

            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository
                            .findById(cartOrderDto.getCartItemId())
                            .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
		
		*/
    	
        return classId;
    }

	
}
