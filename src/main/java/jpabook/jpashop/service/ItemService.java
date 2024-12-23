package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService { // 상품서비스는 상품 리퍼지토리에 위임만 하는 클래스임. 실무에서는 바로 접근해서 써도 됨

    private final ItemRepository itemRepository;

    @Transactional  //변경
    public void saveItem(Item item) {   // 상품 저장
        itemRepository.save(item);
    }

    @Transactional  //변경
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() { // 상품 전체 조회
        return itemRepository.findAll();    // 상품 전체를 조회해서 반환
    }

    public Item findOne(Long itemId) {  // 상품 단건 조회
        return itemRepository.findOne(itemId);  // 상품을 찾아서 반환
    }

}

