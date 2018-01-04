package com.jaime.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jaime.auth.model.User;

@RestController
@RequestMapping("/audit")
public class AuditController {

	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/history/{id}", method = RequestMethod.GET)
	public List<Map> getLogByRepository(@PathVariable("id") int id) {
		try {
			AuditQuery query = AuditReaderFactory.get(entityManager)
			        			.createQuery()
			        			.forRevisionsOfEntity(User.class, false, true)
			        			.add(AuditEntity.id().eq(id))
			        			.addOrder(AuditEntity.revisionNumber().asc());
			
			ArrayList<Object[]> list = (ArrayList) query.getResultList();
			
			List<Map> result = new ArrayList<Map>();
			for(int i=0; i < list.size(); i++){
			    Object[] triplet = list.get(i);
			    User entity = (User) triplet[0];
			    RevisionType revisionType = (RevisionType) triplet[2];
			    
			    Map<String, User> map = new HashMap<String, User>();
			    map.put(revisionType.name(), entity);
			    result.add(map);
			}
			
			return result;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
	
}
