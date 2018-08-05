package service.index;

import java.util.Map;

import org.springframework.stereotype.Repository;

import common.dao.AbstractDAO;
import common.map.QueryMap;

@Repository("indexDAO")
public class IndexDAO extends AbstractDAO {
	
	public Map<String,String> now(){
		return (Map<String,String>) selectOne("service.index.now");
	}
}
