package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.KorzinaDto;

import java.util.List;

public interface KorzinaService {
    Korzina createKorzina (Users users,Long id);
    Korzina createEmptyKorzina (Long id);
    void addProducts (Korzina korzina,Long id);
    KorzinaDto getKorzinaByEmail(String name);
    KorzinaDto getKorzinaByAnonym();
    Korzina removeProduct (Korzina korzina , Long id);

}
