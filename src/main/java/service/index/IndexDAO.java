package service.index;

import org.springframework.stereotype.Repository;

import common.dao.AbstractDAO;
import common.map.QueryMap;

@Repository("indexDAO")
public class IndexDAO extends AbstractDAO {
	
	public QueryMap now(){
		return (QueryMap) selectOne("service.index.now");
	}
}
