package com.sparta.copang.hub.common.configuration;

import com.sparta.copang.hub.domain.model.Hub;
import com.sparta.copang.hub.domain.model.Path;
import com.sparta.copang.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HubInitializer {

    private final HubRepository hubRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        Hub hub1 = new Hub("서울특별시 센터", 37.4742027808565F, 127.123621185562F, "서울 송파구 송파대로 55", 1);
        Hub hub2 = new Hub("경기 북부 센터", 37.6403771056018F, 126.87379545786F, "경기도 고양시 덕양구 권율대로 570", 2);
        Hub hub3 = new Hub("경기 남부 센터", 37.1896213142136F, 127.375050006958F, "경기도 이천시 덕평로 257-21", 3);
        Hub hub4 = new Hub("부산광역시 센터", 35.117605126596F, 129.045060216345F, "부산 동구 중앙대로 206", 4);
        Hub hub5 = new Hub("대구광역시 센터", 35.8758849492106F, 128.596129208483F, "대구 북구 태평로 161", 5);
        Hub hub6 = new Hub("인천광역시 센터", 37.4560499608337F, 126.705255744089F, "인천 남동구 정각로 29", 6);
        Hub hub7 = new Hub("광주광역시 센터", 35.1600994105234F, 126.851461925213F, "광주 서구 내방로 111", 7);
        Hub hub8 = new Hub("대전광역시 센터", 36.3503849976553F, 127.384633005948F, "대전 서구 둔산로 100", 8);
        Hub hub9 = new Hub("울산광역시 센터", 35.5379472830778F, 129.311256608093F, "울산 남구 중앙로 201", 9);
        Hub hub10 = new Hub("세종특별자치시 센터", 36.4800579897497F, 127.289039408864F, "세종특별자치시 한누리대로 2130", 10);
        Hub hub11 = new Hub("강원특별자치도 센터", 37.8800729197963F, 127.727907820318F, "강원특별자치도 춘천시 중앙로 1", 11);
        Hub hub12 = new Hub("충청북도 센터", 36.6353867908159F, 127.491428436987F, "충북 청주시 상당구 상당로 82", 12);
        Hub hub13 = new Hub("충청남도 센터", 36.6590666265439F, 126.672978750559F, "충남 홍성군 홍북읍 충남대로 21", 13);
        Hub hub14 = new Hub("전북특별자치도 센터", 35.8194374902358F, 127.106374667247F, "전북특별자치도 전주시 완산구 효자로 225", 14);
        Hub hub15 = new Hub("전라남도 센터", 34.8174988528003F, 126.465423854957F, "전남 무안군 삼향읍 오룡길 1", 15);
        Hub hub16 = new Hub("경상북도 센터", 36.5761205474728F, 128.505722686385F, "경북 안동시 풍천면 도청대로 455", 16);
        Hub hub17 = new Hub("경상남도 센터", 35.2378032514675F, 128.691940442146F, "경남 창원시 의창구 중앙대로 300", 17);

        saveHub(hub1, hub2, hub3, hub4, hub5, hub6, hub7, hub8);
        saveHub(hub9, hub10, hub11, hub12, hub13, hub14, hub15, hub16);
        hubRepository.save(hub17);

        Path path1 = Path.builder().duration(1).distance(1).startHub(hub1).endHub(hub2).build();
        Path path2 = Path.builder().duration(1).distance(1).startHub(hub2).endHub(hub3).build();
        Path path3 = Path.builder().duration(1).distance(1).startHub(hub3).endHub(hub4).build();
        Path path4 = Path.builder().duration(1).distance(1).startHub(hub4).endHub(hub5).build();
        Path path5 = Path.builder().duration(1).distance(1).startHub(hub5).endHub(hub6).build();
        Path path6 = Path.builder().duration(1).distance(1).startHub(hub6).endHub(hub7).build();
        Path path7 = Path.builder().duration(1).distance(1).startHub(hub7).endHub(hub8).build();
        Path path8 = Path.builder().duration(1).distance(1).startHub(hub8).endHub(hub9).build();
        Path path9 = Path.builder().duration(1).distance(1).startHub(hub9).endHub(hub10).build();
        Path path10 = Path.builder().duration(1).distance(1).startHub(hub10).endHub(hub11).build();
        Path path11 = Path.builder().duration(1).distance(1).startHub(hub11).endHub(hub12).build();
        Path path12 = Path.builder().duration(1).distance(1).startHub(hub12).endHub(hub13).build();
        Path path13 = Path.builder().duration(1).distance(1).startHub(hub13).endHub(hub14).build();
        Path path14 = Path.builder().duration(1).distance(1).startHub(hub14).endHub(hub15).build();
        Path path15 = Path.builder().duration(1).distance(1).startHub(hub15).endHub(hub16).build();
        Path path16 = Path.builder().duration(1).distance(1).startHub(hub16).endHub(hub17).build();

        savePath(hub1, hub2, path1);
        savePath(hub2, hub3, path2);
        savePath(hub3, hub4, path3);
        savePath(hub4, hub5, path4);
        savePath(hub5, hub6, path5);
        savePath(hub6, hub7, path6);
        savePath(hub7, hub8, path7);
        savePath(hub8, hub9, path8);
        savePath(hub9, hub10, path9);
        savePath(hub10, hub11, path10);
        savePath(hub11, hub12, path11);
        savePath(hub12, hub13, path12);
        savePath(hub13, hub14, path13);
        savePath(hub14, hub15, path14);
        savePath(hub15, hub16, path15);
        savePath(hub16, hub17, path16);


    }

    private void savePath(Hub startHub, Hub endHub, Path path) {
        startHub.getStartPaths().add(path);
        endHub.getStartPaths().add(path);

        hubRepository.save(startHub);
        hubRepository.save(endHub);
    }

    private void saveHub(Hub hub9, Hub hub10, Hub hub11, Hub hub12, Hub hub13, Hub hub14, Hub hub15, Hub hub16) {
        hubRepository.save(hub9);
        hubRepository.save(hub10);
        hubRepository.save(hub11);
        hubRepository.save(hub12);
        hubRepository.save(hub13);
        hubRepository.save(hub14);
        hubRepository.save(hub15);
        hubRepository.save(hub16);
    }
}
