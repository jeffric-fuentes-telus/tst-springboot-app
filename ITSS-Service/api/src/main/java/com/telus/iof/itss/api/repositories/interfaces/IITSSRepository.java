package com.telus.iof.itss.api.repositories.interfaces;
import com.telus.iof.itss.api.model.dto.HistoryITSSRegister;
import com.telus.iof.itss.api.model.dto.catalogs.Catalog1Value;
import com.telus.iof.itss.api.model.entities.ITSSRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface IITSSRepository {
    public ITSSRegistry getITSSById(String Id);
    public ArrayList<ITSSRegistry> getAllITSS();
    public ArrayList<String> getAllITSSKeys();
    public ArrayList<String> getAllITSSPaginated(int from, int to);
    public ArrayList<String> getAllITSSByPattern(String pattern);
    public ArrayList<ITSSRegistry> getITSSListByUserId(String UserId);
    public ArrayList<ITSSRegistry> getITSSListByUserIdPaginated(String UserId, int from, int to);
    public Set<String> getRelatedITSS(String user);
    public String getITSSProperty(String ITSSId, String property);
    public Boolean updateITSSProperty(String ITSSId, String propertyName, String value);
    public Boolean addRegisterToITSSHistory(String ITSSId, HistoryITSSRegister register);
    public List<Catalog1Value> getCatalog1Fields();
}
