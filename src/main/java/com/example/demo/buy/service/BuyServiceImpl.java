package com.example.demo.buy.service;

import com.example.demo.buy.model.BuyRequestBody;
import com.example.demo.cart.repository.CartRepository;
import com.example.demo.crud.repository.PlushyRepository;
import com.example.demo.crud.repository.models.PlushyInDB;
import com.example.demo.notifications.service.NotificationsService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuyServiceImpl implements BuyService {

    private final PlushyRepository plushyRepository;
    private final CartRepository cartRepository;
    private final KieContainer notificationsKieContainer;
    private final NotificationsService notificationsService;

    @Autowired
    public BuyServiceImpl(
            PlushyRepository plushyRepository,
            CartRepository cartRepository,
            KieContainer notificationsKieContainer,
            NotificationsService notificationsService
    ) {
        this.plushyRepository = plushyRepository;
        this.cartRepository = cartRepository;
        this.notificationsKieContainer = notificationsKieContainer;
        this.notificationsService = notificationsService;
    }

    @Override
    @Transactional
    public List<PlushyInDB> buyPlushyById(List<BuyRequestBody> plushiesToBuy) {
        List<Long> ids = new ArrayList<>();
        for ( BuyRequestBody requestBody: plushiesToBuy) {
            ids.add(requestBody.getId());
        }
        List<PlushyInDB> plushies = plushyRepository.findAllById(ids);
        for(PlushyInDB plushyInDB : plushies) {
            for (BuyRequestBody buyRequestBody : plushiesToBuy) {
                if (plushyInDB.getId().equals(buyRequestBody.getId())) {
                    plushyInDB.setQuantity(plushyInDB.getQuantity() - buyRequestBody.getAmount());
                    plushyInDB.setQuantitySold(plushyInDB.getQuantitySold() + buyRequestBody.getAmount());
                }
            }
            KieSession kieSession = notificationsKieContainer.newKieSession();
            kieSession.setGlobal("notificationsService", notificationsService);
            kieSession.insert(plushyInDB);
            kieSession.fireAllRules();
            kieSession.dispose();
        }
        cartRepository.deleteAll();
        return plushies;
    }
}
