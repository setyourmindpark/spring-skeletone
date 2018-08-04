package service.index;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.map.QueryMap;

@Service("indexService")
public class IndexServiceImpl implements IndexService {
	
	@Resource(name = "indexDAO")
	private IndexDAO indexDAO;
	
	public QueryMap now(){
		return indexDAO.now();	
	}
}
