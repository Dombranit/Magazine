package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Korzina;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.dto.KorzinaDto;

public interface KorzinaService {
    Korzina createKorzina (Users users,Long id);
    void addProducts (Korzina korzina,Long id);
    KorzinaDto getKorzinaByEmail(String name);
    void commitKorzinaToOrder (String email);
    void removeProduct (Korzina korzina , Long id);

}
