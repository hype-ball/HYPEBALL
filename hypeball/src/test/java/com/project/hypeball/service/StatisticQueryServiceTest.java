package com.project.hypeball.service;

import com.project.hypeball.domain.Review;
import com.project.hypeball.domain.Store;
import com.project.hypeball.dto.CountDto;
import com.project.hypeball.dto.StatisticDto;
import com.project.hypeball.repository.ReviewRepository;
import com.project.hypeball.repository.StoreRepository;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatisticQueryServiceTest {

    @Autowired
    StatisticQueryService service;

    @Test
    public void manyReview() throws Exception {
        List<CountDto> countDtos = service.reviewManyStore();
        for (CountDto countDto : countDtos) {
            System.out.println("countDto = " + countDto.getCount());
            System.out.println("countDto.getStoreId() = " + countDto.getStoreId());
            System.out.println("countDto.getName() = " + countDto.getName());
        }
    }

    @Test
    public void manyLike() throws Exception {
        List<StatisticDto> stores = service.likeManyStore();
        System.out.println("stores.size() = " + stores.size());
        for (StatisticDto store : stores) {
            System.out.println("store = " + store);
        }
    }

    @Test
    public void like_method() throws Exception {
        List<Store> stores = service.likeManyStore_method();
        for (Store store : stores) {
            System.out.println("store.getId() = " + store.getId());
            System.out.println("store.getName() = " + store.getName());
            System.out.println("store.getTotalLikeCount() = " + store.getTotalLikeCount());
        }
    }

    @Test
    public void highStar() throws Exception {
        List<StatisticDto> tempDtos = service.highStarStore();
        for (StatisticDto tempDto : tempDtos) {
            System.out.println("tempDto = " + tempDto);
        }
    }

    @Test
    public void search() throws Exception {
        String keyword = "마포";
        List<Store> search = service.search(keyword);
        for (Store store : search) {
            System.out.println(" ========================== ");
            System.out.println("store.getId() = " + store.getId());
            System.out.println("store.getName() = " + store.getName());
            System.out.println("store.getAddress() = " + store.getAddress());
        }
    }

}