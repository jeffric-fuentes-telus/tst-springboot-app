package com.telus.iof.itss.api.repositories.mocks;
import com.telus.iof.itss.api.model.dto.HistoryITSSRegister;
import com.telus.iof.itss.api.model.dto.catalogs.Catalog1Value;
import com.telus.iof.itss.api.model.entities.ITSSRegistry;
import com.telus.iof.itss.api.repositories.interfaces.IITSSRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ITSSRepositoryMock implements IITSSRepository {

    @Override
    public ITSSRegistry getITSSById(String Id) {
        return null;
    }

    @Override
    public ArrayList<ITSSRegistry> getAllITSS() {
        return null;
    }

    @Override
    public ArrayList<String> getAllITSSKeys() {
        return null;
    }

    @Override
    public ArrayList<String> getAllITSSPaginated(int from, int to) {
        return null;
    }

    @Override
    public ArrayList<String> getAllITSSByPattern(String pattern) {
        return null;
    }

    @Override
    public ArrayList<ITSSRegistry> getITSSListByUserId(String UserId) {
        return null;
    }

    @Override
    public ArrayList<ITSSRegistry> getITSSListByUserIdPaginated(String UserId, int from, int to) {
        return null;
    }

    @Override
    public Set<String> getRelatedITSS(String user) {
        return null;
    }

    @Override
    public String getITSSProperty(String ITSSId, String property) {
        return null;
    }

    @Override
    public Boolean updateITSSProperty(String ITSSId, String propertyName, String value) {
        return null;
    }

    @Override
    public Boolean addRegisterToITSSHistory(String ITSSId, HistoryITSSRegister register) {
        return null;
    }

    @Override
    public List<Catalog1Value> getCatalog1Fields() {
        return null;
    }
}